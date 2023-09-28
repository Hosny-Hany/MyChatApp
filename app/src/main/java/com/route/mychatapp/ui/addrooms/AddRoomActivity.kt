package com.route.mychatapp.ui.addrooms

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.route.mychatapp.R
import com.route.mychatapp.databinding.AddroomActivityBinding
import com.route.mychatapp.generated.callback.OnClickListener.Listener
import com.route.mychatapp.ui.hoom.HomeActivity
import com.route.mychatapp.ui.login.LoginEvents
import com.route.mychatapp.ui.message
import com.route.mychatapp.ui.register.registerActivity
import com.route.mychatapp.ui.showLoadingProgressDialog
import com.route.mychatapp.ui.showMessage

class AddRoomActivity : AppCompatActivity() {
  lateinit var ViewBinding : AddroomActivityBinding
    private val ViewModel : AddRoomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     ViewBinding = DataBindingUtil.setContentView(this , R.layout.addroom_activity)
        initviews()
        subscribeToLiveData()
    }
      private var LoadingDialog : AlertDialog?=null
    private fun subscribeToLiveData() {
        ViewModel.MessageLiveData.observe(this) { Message ->
            showMessage(
                message = Message.message ?: "some thing want Error",
                posActionName = "OK",
                posAction = Message.OnPosAction,
                NegActionName = Message.OnPosAction,
                negAction = Message.OnNegAction,
                IsCancelable = Message.IsCancelable
            )
        }
        ViewModel.LoadingLiveData.observe(this){
            if (it==null){
                //hide
                LoadingDialog?.dismiss()
                LoadingDialog=null
                return@observe
            }
            LoadingDialog = showLoadingProgressDialog(
                message = it.message?:"",
                IsCancelable = it.IsCancelable)
                LoadingDialog?.show()

        }

        ViewModel.events.observe(this, ::handleEvents)
    }
    fun handleEvents(addRoomEvents: AddRoomEvents?) {
      when(addRoomEvents){
          AddRoomEvents.NavigateToHomeAndFinish->{
              finish()
          }
          else->{

          }
      }

    }



    lateinit var categoriesAdapter: RoomCategoriesAdapter
    private fun initviews() {
        categoriesAdapter = RoomCategoriesAdapter(ViewModel.categories)
        ViewBinding.vm=ViewModel
        ViewBinding.lifecycleOwner = this
        ViewBinding.contentHome.CategoriesSpinner.adapter= categoriesAdapter
        ViewBinding.contentHome.CategoriesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, itemview: View?, position: Int, id: Long) {
                    ViewModel.onCategoriesClicked(position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
        }
      }
    }

