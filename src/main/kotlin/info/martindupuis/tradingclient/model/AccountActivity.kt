package info.martindupuis.tradingclient.model

import java.math.BigDecimal
import java.time.ZonedDateTime

class AccountActivity(
    val transactionDate: ZonedDateTime,
    val action: String,
    val symbol: String,
    val symbolId: String,
    val description: String,
    val currency: String,
    val quantity: BigDecimal,
    val price: BigDecimal,
    val grossAmount: BigDecimal,
    val commission: BigDecimal,
    val netAmount: BigDecimal,
    val type: String
) {}