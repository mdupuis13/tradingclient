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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class AccountsControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var service: TradingService

    @Test
    fun getAccounts() {
        every { service.isConnected() } returns true
        every { service.getAccounts() } returns Instancio.createList(Account::class.java)

        mockMvc.perform(get("/tradingclient/api/accounts"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect { e -> e.response. }
//            .andExpect(jsonPath("[0]").isArray)
    }

}