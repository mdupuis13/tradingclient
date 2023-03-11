package info.martindupuis.tradingclient.portsadapters.questradeclient

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class QuestradeServiceTests {
    @Test
    fun canCreateInstance() {
        val sut = QuestradeServiceImpl()
        assertNotNull(sut)
    }
}
