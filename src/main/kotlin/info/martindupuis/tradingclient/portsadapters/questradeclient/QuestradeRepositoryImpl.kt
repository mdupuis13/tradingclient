package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken

class QuestradeRepositoryImpl : QuestradeRepository {
    override fun getRefeshToken(): QuestradeRefreshToken {
        return QuestradeRefreshToken("","", "")
    }
}