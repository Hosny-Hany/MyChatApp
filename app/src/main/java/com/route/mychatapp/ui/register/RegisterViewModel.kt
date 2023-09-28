package com.route.mychatapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.route.mychatapp.Common.SingleLiveEvent
import com.route.mychatapp.firebase.usersDao
import com.route.mychatapp.model.User
import com.route.mychatapp.ui.message
import com.route.mychatapp.ui.sessionProvider

class RegisterViewModel : ViewModel() {

    val MessageLiveData = SingleLiveEvent<message>()
    val isLoading = MutableLiveData<Boolean>()

    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordconfirm = MutableLiveData<String>()

    val usernameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordconfirmError = MutableLiveData<String?>()

    val events = SingleLiveEvent<RegisterEvent>()

    val auth = Firebase.auth

    fun register() {
        if (!ValidForm()) return
        isLoading.value = true

        auth.createUserWithEmailAndPassword(
            email.value!!,
            password.value!!
        )
            .addOnCompleteListener { Task ->
                if (Task.isSuccessful)
                insertUsertoFirebase(Task.result.user?.uid)
                else {
                    //showError
                    isLoading.value = false
                    MessageLiveData.postValue(
                        message(
                            message = Task.exception?.localizedMessage
                        )
                    )
                }
            }
    }

    private fun insertUsertoFirebase(uid: String?) {
        val user = User(
            id = uid,
            UserName = username.value,
            Email = email.value
        )
        usersDao.createUser(user){
            Task ->
            if (Task.isSuccessful){
                isLoading.value = false
                MessageLiveData.postValue(
                    message(
                        message = "User Registed Succesfull",
                        posActionName = "ok",
                        OnPosAction = {
                            sessionProvider.user=user
                            events.postValue(RegisterEvent.NavigateToHome)
                            //Save user Id
                            // Navegat To Home
                        }
                    )
                )

            }else{
                MessageLiveData.postValue(
                    message(
                        message = Task.exception?.localizedMessage
                    )
                )

            }
        }

    }

    private fun ValidForm(): Boolean {
        var isValid = true
        if (username.value.isNullOrBlank()) {
            usernameError.postValue("userNameNotValid")
            isValid = false
        } else {
            usernameError.postValue(null)
        }
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
        if (passwordconfirm.value != password.value) {
            passwordconfirmError.postValue("PasswordConfNotValid")
            isValid = false
        } else {
            passwordconfirmError.postValue(null)
        }
        return isValid
    }
    fun navegatToLogin(){
        events.postValue(RegisterEvent.NavigateToLogin)
    }

}
