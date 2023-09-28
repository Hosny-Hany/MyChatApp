package com.route.mychatapp.ui.hoom

//{
//{ How To Prevent MemoryLeaks In DataBinding }

//private var _binding: ResultProfileBinding? = null
// This property is only valid between onCreateView and
// onDestroyView.
//private val binding get() = _binding!!

//override fun onCreateView(
    //  inflater: LayoutInflater,
    //container: ViewGroup?,
    //savedInstanceState: Bundle?
//): View? {
    //  _binding = ResultProfileBinding.inflate(inflater, container, false)
    //val view = binding.root
    //return view
//}

//override fun onDestroyView() {
    //  super.onDestroyView()
    //_binding = null
//}
import androidx.lifecycle.ViewModel
import com.route.mychatapp.Common.SingleLiveEvent
import com.route.mychatapp.ui.hoom.HomeEvents

class HomeViewModel : ViewModel(){
val events = SingleLiveEvent<HomeEvents>()


    fun NavigateToRoom(){
        events.postValue(HomeEvents.NavigateToRoom)
    }
}