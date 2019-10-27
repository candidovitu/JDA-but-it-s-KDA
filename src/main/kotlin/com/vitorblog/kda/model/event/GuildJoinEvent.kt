package com.vitorblog.kda.model.event

import com.vitorblog.kda.model.Bot
import com.vitorblog.kda.model.Guild

class GuildJoinEvent(val bot:Bot, val guild:Guild) : Event