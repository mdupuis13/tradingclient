package info.martindupuis.tradingclient.portsadapters.questradeclient.entities

import org.amshove.kluent.`should be`
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class QuestradeRefreshTokenTest {
    @Test
    fun whenInstanceCreated_shouldContainData() {
        val sut = QuestradeRefreshToken("a","b", "c")

        assertThat(sut.access_token).isEqualTo("a")
        assertThat(sut.api_server).isEqualTo("b")
        assertThat(sut.refresh_token).isEqualTo("c")
    }
}