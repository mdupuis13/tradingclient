package info.martindupuis.tradingclient.portsadapters.questradeclient

import com.jquestrade.client.QuestradeWebClient as LibQuestrade

class ServiceImpl(val questradeLib: LibQuestrade) : Service {
    private var isConnectedToAPI = false

    override fun isConnected(): Boolean {
        return isConnectedToAPI
    }

    override fun connect() {
        isConnectedToAPI = true
    }

    override fun getAccounts() = questradeLib.getAccounts(null).toList()
}