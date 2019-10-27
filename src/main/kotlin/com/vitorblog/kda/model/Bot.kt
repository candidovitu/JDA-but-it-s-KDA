package com.vitorblog.kda.model

import com.neovisionaries.ws.client.WebSocketFactory
import com.neovisionaries.ws.client.WebSocket
import com.vitorblog.kda.dao.GuildDao
import com.vitorblog.kda.handler.WebSocketHandler
import com.vitorblog.kda.manager.EventManager
import com.vitorblog.kda.model.event.ListenerHandler


class Bot {

    var token = ""
    var selfUser:User? = null
    val eventManager = EventManager(this)
    var listeners = ArrayList<ListenerHandler>()

    /* DAO */
    val guildDao = GuildDao()

    fun start(){
        startWebSocket()
    }

    fun startWebSocket(){
        println("[WS] Starting...")

        val ws = WebSocketFactory().createSocket("wss://gateway.discord.gg")
        ws.addListener(WebSocketHandler(this))
        ws.connect()
    }

}