package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import java.time.ZonedDateTime

class RepositoryImpl : Repository {
    override fun getRefeshToken(): QuestradeRefreshToken = QuestradeRefreshToken("", "", ZonedDateTime.now(), "")
}