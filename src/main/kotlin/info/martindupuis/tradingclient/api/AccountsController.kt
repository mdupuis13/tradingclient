package info.martindupuis.tradingclient.api

import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.portsadapters.questradeclient.TradingService
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountsController(val service: TradingService) {

    @GetMapping("/tradingclient/api/accounts")
    fun getAccounts(): ResponseEntity<List<Account>> {
        val token = QuestradeRefreshToken("", "", "v_BJaDUEawUfQxKmM2KhdaWj_-dj51wD0")

        if (!service.isConnected())
            service.connect(token)

        val accounts = service.getAccounts()

        return ResponseEntity.ok(accounts)
    }
}