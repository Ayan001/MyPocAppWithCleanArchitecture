package com.example.mypocapp.presentation.ui.features.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypocapp.data.NetworkResult
import com.example.mypocapp.domain.usecase.users.GetUsersUseCase
import com.example.mypocapp.presentation.contracts.BaseContract
import com.example.mypocapp.presentation.contracts.UserContract
import com.example.mypocapp.util.ErrorsMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: GetUsersUseCase) : ViewModel() {

    init {
        getUsersData()
    }

    private val _state = MutableStateFlow(
        UserContract.State(
            users = listOf(),
            isLoading = true
        )
    )
    val state: StateFlow<UserContract.State> = _state

    var effects = Channel<BaseContract.Effect>(Channel.UNLIMITED)
        private set

    private fun updateState(newState: UserContract.State) {
        _state.value = newState
    }


    fun getUsersData() {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.execute().collect {
                when (it) {
                    is NetworkResult.Success -> {
                        val newState = state.value.copy(users = it.data!!, isLoading = false)
                        updateState(newState)
                        effects.send(BaseContract.Effect.DataWasLoaded)
                    }

                    is NetworkResult.Error -> {
                        val newState = state.value.copy(isLoading = false)
                        updateState(newState)
                        effects.send(
                            BaseContract.Effect.Error(
                                it.message ?: ErrorsMessage.gotApiCallError
                            )
                        )
                    }

                    is NetworkResult.Loading -> {
                        if (!state.value.isLoading!!) {
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }

                }
            }

        }
    }
}