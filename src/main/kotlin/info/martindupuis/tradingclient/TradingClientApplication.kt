package info.martindupuis.tradingclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["info.martindupuis.tradingclient", "info.martindupuis.jquestrade"])
class TradingClientApplication

fun main(args: Array<String>) {
	runApplication<TradingClientApplication>(*args)
}
