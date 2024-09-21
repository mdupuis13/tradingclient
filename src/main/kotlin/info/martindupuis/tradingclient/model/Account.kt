package info.martindupuis.tradingclient.model

/**
 * Represents a trading account. Could be a TFSA, RRSP, cash, etc.
 *
 * @param type              The type of the account (e.g., "Cash", "Margin").
 * @param number            Eight-digit account number (e.g., "26598145").
 * @param status            The status of the account (e.g., Active).
 * @param isPrimary         Is this a primary account for the holder.
 * @param isBilling         Is this account is one that gets billed for various expenses such as fees, market data, etc.
 * @param clientAccountType The type of client holding the account (e.g., "Individual").
 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts">
 * The account properties documentation</a>
 */
class Account(
    val type: AccountType,
    val number: String,
    val status: AccountStatus,
    val isPrimary: Boolean,
    val isBilling: Boolean,
    val clientAccountType: ClientAccountType
)
