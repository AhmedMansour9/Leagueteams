package com.leagueteams.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@SuppressLint("ParcelCreator")
@Parcelize
data class Teams_Response(
    @SerializedName("competition")
    val competition: Competition,
    @SerializedName("count")
    val count: Int,
    @SerializedName("filters")
    val filters: Filters,
    @SerializedName("season")
    val season: Season,
    @SerializedName("teams")
    val teams: List<Team>
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Competition(
        @SerializedName("area")
        val area: Area,
        @SerializedName("code")
        val code: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("lastUpdated")
        val lastUpdated: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("plan")
        val plan: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Area(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("name")
            val name: String?
        ) : Parcelable
    }

    @SuppressLint("ParcelCreator")
    @Parcelize
    class Filters(
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Season(
        @SerializedName("currentMatchday")
        val currentMatchday: Int?,
        @SerializedName("endDate")
        val endDate: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("startDate")
        val startDate: String?,
        @SerializedName("winner")
        val winner: String?
    ) : Parcelable

    @Entity(tableName = "Team")
    @SuppressLint("ParcelCreator")
    @Parcelize
   public data class Team(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id_primary")
        var id_primary: Int = 0,

        @ColumnInfo(name="clubColors")
        @SerializedName("clubColors")
        var clubColors: String? = null,

        @ColumnInfo(name="id")
        @SerializedName("id")
         var id: Int = 0,
        @ColumnInfo(name="name")
        @SerializedName("name")
        var name: String? = null,
        @ColumnInfo(name="shortName")
        @SerializedName("shortName")
        var shortName: String? = null,
        @ColumnInfo(name="venue")
        @SerializedName("venue")
        var venue: String? = null,
        @ColumnInfo(name="website")
        @SerializedName("website")
        var website: String? = null
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Area(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("name")
            val name: String?
        ) : Parcelable
    }
}