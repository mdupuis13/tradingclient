package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.jquestrade.Account
import info.martindupuis.jquestrade.AuthenticationToken
import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import info.martindupuis.jquestrade.client.QuestradeWebClient as LibQuestrade

@Service
class TradingServiceImpl(private val tradingPlatform: LibQuestrade) : TradingService {

    private val log = LoggerFactory.getLogger(javaClass)

    private lateinit var platformToken: QuestradeRefreshToken

    override fun isConnected(): Boolean = ::platformToken.isInitialized && tradingPlatformTokenFrom(platformToken).isValid


    override fun connect(token: QuestradeRefreshToken) {
        val tradingPlatformToken = tradingPlatform.authenticate(token.refreshToken)
        log.info(tradingPlatformToken.toString())

        platformToken = questradeRefreshTokenFrom(tradingPlatformToken)
    }

    override fun getAccounts(): List<Account> = tradingPlatform.getAccounts(tradingPlatformTokenFrom(platformToken)).toList()


    fun tradingPlatformTokenFrom(refreshToken: QuestradeRefreshToken): AuthenticationToken =
        AuthenticationToken(
            refreshToken.accessToken,
            refreshToken.apiServer,
            refreshToken.expiresAt,
            refreshToken.refreshToken,
            null
        )


    fun questradeRefreshTokenFrom(tradingPlatformToken: AuthenticationToken): QuestradeRefreshToken =
        QuestradeRefreshToken(
            tradingPlatformToken.access_token,
            tradingPlatformToken.api_server,
            tradingPlatformToken.expires_at,
            tradingPlatformToken.refresh_token
        )
}