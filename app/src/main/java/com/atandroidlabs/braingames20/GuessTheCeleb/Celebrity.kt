package com.atandroidlabs.braingames20.GuessTheCeleb

class Celebrity(var celeb_name: String, var celeb_description: String,  var photo_id: Int, var number: Int) {

    private var celeb_no = 0

    fun getName(): String? {
        return celeb_name
    }

    fun getCeleb_no(): Int {
        return celeb_no
    }

    @JvmName("getPhoto_id1")
    fun getPhoto_id(): Int {
        return photo_id
    }

    fun getDescription(): String? {
        return celeb_description
    }

    fun getNo(): Int {
        return number
    }
}