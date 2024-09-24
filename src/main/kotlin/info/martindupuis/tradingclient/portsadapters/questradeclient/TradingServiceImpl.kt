package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.jquestrade.AuthenticationToken
import info.martindupuis.jquestrade.QuestradeActivity
import info.martindupuis.jquestrade.client.RequestPeriod
import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.model.AccountActivity
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import info.martindupuis.tradingclient.portsadapters.questradeclient.mapping.AccountActivitiesMapper
import info.martindupuis.tradingclient.portsadapters.questradeclient.mapping.AccountMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import info.martindupuis.jquestrade.client.QuestradeWebClient as LibQuestrade

@Service
class TradingServiceImpl(
    private val tradingPlatform: LibQuestrade,
    private val accountMapper: AccountMapper,
    private val accountActivitiesMapper: AccountActivitiesMapper
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

        return accountMapper.map(qAccounts)
    }

    override fun getAccountActivities(accountId: String, startDate: LocalDateTime, endDate: LocalDateTime): Set<AccountActivity> {
        val qAccount = tradingPlatform.getAccounts(authenticationToken).firstOrNull { acct -> acct.number == accountId }

        if (qAccount == null)
            throw IllegalArgumentException("TradingClient: Account not found !")

        val requestPeriod = RequestPeriod(startDate.atZone(ZoneId.systemDefault()), endDate.atZone(ZoneId.systemDefault()))

        val acctActivities: MutableSet<QuestradeActivity> = mutableSetOf()

        var nextPeriodStart = requestPeriod.periodStart
        var nbDaysLeft = requestPeriod.numberDaysInBetween()

        do {
            val nbDaysInPeriod: Long = if (nbDaysLeft < 30) nbDaysLeft else 30
            val period = RequestPeriod(nextPeriodStart, nextPeriodStart.plusDays(nbDaysInPeriod))

            nextPeriodStart = nextPeriodStart.plusDays(nbDaysInPeriod+1)

            val activities = tradingPlatform.getAccountActivities(authenticationToken, qAccount, period)
            acctActivities.addAll(activities)

            log.info(activities.sortedBy { act -> act.transactionDate }.toString())

            nbDaysLeft -= nbDaysInPeriod

        } while (nbDaysLeft / 30 >= 1)

        return accountActivitiesMapper.map(acctActivities)
    }
}