package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken

class RepositoryImpl : Repository {
    override fun getRefeshToken(): QuestradeRefreshToken {
        return QuestradeRefreshToken("", "", "")
    }
}