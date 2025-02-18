package com.example.mypocapp.presentation.ui.features.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityOptionsCompat
import com.example.mypocapp.presentation.ui.features.user.view.UserScreen
import com.example.mypocapp.presentation.ui.features.user.viewmodel.UserViewModel
import com.example.mypocapp.presentation.ui.theme.MyPocAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow

@AndroidEntryPoint
class UserActivity : ComponentActivity(){
    private val viewModel: UserViewModel by viewModels()
    private val myActivityResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPocAppTheme {
                UsersDestination()
            }
        }
    }

    @Composable
    fun UsersDestination() {
        UserScreen (
            state = viewModel.state.collectAsState().value,
            effectFlow = viewModel.effects.receiveAsFlow(),
            onNavigationRequested = { itemUrl, imageId ->

            },
            onRefreshCall = {
                viewModel.getUsersData()
            })
    }
}