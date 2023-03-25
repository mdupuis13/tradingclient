package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken

interface QuestradeRepository {
    fun getRefeshToken(): QuestradeRefreshToken

}
