package com.example.applicationwillwork.Models

class User {
    var uid: String? = null
    var name: String? = null
    var mail: String? = null
    var age: String? = null
    var firstName: String? = null
    var lastName: String? = null
    fun setUID(uid: String?) {
        this.uid = uid
    }

    override fun toString(): String {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ",mail='" + mail + '\'' +
                '}'
    }
}