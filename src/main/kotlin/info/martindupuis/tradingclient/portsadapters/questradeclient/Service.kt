package info.martindupuis.tradingclient.portsadapters.questradeclient

import com.jquestrade.Account

interface Service {
    fun isConnected(): Boolean
    fun connect()
    fun getAccounts(): List<Account>
}