package info.martindupuis.tradingclient.portsadapters.questradeclient

interface QuestradeService {
    fun isConnected(): Boolean
    fun connect(refreshToken: String)
}