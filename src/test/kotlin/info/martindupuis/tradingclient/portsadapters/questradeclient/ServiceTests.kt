package info.martindupuis.tradingclient.portsadapters.questradeclient


import com.jquestrade.Account
import com.jquestrade.AuthenticationToken
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.ZonedDateTime
import com.jquestrade.client.QuestradeWebClient as LibQuestrade

private const val REFRESH_TOKEN = "abc123"

@ExtendWith(MockKExtension::class)
internal class ServiceTests {

    @MockK
    lateinit var libQuestrade: LibQuestrade

    @MockK
    lateinit var questradeRepo: Repository

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
            every { questradeRepo.getRefeshToken() } returns Instancio.create(QuestradeRefreshToken::class.java)

            sut.connect()
            assertThat(sut.isConnected()).isTrue
        }
    }

    @Test
    fun whenFetchAccountsIsCalled_thenAllAccountsAreReturned() {
        every { libQuestrade.getAccounts(null) } returns listOf(Instancio.create(Account::class.java))

        val myAccounts = sut.getAccounts()

        assertThat(myAccounts.size).isEqualTo(1)
    }


    /**
     *  This class is created because the j-questrade library has not been
     *  created with testing in mind :/
     *
     *  We have to create objects using a json to object mapper (jackson-module-kotlin in this case)
     */
    inner class JQuestradeTestData {
        fun createBasicAccountsArray(): List<Account> {
            val anAccount = Account("Cash", "1", "Active", true, true, "Individual")

            return listOf(anAccount)
        }

        fun createAuthorizationToken(): AuthenticationToken{
            return AuthenticationToken("eidohch4Oom2ieNgetooLeiF4ix1iequo", "https://api07.iq.questrade.com/",
                ZonedDateTime.now() , "baeNoo1cie0Daiyo6at7seisah3Ohweef", "Bearer")
        }
    }
}

