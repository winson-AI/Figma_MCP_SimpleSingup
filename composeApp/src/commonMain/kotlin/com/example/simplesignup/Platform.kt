package com.example.simplesignup

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform