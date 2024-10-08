package info.martindupuis.tradingclient.portsadapters.questradeclient


import info.martindupuis.jquestrade.AuthenticationToken
import info.martindupuis.jquestrade.QuestradeAccount
import info.martindupuis.jquestrade.exceptions.AuthenticationException
import info.martindupuis.jquestrade.exceptions.AuthenticationExpiredException
import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.model.AccountStatus
import info.martindupuis.tradingclient.model.AccountType
import info.martindupuis.tradingclient.model.ClientAccountType
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import info.martindupuis.tradingclient.portsadapters.questradeclient.mapping.AccountActivitiesMapper
import info.martindupuis.tradingclient.portsadapters.questradeclient.mapping.AccountMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.instancio.Gen.oneOf
import org.instancio.Instancio
import org.instancio.Select.field
import org.instancio.generators.Generators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mapstruct.factory.Mappers
import info.martindupuis.jquestrade.client.QuestradeWebClient as LibQuestrade

@ExtendWith(MockKExtension::class)
class TradingServiceImplTests {

    @MockK
    lateinit var libQuestrade: LibQuestrade

    private lateinit var acctMapper: AccountMapper
    private lateinit var acctActvMapper: AccountActivitiesMapper

    private lateinit var sut: TradingService

    @BeforeEach
    fun init_test() {
        MockKAnnotations.init(this)

        // real implementation of the mapper so that we can test it
        acctMapper = Mappers.getMapper(AccountMapper::class.java)
        acctActvMapper = Mappers.getMapper(AccountActivitiesMapper::class.java)

        sut = TradingServiceImpl(libQuestrade, acctMapper, acctActvMapper)
    }

    @Nested
    inner class AuthenticationToExternalTradingServiceTests {
        @Test
        fun whenANewInstanceIsCreated_thenItIsDisconnected() {
            assertThat(sut).isNotNull
            assertThat(sut.isConnected()).isFalse
        }

        @Test
        fun givenEverythingIsFine_whenConnectIsCalled_thenTheServiceConnectsToQuestradeAPI() {
            val authToken = Instancio.of(AuthenticationToken::class.java)
                .set(field(AuthenticationToken::access_token), "Valid test token")
                .generate(field(AuthenticationToken::expires_at)) { gen -> gen.temporal().zonedDateTime().future() }
                .create()
            val refreshToken = Instancio.create(QuestradeRefreshToken::class.java)

            every { libQuestrade.authenticate(any()) } returns authToken

            sut.connect(refreshToken)
            assertThat(sut.isConnected()).isTrue
        }

        @Test
        fun givenAccessTokenIsNotValid_whenConnectIsCalled_thenTheServiceIsNotConnected() {
            val authToken = Instancio.of(AuthenticationToken::class.java)
                .set(field(AuthenticationToken::access_token), "")
                .create()

            val refreshToken = Instancio.create(QuestradeRefreshToken::class.java)

            every { libQuestrade.authenticate(any()) } returns authToken

            sut.connect(refreshToken)
            assertThat(sut.isConnected()).isFalse()
        }

        @Test
        fun givenNoTokenIsReturnedByExternalService_whenConnectIsCalled_thenAuthenticationExceptionIsThrown() {
            val refreshToken = Instancio.create(QuestradeRefreshToken::class.java)
            val authErrMsg = "Auth exception test"

            every { libQuestrade.authenticate(any()) } throws AuthenticationException(authErrMsg)

            assertThatExceptionOfType(AuthenticationException::class.java)
                .isThrownBy { sut.connect(refreshToken) }
                .withMessage(authErrMsg)
        }
    }

    @Test
    fun givenValidTokens_whenFetchAccountsIsCalled_thenAllAccountsAreReturned() {
        val refeshToken = Instancio.create(QuestradeRefreshToken::class.java)
        val authToken = Instancio.of(AuthenticationToken::class.java)
            .generate(field(AuthenticationToken::expires_at)) { gen: Generators -> gen.temporal().zonedDateTime().future() }
            .create()

        every { libQuestrade.authenticate(refeshToken.refreshToken) } returns authToken
        every { libQuestrade.getAccounts(authToken) } returns Instancio.ofSet(QuestradeAccount::class.java)
            .set(field(QuestradeAccount::clientAccountType), ClientAccountType.Individual.name)
            .generate(field(QuestradeAccount::status), oneOf(AccountStatus.Active.name, AccountStatus.Closed.name))
            .generate(field(QuestradeAccount::type), oneOf(AccountType.RRSP.name, AccountType.TFSA.name))
            .create()

        sut.connect(refeshToken)
        val myAccounts = sut.getAccounts()

        assertThat(myAccounts.size).isGreaterThanOrEqualTo(1)
        assertThat(myAccounts.first()).isNotNull

        val anAccount: Account = myAccounts.first()
        assertThat(anAccount.status).isNotNull
    }

    @Test
    fun givenExpiredAuthToken_whenAccountIsCalled_thenAuthenticationExpiredExceptionIsThrown() {
        val authErrMsg = "Auth expired test"

        val refreshToken = Instancio.create(QuestradeRefreshToken::class.java)
        val expiredAuthToken = Instancio.of(AuthenticationToken::class.java)
            .generate(field(AuthenticationToken::expires_at)) { gen: Generators -> gen.temporal().zonedDateTime().past() }
            .create()

        every { libQuestrade.authenticate(any()) } returns expiredAuthToken
        every { libQuestrade.getAccounts(any()) } throws AuthenticationExpiredException(authErrMsg)

        sut.connect(refreshToken)

        assertThatExceptionOfType(AuthenticationExpiredException::class.java)
            .isThrownBy { sut.getAccounts() }
            .withMessage(authErrMsg)
    }
}

