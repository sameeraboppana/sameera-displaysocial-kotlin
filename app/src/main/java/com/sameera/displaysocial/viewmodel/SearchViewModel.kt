package com.sameera.displaysocial.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.sameera.displaysocial.model.User
import com.sameera.displaysocial.repository.UserRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val userDataLiveData = MutableLiveData<List<User>>()

    fun searchUsers(context: Context, query: String) {
        if(query.length >= 2) {
            viewModelScope.launch {
                var users = UserRepository.getUsers(context)
                users =users.filter { str -> str.fullName.startsWith(query) }
                userDataLiveData.postValue(users)
            }
        } else {
            userDataLiveData.postValue(emptyList())
        }
    }



}