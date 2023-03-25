package info.martindupuis.tradingclient.portsadapters.questradeclient.entities

class QuestradeRefreshToken(
        val access_token: String,
        val api_server: String,
        val refresh_token: String) {}

/*
{
    "access_token": "9SL7_TBDglIkns25VvRCJR8DARzyzRpQ0",
    "api_server": "https://api07.iq.questrade.com/",
    "expires_in": 1800,
    "refresh_token": "53bHzILSSv1O6KLvE0rFA1ibJcHi7gKc0",
    "token_type": "Bearer"
}*/
