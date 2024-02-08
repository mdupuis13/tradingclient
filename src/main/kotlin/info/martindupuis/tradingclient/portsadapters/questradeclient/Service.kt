package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.Account
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken

interface Service {
    fun isConnected(): Boolean
    fun connect(token: QuestradeRefreshToken)
    fun getAccounts(): List<Account>
}