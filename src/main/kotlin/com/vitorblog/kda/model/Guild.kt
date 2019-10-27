package com.vitorblog.kda.model

import org.json.simple.JSONObject

class Guild(val id:String, var name:String, var roles:ArrayList<String>, var region:String, var boostLevel:Int=0) {

    companion object {

        fun load(json:JSONObject):Guild{
            return Guild(json.getString("id")!!, json.getString("name")!!, arrayListOf(), json.getString("region")!!)
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

}