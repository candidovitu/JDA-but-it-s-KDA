package com.vitorblog.kda.manager

import com.vitorblog.kda.model.Bot
import com.vitorblog.kda.model.event.Event
import com.vitorblog.kda.model.event.EventHandler

class EventManager(val bot: Bot) {

    fun executeEvent(e:Event){
        for (listener in bot.listeners){
            val classs = listener.javaClass

            for (method in classs.methods.filter { it.getAnnotationsByType(EventHandler::class.java).isNotEmpty() }){
                val argType = method.parameterTypes.firstOrNull()

                if (argType != null && argType.equals(e::class.java)){
                    method.invoke(listener, e)
                }
            }
        }
    }

}