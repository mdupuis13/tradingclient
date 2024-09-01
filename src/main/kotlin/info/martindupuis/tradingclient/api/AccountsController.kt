package info.martindupuis.tradingclient.api

import info.martindupuis.jquestrade.Account
import info.martindupuis.tradingclient.portsadapters.questradeclient.TradingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountsController(val service: TradingService) {

    @GetMapping("/tradingclient/api/accounts")
    fun accounts(): List<Account> = service.getAccounts()
}