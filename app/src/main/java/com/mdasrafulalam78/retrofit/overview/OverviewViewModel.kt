package com.mdasrafulalam78.retrofit.overview

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdasrafulalam78.retrofit.model.MarsPhoto
import com.mdasrafulalam78.retrofit.network.MarsApi
import kotlinx.coroutines.launch


enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    var isOnline = MutableLiveData<Boolean>()
    private val _status = MutableLiveData<MarsApiStatus>()
    var status: LiveData<MarsApiStatus> = _status
    private val _photos = MutableLiveData<List<MarsPhoto>>()
    val photos: LiveData<List<MarsPhoto>> = _photos
    init {
        if (isOnline.value==true){
            getMarsPhotos()
        }else{
            _status.value = MarsApiStatus.ERROR
        }
    }
    fun getImages(){
        if (isOnline.value == true){
            getMarsPhotos()
        }else{
            _status.value = MarsApiStatus.ERROR
        }
    }

    private fun getMarsPhotos() {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                _photos.value = MarsApi.retrofitService.getPhotos()
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}
