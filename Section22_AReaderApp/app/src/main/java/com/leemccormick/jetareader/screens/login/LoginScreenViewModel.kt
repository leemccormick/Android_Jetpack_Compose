package com.leemccormick.jetareader.screens.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.ImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.leemccormick.jetareader.model.MUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Create User in Database
                        val displayName = task.result?.user?.email?.split("@")?.get(0)
                        createUser(displayName = displayName)
                        home()
                    } else {
                        Log.d(
                            "FB",
                            "createUserWithEmailAndPassword ${task.result.toString()} | Yayyy, We can go home....."
                        )
                    }

                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        // User before MUser --> Save this to database
        // val user = mutableMapOf<String, Any>()
        // user["user_id"] = userId.toString()
        // user["display_name"] = displayName.toString()

        // Using MUser Object to save in database
        val user = MUser(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great.",
            profession = "Mobile Developer",
            id = null
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(
                                "FB",
                                "signInWithEmailAndPassword ${task.result.toString()} | Yayyy, We can go home....."
                            )

                            // Take them home using Lambda here...
                            home()
                        } else {
                            Log.d(
                                "FB",
                                "signInWithEmailAndPassword ${task.result.toString()} | Opp, Failed to sign in...."
                            )
                        }
                    }
            } catch (ex: Exception) {
                Log.d("FB", "signInWithEmailAndPassword ${ex.message}")
            }
        }
}



