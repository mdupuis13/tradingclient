package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class QuestradeRepositoryImplTest {

    @Test
    fun givenValidRepo_whenIgetRefeshToken_thenTheGivenTokenIsValid() {
        val sut: QuestradeRepository = QuestradeRepositoryImpl()

        assertThat(sut.getRefeshToken()).isInstanceOf(QuestradeRefreshToken::class.java)
    }
}