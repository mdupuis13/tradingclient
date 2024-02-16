package info.martindupuis.tradingclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TradingClientApplication

fun main(args: Array<String>) {
	runApplication<TradingClientApplication>(*args)
}
