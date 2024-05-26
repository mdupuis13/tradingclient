package info.martindupuis.tradingclient.model

/**
 * Individual	Account held by an individual.
 * Joint	Account held jointly by several individuals (e.g., spouses).
 * Informal Trust	Non-individual account held by an informal trust.
 * Corporation	Non-individual account held by a corporation.
 * Investment Club	Non-individual account held by an investment club.
 * Formal Trust	Non-individual account held by a formal trust.
 * Partnership	Non-individual account held by a partnership.
 * Sole Proprietorship	Non-individual account held by a sole proprietorship.
 * Family	Account held by a family.
 * Joint and Informal Trust	Non-individual account held by a joint and informal trust.
 * Institution	Non-individual account held by an institution.
 *
 * https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#client-account-type
 */
enum class ClientAccountType {
    Individual
}