package com.example.somnum.data.repository

import android.util.Log
import com.example.somnum.data.network.supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

class PlannerRepository {

    suspend fun fetchPlanningForADay(date: LocalDate, userId: String): List<Planner> {
        Log.i("TAG", "Ceci est un message d'information : ${date}")
        val test = supabase
            .from("planner")
            .select {
                filter {
                    //TODO : filtrer par la date et par le userID
                    eq("date",date)
                    eq("userId",userId)
                }
            }
            .decodeList<Planner>()
        Log.i("TAG","Supabase return : $test")
        return test
    }

    suspend fun deletePlanning(id: Int) {
        supabase
            .from("planner")
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }
}

@Serializable
data class Planner(
    val id: Int? = null,
    val date: String,           // Format ISO: "YYYY-MM-DDTHH:mm:ss"
    @SerialName("sleepTime")
    val sleepTime: String,      // Format ISO: "YYYY-MM-DDTHH:mm:ss"
    @SerialName("wakeTime")
    val wakeTime: String,       // Format ISO: "YYYY-MM-DDTHH:mm:ss"
    @SerialName("createdAt")
    val createdAt: String? = null,  // Format ISO timestamp
    @SerialName("userId")
    val userId: String
) {
    fun getSleepTime(): Any {
     return sleepTime
    }

    fun getWakeTime(): Any {
        return getWakeTime()
    }

    fun getSleepDuration(): Any {
        return getSleepDuration()
    }
}
