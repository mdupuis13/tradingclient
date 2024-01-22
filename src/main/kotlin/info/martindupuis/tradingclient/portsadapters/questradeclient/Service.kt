package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.Account

interface Service {
    fun isConnected(): Boolean
    fun connect()
    fun getAccounts(): List<Account>
}