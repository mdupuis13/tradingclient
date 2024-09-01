package info.martindupuis.tradingclient.api

import info.martindupuis.jquestrade.Account
import info.martindupuis.tradingclient.portsadapters.questradeclient.TradingService
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class AccountsController(val service: TradingService) {

    @GetMapping("/tradingclient/api/accounts")
    fun accounts(@RequestParam("refresh_token") refreshToken: String): List<Account> {
        if (!service.isConnected())
            service.connect(QuestradeRefreshToken("", "", ZonedDateTime.now(), refreshToken))

        return service.getAccounts()
    }

}