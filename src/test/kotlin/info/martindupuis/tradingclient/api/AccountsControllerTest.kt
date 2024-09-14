package info.martindupuis.tradingclient.api

import com.ninjasquad.springmockk.MockkBean
import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.portsadapters.questradeclient.TradingService
import io.mockk.every
import org.instancio.Instancio
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest
class AccountsControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var service: TradingService

    @Test
    fun getAccounts() {
        val accounts = Instancio.createList(Account::class.java)
        every { service.isConnected() } returns true
        every { service.getAccounts() } returns accounts

        mockMvc.get("/tradingclient/api/accounts").andExpectAll {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.length()") { value(accounts.size) }
        }
    }
}