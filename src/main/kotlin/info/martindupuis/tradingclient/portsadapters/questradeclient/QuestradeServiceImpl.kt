package info.martindupuis.tradingclient.portsadapters.questradeclient

class QuestradeServiceImpl: QuestradeService {
    private var isConnectedToAPI = false

    override fun isConnected(): Boolean {
        return isConnectedToAPI
    }

    override fun connect(refreshToken: String) {
        isConnectedToAPI = true
    }
}