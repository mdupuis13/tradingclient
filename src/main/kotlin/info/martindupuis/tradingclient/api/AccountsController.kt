package info.martindupuis.tradingclient.api

import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.portsadapters.questradeclient.TradingService
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class AccountsController(val service: TradingService) {

    @GetMapping("/tradingclient/api/accounts")
    fun getAccounts(@RequestParam("refresh_token") refreshToken: String): ResponseEntity<Set<Account>> {
        val token = QuestradeRefreshToken("", "", ZonedDateTime.now(), refreshToken)

        if (!service.isConnected())
            service.connect(token)

        val accounts = service.getAccounts()

        return ResponseEntity.ok(accounts)
    }
}