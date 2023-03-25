package info.martindupuis.tradingclient.portsadapters.questradeclient

class QuestradeServiceImpl: QuestradeService {
    private var isConnectedToAPI = false

    override fun isConnected(): Boolean {
        return isConnectedToAPI
    }

    override fun connect() {
        isConnectedToAPI = true
    }
}