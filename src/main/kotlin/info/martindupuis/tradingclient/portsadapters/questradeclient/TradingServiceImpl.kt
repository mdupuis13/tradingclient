package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.jquestrade.AuthenticationToken
import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import info.martindupuis.tradingclient.portsadapters.questradeclient.mapping.AccountMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
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
}