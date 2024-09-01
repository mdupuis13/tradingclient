package info.martindupuis.tradingclient.portsadapters.questradeclient.entities

import java.time.ZonedDateTime

class QuestradeRefreshToken(
    val accessToken: String,
    val apiServer: String,
    val expiresAt: ZonedDateTime,
    val refreshToken: String
) {}

