package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.AuthenticationToken
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import info.martindupuis.client.QuestradeWebClient as LibQuestrade

class ServiceImpl(private val tradingPlatform: LibQuestrade) : Service {

    private lateinit var authenticationToken: AuthenticationToken

    private var isConnectedToAPI = false

    override fun isConnected(): Boolean {
        return isConnectedToAPI
    }

    override fun connect(token: QuestradeRefreshToken) {
        authenticationToken = tradingPlatform.authenticate(token.refresh_token)

        if (authenticationToken.isValid)
            isConnectedToAPI = true
    }

    override fun getAccounts() = tradingPlatform.getAccounts(authenticationToken).toList()
}