package info.martindupuis.tradingclient.portsadapters.questradeclient


import info.martindupuis.Account
import info.martindupuis.AuthenticationToken
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.instancio.Select.field
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import info.martindupuis.client.QuestradeWebClient as LibQuestrade

@ExtendWith(MockKExtension::class)
internal class ServiceTests {

    @MockK
    lateinit var libQuestrade: LibQuestrade

//    @MockK
//    lateinit var repo: Repository

    private lateinit var sut: Service

    @BeforeEach
    fun init_test() {
        MockKAnnotations.init(this)

        sut = ServiceImpl(libQuestrade)
    }

    @Nested
    inner class TemporaryTestsForInternalWorkings {
        @Test
        fun whenANewInstanceIsCreated_thenItIsDisconnected() {
            assertThat(sut).isNotNull
            assertThat(sut.isConnected()).isFalse
        }

        @Test
        fun whenConnectIsCalled_thenTheServiceConnectsToQuestradeAPI() {
//            every { repo.getRefeshToken() } returns Instancio.create(QuestradeRefreshToken::class.java)
            val authToken = Instancio.of(AuthenticationToken::class.java)
                    .set(field(AuthenticationToken::access_token), "Valid test token")
                    .create()
            val refreshToken = Instancio.create(QuestradeRefreshToken::class.java)

            every { libQuestrade.authenticate(any()) } returns authToken

            sut.connect(refreshToken)
            assertThat(sut.isConnected()).isTrue
        }

        @Test
        fun whenConnectIsCalled_andRefeshtokenIsnotValid_thenTheServiceIsNotConnected() {
//            every { repo.getRefeshToken() } returns Instancio.create(QuestradeRefreshToken::class.java)
            val authToken = Instancio.of(AuthenticationToken::class.java)
                    .set(field(AuthenticationToken::access_token), null)
                    .create()

            val refreshToken = Instancio.create(QuestradeRefreshToken::class.java)

            every { libQuestrade.authenticate(any()) } returns authToken

            sut.connect(refreshToken)
            assertThat(sut.isConnected()).isFalse()
        }}

    @Test
    fun whenFetchAccountsIsCalled_thenAllAccountsAreReturned() {
        val refeshToken = Instancio.create(QuestradeRefreshToken::class.java)
        val authToken = Instancio.create(AuthenticationToken::class.java)

        every { libQuestrade.authenticate(any()) } returns authToken
        every { libQuestrade.getAccounts(authToken) } returns listOf(Instancio.create(Account::class.java))

        sut.connect(refeshToken)
        val myAccounts = sut.getAccounts()

        assertThat(myAccounts.size).isGreaterThanOrEqualTo(1)
    }
}

