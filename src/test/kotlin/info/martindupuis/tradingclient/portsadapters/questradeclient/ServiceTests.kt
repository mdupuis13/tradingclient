package info.martindupuis.tradingclient.portsadapters.questradeclient


import info.martindupuis.Account
import info.martindupuis.AuthenticationToken
import info.martindupuis.exceptions.AuthenticationException
import info.martindupuis.exceptions.AuthenticationExpiredException
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.instancio.Instancio
import org.instancio.Select.field
import org.instancio.generators.Generators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import info.martindupuis.client.QuestradeWebClient as LibQuestrade

@ExtendWith(MockKExtension::class)
internal class ServiceTests {

    @MockK
    lateinit var libQuestrade: LibQuestrade

    private lateinit var sut: Service

    @BeforeEach
    fun init_test() {
        MockKAnnotations.init(this)

        sut = ServiceImpl(libQuestrade)
    }

    @Nested
    inner class AuthenticationToExternalServiceTests {
        @Test
        fun whenANewInstanceIsCreated_thenItIsDisconnected() {
            assertThat(sut).isNotNull
            assertThat(sut.isConnected()).isFalse
        }

        @Test
        fun givenEverythingIsFine_whenConnectIsCalled_thenTheServiceConnectsToQuestradeAPI() {
            val authToken = Instancio.of(AuthenticationToken::class.java)
                .set(field(AuthenticationToken::access_token), "Valid test token")
                .generate(field(AuthenticationToken::expires_at)) { gen: Generators -> gen.temporal().zonedDateTime().future() }
                .create()
            val refreshToken = Instancio.create(QuestradeRefreshToken::class.java)

            every { libQuestrade.authenticate(refreshToken.refresh_token) } returns authToken

            sut.connect(refreshToken)
            assertThat(sut.isConnected()).isTrue
        }

        @Test
        fun givenAccessTokenIsNotValid_whenConnectIsCalled_thenTheServiceIsNotConnected() {
            val authToken = Instancio.of(AuthenticationToken::class.java)
                .set(field(AuthenticationToken::access_token), null)
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
        val authToken = Instancio.create(AuthenticationToken::class.java)

        every { libQuestrade.authenticate(refeshToken.refresh_token) } returns authToken
        every { libQuestrade.getAccounts(authToken) } returns Instancio.createList(Account::class.java)

        sut.connect(refeshToken)
        val myAccounts = sut.getAccounts()

        assertThat(myAccounts.size).isGreaterThanOrEqualTo(1)
    }

    @Test
    fun givenExpiredAuthToken_whenAccountIsCalled_thenAuthenticationExpiredExceptionIsThrown() {
        val authErrMsg = "Auth expired test"

        val refeshToken = Instancio.create(QuestradeRefreshToken::class.java)
        val expiredAuthToken = Instancio.of(AuthenticationToken::class.java)
            .generate(field(AuthenticationToken::expires_at)) { gen: Generators -> gen.temporal().zonedDateTime().past() }
            .create()

        every { libQuestrade.authenticate(refeshToken.refresh_token) } returns expiredAuthToken
        every { libQuestrade.getAccounts(expiredAuthToken) } throws AuthenticationExpiredException(authErrMsg)

        sut.connect(refeshToken)

        assertThatExceptionOfType(AuthenticationExpiredException::class.java)
            .isThrownBy { sut.getAccounts() }
            .withMessage(authErrMsg)
    }
}

