package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

private const val REFRESH_TOKEN = "abc123"

@ExtendWith(MockKExtension::class)
internal class QuestradeServiceTests {
    @MockK
    lateinit var questradeRepo: QuestradeRepository

    private lateinit var sut: QuestradeService

    @BeforeEach
    fun init_test() {
        sut = QuestradeServiceImpl()
    }

    @Test
    fun whenICreateANewInstance_thenItIsDisconnected() {
        assertNotNull(sut)
        assertFalse(sut.isConnected())
    }

    @Test
    fun whenIcallConnect_thenTheServiceConnectsToQuestradeAPI() {
        every { questradeRepo.getRefeshToken() } returns QuestradeRefreshToken("", "", REFRESH_TOKEN)

        sut.connect()
        assertTrue(sut.isConnected())
    }

}
