package net.azarquiel.innocv.model

import java.io.Serializable
import java.util.*

data class User (
    val id:Int,
    val name:String,
    var birthdate:String
): Serializable