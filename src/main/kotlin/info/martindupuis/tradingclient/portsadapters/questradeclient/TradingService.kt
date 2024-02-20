package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.jquestrade.Account
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken

interface TradingService {
    fun isConnected(): Boolean
    fun connect(token: QuestradeRefreshToken)
    fun getAccounts(): List<Account>
}