package com.example.applicationwillwork.Models

class MessageClass {
    var sender: String? = null
        private set
    var message: String? = null
        private set

    constructor()
    constructor(sender: String?, message: String?) {
        this.sender = sender
        this.message = message
    }
}