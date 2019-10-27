package com.vitorblog.kda.model

class User(val id:String, val username:String, val discriminator:String, val verified:Boolean, val mfa:Boolean, val email:String?, val bot:Boolean, var avatar:String) {
}