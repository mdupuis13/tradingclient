package info.martindupuis.tradingclient.portsadapters.questradeclient

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class QuestradeServiceTests {
    private lateinit var sut : QuestradeService

    @BeforeEach
    fun init_test() {
        sut = QuestradeServiceImpl()
    }
    @Test
    fun newInstance_isDisconnected() {
        assertNotNull(sut)
        assertFalse(sut.isConnected())
    }
}
