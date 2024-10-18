package com.example.somnum.ui.screens

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.somnum.activities.LoginActivity
import com.example.somnum.activities.MainActivity
import com.example.somnum.activities.ProfileActivity
import com.example.somnum.data.entities.Planner
import com.example.somnum.data.network.supabase
import com.example.somnum.data.repository.PlannerRepository
import com.example.somnum.ui.components.ProfileField
import com.example.somnum.ui.components.ProfileHeader
import com.example.somnum.ui.viewmodel.LoginViewModel
import com.example.somnum.ui.viewmodel.PlannerViewModel
import com.example.somnum.viewmodel.ProfileViewModel
import io.github.jan.supabase.gotrue.auth
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    loginViewModel: LoginViewModel,
    plannerRepository: PlannerRepository
) {
    val context = LocalContext.current
    val user = supabase.auth.currentUserOrNull()

    val userProfile = profileViewModel.userProfileInfos
    val isLoading = profileViewModel.isLoading
    var lastPlanning by remember { mutableStateOf<List<Planner>?>(null) }

    LaunchedEffect(Unit) {
        profileViewModel.loadUserProfile()
        if (user?.id != null) {
            lastPlanning = plannerRepository.fetchPlanningForADay(LocalDate.now(), user.id)
            if (lastPlanning!!.size == 0) {
                lastPlanning = null
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (isLoading.value) {
            Text(text = "Loading...", fontSize = 16.sp)
        } else {
            ProfileHeader(
                fullName = userProfile?.fullName ?: "Full Name",
                bio = userProfile?.bio ?: "Bio",
                profileImageUrl = null
            )
        }

        val createdAt = user?.createdAt.let {
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            val parsedDate = OffsetDateTime.parse(it.toString())
            parsedDate.format(formatter)
        } ?: "Date inconnue"

        ProfileField(label = "Email", value = user?.email ?: "Data unavailable")
        ProfileField(
            label = "Last sleep time",
            value = lastPlanning?.get(0)?.sleepTime ?: "Data unavailable"
        )
        ProfileField(
            label = "Last wake time",
            value = lastPlanning?.get(0)?.wakeTime ?: "Data unavailable"

        )

        Text(
            text = "Member since $createdAt",
            modifier = Modifier.padding(bottom = 4.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as ProfileActivity).finish()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Return to Home")
        }

        Button(
            onClick = {
                loginViewModel.logout()
                context.startActivity(Intent(context, LoginActivity::class.java))
                (context as ProfileActivity).finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Logout")
        }
    }
}