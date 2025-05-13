package com.example.socialnetworkapp.model
data class User(
    val userID: String = "",
    val email: String = "",
    val userName: String = "",
    val avatarUrl: String = "",
    val bio: String = "",
    val createAt: String = "",
    val followerCount : Int = 0,
    val followingCount: Int = 0,
    val postCount: Int = 0
)