package info.martindupuis.tradingclient.api

import info.martindupuis.tradingclient.portsadapters.questradeclient.TradingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tradingclient/api")
class AccountController(val service: TradingService) {

    @GetMapping("/accounts")
    fun accounts() = service.getAccounts()
}