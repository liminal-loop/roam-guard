package com.roamguard.data.local.mcc

import android.content.Context
import com.roamguard.mcc.MccEntry
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MccDatasetLoader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val defaultEntries: List<MccEntry> by lazy {
        mutableListOf<MccEntry>().apply {
            val mccData = getMccDataFromAssets()
            if (mccData != null) {
                addAll(mccData)
            } else {
                addAll(builtInEntries())
            }
        }
    }

    fun getEntries(): List<MccEntry> = defaultEntries

    suspend fun fetchFromUrl(url: String): List<MccEntry>? {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()
            reader.close()

            parseJsonResponse(response)
        } catch (e: Exception) {
            null
        }
    }

    private fun parseJsonResponse(json: String): List<MccEntry>? {
        return try {
            val jsonArray = JSONArray(json)
            (0 until jsonArray.length()).map { i ->
                val obj = jsonArray.getJSONObject(i)
                MccEntry(
                    mcc = obj.getInt("mcc"),
                    mnc = obj.optInt("mnc", -1).takeIf { it >= 0 },
                    countryCode = obj.getString("country_code"),
                    countryName = obj.getString("country_name"),
                    operatorName = obj.optString("operator_name", "").ifEmpty { null }
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun getMccDataFromAssets(): List<MccEntry>? {
        return try {
            val inputStream = context.assets.open("mcc_dataset.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val json = reader.readText()
            reader.close()
            parseJsonResponse(json)
        } catch (e: Exception) {
            null
        }
    }

    private fun builtInEntries(): List<MccEntry> {
        return com.roamguard.mcc.MccDataset.getEntries()
    }
}
