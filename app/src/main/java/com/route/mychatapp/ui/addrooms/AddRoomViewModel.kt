package com.route.mychatapp.ui.addrooms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.route.mychatapp.Common.SingleLiveEvent
import com.route.mychatapp.firebase.RoomsDao
import com.route.mychatapp.model.Category
import com.route.mychatapp.model.Room
import com.route.mychatapp.ui.addrooms.AddRoomEvents
import com.route.mychatapp.ui.message
import com.route.mychatapp.ui.register.RegisterEvent
import com.route.mychatapp.ui.sessionProvider

class AddRoomViewModel : ViewModel() {
    val LoadingLiveData = MutableLiveData<message?>()
    val events = SingleLiveEvent<AddRoomEvents>()
     val MessageLiveData = SingleLiveEvent<message>()
    val RoomTitle = MutableLiveData<String>()
    val RoomTitleError = MutableLiveData<String?>()
    val categories = Category.categories()
    var SelectedCategories = categories[0]

    val RoomDescription = MutableLiveData<String>()
    val RoomDescriptionError = MutableLiveData<String?>()

  fun creatroom(){
    if (!vaild())return
      LoadingLiveData.postValue(message(
          message = "Loading....",
          IsCancelable = false
      ))
    RoomsDao.createRoom(
        title = RoomTitle.value?:"",
        desc = RoomDescription.value?:"",
        ownerId = sessionProvider.user?.id?:"",
        categoryId = SelectedCategories.id
    ){
        Task->
        LoadingLiveData.postValue(null)
        if (Task.isSuccessful){
            MessageLiveData.postValue(
                message(
                    message = "Room Add Succesfull",
                    posActionName = "ok",
                    OnPosAction = {
                        events.postValue(AddRoomEvents.NavigateToHomeAndFinish)
                        // Navigate To Home
                    }
                )
            )
            return@createRoom
        }else{
            MessageLiveData.postValue(
                message(
                    message = Task.exception?.localizedMessage
                )
            )
        }
    }
}

    private fun vaild(): Boolean {
        var isValid = true

        if (RoomTitle.value.isNullOrBlank()) {
            RoomTitleError.postValue("EmailNotValid")
            isValid = false
        } else {
            RoomTitleError.postValue(null)
        }
        if (RoomDescription.value.isNullOrBlank()) {
            RoomDescriptionError.postValue("PasswordNotValid")
            isValid = false
        } else {
            RoomDescriptionError.postValue(null)
        }

        return isValid

    }

    fun onCategoriesClicked(position: Int) {
        SelectedCategories = categories[position]
    }

}