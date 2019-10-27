import com.vitorblog.kda.builder.BotBuilder
import com.vitorblog.kda.model.event.EventHandler
import com.vitorblog.kda.model.event.GuildJoinEvent
import com.vitorblog.kda.model.event.ListenerHandler

class TestHandler : ListenerHandler {

    @EventHandler
    fun onGuildJoin(e:GuildJoinEvent){
        println("Joined to ${e.guild!!.name}")
    }

}