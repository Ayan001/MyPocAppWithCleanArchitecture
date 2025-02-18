package com.example.mypocapp.presentation.contracts

class BaseContract {

    sealed class Effect {
        data object DataWasLoaded : Effect()
        data class Error(val errorMessage: String) : Effect()

    }
}