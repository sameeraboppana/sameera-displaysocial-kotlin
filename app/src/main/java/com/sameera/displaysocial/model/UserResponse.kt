package com.sameera.displaysocial.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserResponse(val data: UserData)

data class UserData(val users: List<User>)

@Entity(tableName = "USER")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int? = null,

    @ColumnInfo(name = "username")
    @SerializedName("username")
    val userName: String,

    @ColumnInfo(name = "profile_picture_url")
    @SerializedName("profile_picture_url")
    val profilePictureUrl: String,

    @ColumnInfo(name = "verified_status")
    @SerializedName("verified_status")
    val verifiedStatus: Int,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String,

    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    val fullName: String
)