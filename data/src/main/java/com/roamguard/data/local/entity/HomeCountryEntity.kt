package com.roamguard.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_country")
data class HomeCountryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "mcc")
    val mcc: Int,
    @ColumnInfo(name = "country_name")
    val countryName: String,
    @ColumnInfo(name = "country_code")
    val countryCode: String
)
