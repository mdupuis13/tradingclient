package info.martindupuis.tradingclient.portsadapters.questradeclient

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

private const val REFRESH_TOKEN = "abc123"

@ExtendWith(MockKExtension::class)
class QuestradeServiceTests {
    private lateinit var sut: QuestradeService

    @BeforeEach
    fun init_test() {
        sut = QuestradeServiceImpl()
    }

    @Test
    fun newInstance_isDisconnected() {
        assertNotNull(sut)
        assertFalse(sut.isConnected())
    }

    @Test
    fun callingConnectWithRefreshToken_connectsToQuestradeAPI() {
        sut.connect(REFRESH_TOKEN)
        assertTrue(sut.isConnected())
    }

}
