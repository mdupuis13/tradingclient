package info.martindupuis.tradingclient.model

/**
 * Cash	Cash account.
 * Margin	Margin account.
 * TFSA	Tax Free Savings Account.
 * RRSP	Registered Retirement Savings Plan.
 * FHSA	First Home Savings Account.
 * SRRSP	Spousal RRSP.
 * LRRSP	Locked-In RRSP.
 * LIRA	Locked-In Retirement Account.
 * LIF	    Life Income Fund.
 * RIF	    Retirement Income Fund.
 * SRIF	Spousal RIF.
 * LRIF	Locked-In RIF.
 * RRIF	Registered RIF.
 * PRIF	Prescribed RIF.
 * RESP	Individual Registered Education Savings Plan.
 * FRESP	Family RESP.
 *
 * https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#account-type
 */
enum class AccountType {
    TFSA,
    RRSP
}