package info.martindupuis.tradingclient.portsadapters.questradeclient.mapping

import info.martindupuis.tradingclient.model.Account
import org.mapstruct.Mapper
import info.martindupuis.jquestrade.Account as jqAccount

@Mapper(componentModel = "spring")
interface AccountMapper {
    fun map(source: jqAccount): Account
    fun map(source: Set<jqAccount>): Set<Account>
}