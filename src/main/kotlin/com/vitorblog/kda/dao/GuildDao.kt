package com.vitorblog.kda.dao

import com.vitorblog.kda.model.Guild

class GuildDao {

    val GUILDS = hashMapOf<String, Guild>()

    fun add(guild:Guild){
        GUILDS.put(guild.id, guild)
    }

    fun get(id:String):Guild? = GUILDS.get(id)

}