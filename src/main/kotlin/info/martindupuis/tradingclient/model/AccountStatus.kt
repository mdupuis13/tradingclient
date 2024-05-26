package info.martindupuis.tradingclient.model

/**
 * Active
 * Suspended (Closed)
 * Suspended (View Only)
 * Liquidate Only
 * Closed
 *
 * https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#account-status
 */
enum class AccountStatus {
    Active,
    SuspendedClosed,
    SuspendedViewOnly,
    LiquidateOnly,
    Closed
}