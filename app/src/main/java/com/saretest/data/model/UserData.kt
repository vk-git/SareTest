package com.saretest.data.model

import java.io.Serializable

data class UserData(
    val coverImage: String,
    val description: String,
    val follow: Boolean,
    val fragmentImages: List<FragmentImage>,
    val fullName: String,
    val likeCount: Int,
    val numberFollowers: String,
    val numberFollowing: String,
    val profileImage: String,
    val username: String,
    val videoCount: Int
): Serializable