package com.vitorblog.kda.handler

import com.neovisionaries.ws.client.*
import com.vitorblog.kda.model.Bot
import com.vitorblog.kda.model.Guild
import com.vitorblog.kda.model.User
import com.vitorblog.kda.model.event.GuildJoinEvent
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.util.*

class WebSocketHandler(val bot:Bot) : WebSocketListener {

    var socket:WebSocket? = null
    var connected = false

    @Throws(Exception::class)
    override
    fun onStateChanged(websocket: WebSocket, newState: WebSocketState) {
    }


    @Throws(Exception::class)
    override
    fun onConnected(websocket: WebSocket, headers: Map<String, List<String>>) {
        socket = websocket
        connected = true
        println("[WS] Connected!")

        sendIdentify()
    }


    @Throws(Exception::class)
    override
    fun onConnectError(websocket: WebSocket, exception: WebSocketException) {
        exception.printStackTrace()
    }


    @Throws(Exception::class)
    override
    fun onDisconnected(websocket: WebSocket, serverCloseFrame: WebSocketFrame, clientCloseFrame: WebSocketFrame, closedByServer: Boolean) {
        connected = false
        if (closedByServer){
            println("[WS] Disconnected, reconnecting")
            socket = websocket.recreate()
            socket!!.connect()
        }
    }


    @Throws(Exception::class)
    override
    fun onFrame(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onContinuationFrame(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onTextFrame(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onBinaryFrame(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onCloseFrame(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onPingFrame(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onPongFrame(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onTextMessage(websocket: WebSocket, text: String) {
        println(" « $text")

        handleEvent(text)
    }


    @Throws(Exception::class)
    override
    fun onBinaryMessage(websocket: WebSocket?, p1: kotlin.ByteArray) {
    }


    @Throws(Exception::class)
    override
    fun onSendingFrame(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onFrameSent(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onFrameUnsent(websocket: WebSocket, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onError(websocket: WebSocket, cause: WebSocketException) {
        cause.printStackTrace()
    }


    @Throws(Exception::class)
    override
    fun onFrameError(websocket: WebSocket, cause: WebSocketException, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onMessageError(websocket: WebSocket, cause: WebSocketException, frames: List<WebSocketFrame>) {
    }


    @Throws(Exception::class)
    override
    fun onMessageDecompressionError(websocket: WebSocket, cause: WebSocketException, compressed: kotlin.ByteArray) {
    }

    @Throws(Exception::class)
    override
    fun onTextMessage(websocket: WebSocket, data: kotlin.ByteArray) {
    }

    override
    fun onTextMessageError(websocket: WebSocket, cause: WebSocketException, data: kotlin.ByteArray) {
    }


    @Throws(Exception::class)
    override
    fun onSendError(websocket: WebSocket, cause: WebSocketException, frame: WebSocketFrame) {
    }


    @Throws(Exception::class)
    override
    fun onUnexpectedError(websocket: WebSocket, cause: WebSocketException) {
    }


    @Throws(Exception::class)
    override
    fun handleCallbackError(websocket: WebSocket, cause: Throwable) {
    }


    @Throws(Exception::class)
    override
    fun onSendingHandshake(websocket: WebSocket, requestLine: String, headers: List<Array<String>>) {
    }


    @Throws(Exception::class)
    override
    fun onThreadCreated(websocket: WebSocket, threadType: ThreadType, thread: Thread) {
    }


    @Throws(Exception::class)
    override
    fun onThreadStarted(websocket: WebSocket, threadType: ThreadType, thread: Thread) {
    }


    @Throws(Exception::class)
    override
    fun onThreadStopping(websocket: WebSocket, threadType: ThreadType, thread: Thread) {
    }

    /* -------------------------------------------- */

    fun sendIdentify(){
        val connectionProperties = JSONObject()
        connectionProperties.set("\$os", System.getProperty("os.name"))
        connectionProperties.set("\$browser", "JDA")
        connectionProperties.set("\$device", "JDA")
        connectionProperties.set("\$referring_domain", "")
        connectionProperties.set("\$referrer", "")

        val payload = JSONObject()
        payload.set("token", bot.token)
        payload.set("properties", connectionProperties)
        payload.set("v", 6)
        payload.set("large_threshold", 250)

        val identify = JSONObject()
        identify.put("op", 2)
        identify.put("d", payload)

        identify.toJSONString().send(false)
    }

    fun String.send(queue:Boolean=true){
        if (!connected){return}

        if (queue){
            return
        }

        socket!!.sendText(this)
    }

    fun handleEvent(text: String){

        val json1 = JSONParser().parse(text) as JSONObject

        when (json1.getString("t")){
            "READY" -> {
                val json = (json1.get("d") as JSONObject).get("user") as JSONObject

                bot.selfUser = User(json.getString("id")!!, json.getString("username")!!, json.getString("discriminator")!!, json.getBoolean("verified")!!, json.getBoolean("mfa_enabled")!!, json.getString("email").toString(), json.getBoolean("bot")!!, json.getString("avatar")!!)
            }
            "GUILD_CREATE" -> {
                val json = json1.get("d") as JSONObject

                val guild = Guild.load(json)
                bot.guildDao.add(guild)

                if (!Date(json.getString("joined_at")).before(Date()))
                    bot.eventManager.executeEvent(GuildJoinEvent(bot, guild))
            }
        }


    }

    fun JSONObject.getString(d:String):String?{
        val y = this.get(d)

        if (y != null)
            return y.toString()
        return null
    }

    fun JSONObject.getBoolean(d:String):Boolean?{
        val y = this.get(d)

        if (y != null)
            return y as Boolean
        return null
    }

}