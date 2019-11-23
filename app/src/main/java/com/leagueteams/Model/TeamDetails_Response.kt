package com.leagueteams.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@SuppressLint("ParcelCreator")
@Parcelize
data class TeamDetails_Response(
    @SerializedName("activeCompetitions")
    val activeCompetitions: List<ActiveCompetition>,
    @SerializedName("address")
    val address: String,
    @SerializedName("area")
    val area: Area,
    @SerializedName("clubColors")
    val clubColors: String,
    @SerializedName("crestUrl")
    val crestUrl: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("founded")
    val founded: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastUpdated")
    val lastUpdated: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("shortName")
    val shortName: String,
    @SerializedName("squad")
    val squad: List<Squad>,
    @SerializedName("tla")
    val tla: String,
    @SerializedName("venue")
    val venue: String,
    @SerializedName("website")
    val website: String
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class ActiveCompetition(
        @SerializedName("area")
        val area: Area,
        @SerializedName("code")
        val code: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("lastUpdated")
        val lastUpdated: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("plan")
        val plan: String
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Area(
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String
        ) : Parcelable
    }

    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Area(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    ) : Parcelable

    @Entity(tableName = "Team_Details")
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Squad(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id_primary")
        var id_primary: Int = 0,

    @ForeignKey(entity = Teams_Response.Team::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("forginid"))
    @ColumnInfo(name="forginid")
        var forginid: Int=0,

        @ColumnInfo(name="id")
        @SerializedName("id")
        var id: Int=0,
        @ColumnInfo(name="name")
        @SerializedName("name")
        var name: String?=null,
        @ColumnInfo(name="nationality")
        @SerializedName("nationality")
        var nationality: String?=null,
        @ColumnInfo(name="position")
        @SerializedName("position")
        var position: String?=null,
        @ColumnInfo(name="shirtNumber")
        @SerializedName("shirtNumber")
        var shirtNumber: Int? = null
    ) : Parcelable
}