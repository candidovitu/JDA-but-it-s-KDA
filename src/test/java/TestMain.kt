import com.vitorblog.kda.builder.BotBuilder

class TestMain {

    companion object {
        val token = "NjM3NzU5NDAwODgyNDcwOTEy.XbS17Q.8XJQNq8_VCWZmdID7dAQdqEy9zY"

        @JvmStatic
        fun main(args:Array<String>){
            println("Starting bot...")
            val bot = BotBuilder()
                .setToken(token)
                .addListener(TestHandler())
                .start()
        }

    }

}