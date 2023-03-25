package info.martindupuis.tradingclient.portsadapters.questradeclient

import com.jquestrade.Authorization
import com.jquestrade.Questrade
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import io.mockk.OfTypeMatcher
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock

private const val REFRESH_TOKEN = "abc123"

@ExtendWith(MockKExtension::class)
internal class QuestradeServiceTests {
    @MockK
    lateinit var questradeRepo: QuestradeRepository

    @MockK
    lateinit var jquestradeLib: Questrade

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
//        every { constructedWith<Questrade>(OfTypeMatcher<String>(String::class)).retrieveAccessToken() } returns REFRESH_TOKEN

        sut.connect()
        assertTrue(sut.isConnected())
    }

}
