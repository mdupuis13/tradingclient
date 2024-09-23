package info.martindupuis.tradingclient.api

import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.model.AccountActivity
import info.martindupuis.tradingclient.portsadapters.questradeclient.TradingService
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.ZonedDateTime

@RestController
class AccountsController(val service: TradingService) {

    @GetMapping("/tradingclient/api/accounts")
    fun getAccounts(@RequestParam("refresh_token") refreshToken: String): ResponseEntity<Set<Account>> {
        verifyConnection(refreshToken)

        val accounts = service.getAccounts()

        return ResponseEntity.ok(accounts)
    }

    @GetMapping("/tradingclient/api/accounts/{account_id}/activities")
    fun getAccountActivitiess(@PathVariable account_id: String,
                              @RequestParam("refresh_token") refreshToken: String,
                              @RequestParam("start_date") startDate: LocalDateTime,
                              @RequestParam("end_date") endDate: LocalDateTime): ResponseEntity<Set<AccountActivity>> {
        verifyConnection(refreshToken)

        val activities = service.getAccountActivities(account_id,startDate,endDate)

        return ResponseEntity.ok(activities)
    }

    private fun verifyConnection(refreshToken: String) {
        val token = createToken(refreshToken)

        if (!service.isConnected())
            service.connect(token)
    }

    private fun createToken(refreshToken: String): QuestradeRefreshToken {
        val token = QuestradeRefreshToken("", "", ZonedDateTime.now(), refreshToken)
        return token
    }
}