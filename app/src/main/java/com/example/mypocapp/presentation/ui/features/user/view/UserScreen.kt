package com.example.mypocapp.presentation.ui.features.user.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mypocapp.R
import com.example.mypocapp.domain.mappers.UserDataModel
import com.example.mypocapp.presentation.contracts.BaseContract
import com.example.mypocapp.presentation.contracts.UserContract
import com.example.mypocapp.presentation.ui.component.EmptyView
import com.example.mypocapp.presentation.ui.features.user.navigation.NavigationScreens
import com.example.mypocapp.presentation.ui.theme.MyPocAppTheme
import com.example.mypocapp.util.TestTags
import com.example.mypocapp.util.TestTags.PROGRESS_BAR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@Composable
fun UserScreen(

    state: UserContract.State,
    effectFlow: Flow<BaseContract.Effect>?,
    onNavigationRequested: (itemUrl: String, imageId: String) -> Unit,
    onRefreshCall: () -> Unit

){

    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val userMessage = stringResource(R.string.users_are_loaded)

    //initializing the default selected item
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    /**
     * using the rememberNavController()
     * to get the instance of the navController
     */
    val navController = rememberNavController()

    // Listen for side effects from the VM
    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->
            if (effect is BaseContract.Effect.DataWasLoaded)
                snackBarHostState.showSnackbar(
                    message = userMessage,
                    duration = SnackbarDuration.Short
                )
        }?.collect { value ->
            if (value is BaseContract.Effect.Error) {
                // Handle other emitted values if needed
                Toast.makeText(context, value.errorMessage, Toast.LENGTH_LONG).show()
            }

        }
    }

   Scaffold (

       topBar = {

           TopAppBar(
               modifier = Modifier.semantics { testTag = TestTags.CAT_SCREEN_APP_BAR },
               title = {
                   Text(
                       text = stringResource(R.string.app_name),
                       color = colorResource(id = R.color.white),
                   )
               }, actions = {
                   // Show the "Refresh" button only when showRefreshButton is true
               },
               colors = centerAlignedTopAppBarColors(
                   containerColor = colorResource(R.color.purple_200),
                   titleContentColor = Color(R.color.white),
                   navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                   actionIconContentColor = MaterialTheme.colorScheme.onSecondary
               )
           )
       }

   ){  paddingValues ->
       //We need to setup our NavHost in here
       NavHost(
           navController = navController,
           startDestination = NavigationScreens.Home.screenRoute,
           modifier = Modifier.padding(paddingValues = paddingValues)
       ){

           composable(NavigationScreens.Home.screenRoute) {
               UserView(
                   state,
                   onNavigationRequested = onNavigationRequested
               )
           }
       }

   }

}

@Composable
fun UserView(
    state: UserContract.State,
    onNavigationRequested: (itemUrl: String, imageId: String) -> Unit
) {
    Surface(modifier = Modifier.semantics {
        testTag = TestTags.HOME_SCREEN_TAG
    }) {
        Box {
            val users = state.users
            if (users.isEmpty()) {
                EmptyView(message = stringResource(R.string.favorite_screen_empty_list_text))
            } else
                UserList(
                    users = users,
                    isLoading = state.isLoading,
                ) { itemUrl, imageId ->
                    onNavigationRequested(itemUrl, imageId)
                }
            if (state.isLoading)
                LoadingBar()
        }

    }
}
@Composable
fun LoadingBar() {
    Box(
        modifier = Modifier
            .semantics { testTag = TestTags.LOADING_BAR_TAG }
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center

    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .testTag(PROGRESS_BAR)
                .size(60.dp),
            color = colorResource(id = R.color.purple_700),
            strokeWidth = 5.dp, // Width of the progress indicator's stroke
            trackColor =  colorResource(id = R.color.white), // Color of the track behind the progress indicator
            strokeCap = StrokeCap.Round
        )
    }
}
@Composable
fun UserList(
    isLoading: Boolean = false,
    users: List<UserDataModel>,
    onItemClicked: (url: String, imageId: String) -> Unit = { _: String, _: String ->}
    ){

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp),

        content = {
            this.items (users)
            {item->
                Card(
                    modifier = Modifier.background(Color.Magenta)
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                ){
                    Row (modifier = Modifier
                        .background(Color.White)
                        .height(55.dp)
                        .fillMaxWidth()
                    ){
                        Box (modifier = Modifier
                            .background(Color.Cyan)
                            .fillMaxHeight()
                            .width(55.dp)
                            .weight(1f)

                        ){
                        }
                        Column(modifier = Modifier
                            .background(Color.White)
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(4f)) {

                            Text(text = "${item.first_name} ${item.last_name}",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(1.dp),
                                color = colorResource(id = R.color.black),)

                            Text(text = "${item.email}",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(1.dp),
                                color = colorResource(id = R.color.black),)
                        }
                    }
                }


            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    MyPocAppTheme {
        UserScreen (
            UserContract.State(),
            null,
            onNavigationRequested = { _: String, _: String -> },
            onRefreshCall = {})
    }
}