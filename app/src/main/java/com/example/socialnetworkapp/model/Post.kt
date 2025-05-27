package com.example.socialnetworkapp.model

import java.sql.Timestamp

data class Post(
    val postId: String = "",
    val userId: String = "",
    val content: String = "",
    val imageUrls: List<String> = listOf<String>(),
    val hashtags: List<String> =listOf<String>(),
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),
    val likeCount: Int = 0,
    val commentCount: Int = 0,
)