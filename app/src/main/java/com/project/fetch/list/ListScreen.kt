package com.project.fetch.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import com.project.fetch.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable fun ItemListScreen(
    viewModel: ListViewModel = koinViewModel(),
) {
    val screenState = viewModel.screenState.collectAsState().value

    when (screenState) {
        is ScreenState.Loading -> LoadingScreen()
        is ScreenState.NoInternet -> NoInternetScreen(viewModel)
        is ScreenState.Success -> ListScreen(screenState.items)
        is ScreenState.Error -> ErrorScreen(viewModel,screenState.message)
    }
}

@Composable fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable fun NoInternetScreen(viewModel: ListViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.baseline_wifi_off_24), contentDescription = "No Internet")
            Text(text = "No Internet Connection", style = MaterialTheme.typography.bodyLarge)
            TextButton(onClick = { viewModel.retryRequest() }) { Text(text = "Try Again") }
        }
    }
}

@Composable fun ListScreen(items: Map<Int, List<ListItemInfo>>) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 48.dp, start = 20.dp, end = 20.dp)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items.forEach { (id, groupItems) ->
                item {
                    Text(modifier = Modifier.padding(vertical = 8.dp),
                        text = "Group $id",
                        style = TextStyle(fontSize = 18.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)

                    )
                }
                items(groupItems) { item ->
                    val displayText = "The current item's ID is ${item.id}, and its name is ${item.name}."
                    Text(modifier = Modifier.padding(vertical = 4.dp),
                        text = displayText,
                        style = TextStyle(fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Normal))
                }
            }
        }
    }
}

@Composable fun ErrorScreen(viewModel: ListViewModel, message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.baseline_wifi_off_24), contentDescription = "Error")
            Text(text = message, style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
            TextButton(onClick = { viewModel.retryRequest() }) { Text(text = "Try Again") }
        }
    }
}

