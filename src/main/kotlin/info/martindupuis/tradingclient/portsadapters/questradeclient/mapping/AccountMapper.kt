package info.martindupuis.tradingclient.portsadapters.questradeclient.mapping

import info.martindupuis.jquestrade.QuestradeAccount
import info.martindupuis.jquestrade.QuestradeActivity
import info.martindupuis.tradingclient.model.Account
import info.martindupuis.tradingclient.model.AccountActivity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface AccountMapper {
    fun map(source: QuestradeAccount): Account
    fun map(source: Set<QuestradeAccount>): Set<Account>

    fun map(source: Set<QuestradeActivity>): Set<AccountActivity>
}