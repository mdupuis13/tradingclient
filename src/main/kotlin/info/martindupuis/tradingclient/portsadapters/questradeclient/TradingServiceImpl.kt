package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.jquestrade.Account
import info.martindupuis.jquestrade.AuthenticationToken
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import info.martindupuis.jquestrade.client.QuestradeWebClient as LibQuestrade

@Service
class TradingServiceImpl(private val tradingPlatform: LibQuestrade) : TradingService {

    private val log = LoggerFactory.getLogger(javaClass)

    private lateinit var authenticationToken: AuthenticationToken

    private var isConnectedToAPI = false

    override fun isConnected(): Boolean {
        return isConnectedToAPI
    }

    override fun connect(token: QuestradeRefreshToken) {
        authenticationToken = tradingPlatform.authenticate(token.refresh_token)
        log.info(authenticationToken.toString())

        if (authenticationToken.isValid)
            isConnectedToAPI = true
    }

    override fun getAccounts(): List<Account> = tradingPlatform.getAccounts(authenticationToken)
}