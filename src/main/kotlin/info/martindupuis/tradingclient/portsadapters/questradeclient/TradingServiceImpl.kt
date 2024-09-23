package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.jquestrade.AuthenticationToken
import info.martindupuis.jquestrade.client.RequestPeriod
import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.model.AccountActivity
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import info.martindupuis.tradingclient.portsadapters.questradeclient.mapping.AccountMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import info.martindupuis.jquestrade.client.QuestradeWebClient as LibQuestrade

@Service
class TradingServiceImpl(
    private val tradingPlatform: LibQuestrade,
    private val mapper: AccountMapper
) : TradingService {

    private val log = LoggerFactory.getLogger(javaClass)

    private lateinit var authenticationToken: AuthenticationToken

    private var isConnectedToAPI = false

    override fun isConnected(): Boolean {
        return isConnectedToAPI
    }

    override fun connect(token: QuestradeRefreshToken) {
        authenticationToken = tradingPlatform.authenticate(token.refreshToken)
        log.info(authenticationToken.toString())

        if (authenticationToken.isValid)
            isConnectedToAPI = true
    }

    override fun getAccounts(): Set<Account> {
        val qAccounts = tradingPlatform.getAccounts(authenticationToken)

        return mapper.map(qAccounts)
    }

    override fun getAccountActivities(accountId: String, startDate: LocalDateTime, endDate: LocalDateTime): Set<AccountActivity> {
        val qAccount = tradingPlatform.getAccounts(authenticationToken).firstOrNull { acct -> acct.number == accountId }

        if (qAccount == null)
            throw IllegalArgumentException("TradingClient: Account not found !")

        val requestPeriod = RequestPeriod(startDate.atZone(ZoneId.systemDefault()), endDate.atZone(ZoneId.systemDefault()))

        val acctActivities = tradingPlatform.getAccountActivities(authenticationToken, qAccount, requestPeriod)
        return mapper.map(acctActivities)
    }
}