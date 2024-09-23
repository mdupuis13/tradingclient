package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.model.AccountActivity
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import org.springframework.cglib.core.Local
import java.time.LocalDateTime

interface TradingService {
    fun isConnected(): Boolean
    fun connect(token: QuestradeRefreshToken)
    fun getAccounts(): Set<Account>
    fun getAccountActivities(accountId: String, startDate: LocalDateTime, endDate: LocalDateTime): Set<AccountActivity>
}