package com.route.mychatapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.route.mychatapp.Common.SingleLiveEvent
import com.route.mychatapp.firebase.usersDao
import com.route.mychatapp.ui.message
import com.route.mychatapp.ui.sessionProvider

class LoginViewModel : ViewModel() {
    val MessageLiveData = SingleLiveEvent<message>()
    val isLoading = MutableLiveData<Boolean>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    val events = SingleLiveEvent<LoginEvents>()

    val auth = Firebase.auth

    fun login() {
        if (!ValidForm()) return
        isLoading.value = true

        auth.signInWithEmailAndPassword(
            email.value!!,
            password.value!!
        ).addOnCompleteListener { Task ->

            if (Task.isSuccessful) {
                getUserFromFirestore(Task.result.user?.uid)
            } else {

            }
        }
    }

    private fun getUserFromFirestore(uid:String?) {
        usersDao.getUser(uid){
            Task->
            isLoading.value = false
            if (Task.isSuccessful)
            {
                val user = Task.result.toObject(com.route.mychatapp.model.User::class.java)
                sessionProvider.user = user
                MessageLiveData.postValue(
                    message(
                        message = "User Login Succesfull",
                        posActionName = "ok",
                        OnPosAction = {
                            events.postValue(LoginEvents.NavigateToHome)

                                      // Navigat To Home
                        },
                        IsCancelable = false
                    )
                )
            }

            else{
                MessageLiveData.postValue(
                    message(
                        message = Task.exception?.localizedMessage
                    )
                )
            }
        }
    }

    fun ValidForm(): Boolean {
            var isValid = true

            if (email.value.isNullOrBlank()) {
                emailError.postValue("EmailNotValid")
                isValid = false
            } else {
                emailError.postValue(null)
            }
            if (password.value.isNullOrBlank()) {
                passwordError.postValue("PasswordNotValid")
                isValid = false
            } else {
                passwordError.postValue(null)
            }

            return isValid
        }


    fun navigateToRegister(){
        events.postValue(LoginEvents.NavigateToRegister)
    }

    }
//MessageLiveData.postValue(
//ChatAppError(
//message = Task.result.user?.uid
//)
//)