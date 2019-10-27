package com.vitorblog.kda.builder

import com.vitorblog.kda.model.Bot
import com.vitorblog.kda.model.event.ListenerHandler

class BotBuilder {

    val bot = Bot()

    fun setToken(token:String):BotBuilder {
        bot.token = token

        return this
    }

    fun addListener(listenerHandler:ListenerHandler):BotBuilder {
        bot.listeners.add(listenerHandler)

        return this
    }

    fun build():Bot = bot

    fun start():Bot {
        bot.start()

        return bot
    }

}