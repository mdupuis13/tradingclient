package info.martindupuis.tradingclient.portsadapters.questradeclient

import info.martindupuis.tradingclient.portsadapters.questradeclient.entities.QuestradeRefreshToken
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RepositoryImplTest {

    @Test
    fun givenValidRepo_whenGetRefreshTokenIsCalled_thenTheGivenTokenIsValid() {
        val sut: Repository = RepositoryImpl()

        assertThat(sut.getRefeshToken()).isInstanceOf(QuestradeRefreshToken::class.java)
    }
}