package com.example.foodreciepeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.foodreciepeapp.model.data.FoodReciepeListEntry
import com.example.foodreciepeapp.ui.FoodReciepeViewModel
import com.google.accompanist.coil.CoilImage
import com.example.foodreciepeapp.FoodListing as FoodListing

@Composable
fun FoodReciepeList(
    navController: NavController,
    viewModel: FoodReciepeViewModel = hiltNavGraphViewModel()
) {
    Column {

        SearchBar(navController)
        Spacer(modifier = Modifier.height(8.dp))
        CustomChips()
        Spacer(modifier = Modifier.height(8.dp))
        FoodListing(navController = navController)

    }

}


@Composable
fun SearchBar(
    navController: NavController,
    viewModel: FoodReciepeViewModel = hiltNavGraphViewModel(),
    modifier: Modifier = Modifier,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    val focus = LocalFocusManager.current
    Box(Modifier.padding(start = 8.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(

                value = text,
                onValueChange = {
                    text = it
                    onSearch(it)
                },
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.White),
                label = {
                    Text(text = "Search", color = Color.Black)
                },

                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search32),
                        contentDescription = "Search"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Black,
                    disabledIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                    backgroundColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),

                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchFood(text)
                        focus.clearFocus()
                    }
                )

            )

            Icon(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "Menu",
                modifier = Modifier.padding(start = 8.dp)
            )
        }


    }


}

@Composable
fun CustomChips() {
    val list = listOf<String>("Chicken", "Beef", "Soup", "Desert", "Vegetarian", "Eggs")
    LazyRow(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(4.dp))
    {
        items(items = list, itemContent = { item ->

            when (item) {

                "Chicken" -> {
                    Chips(text = "Chicken")
                }

                "Beef" -> {
                    Chips(text = "Beef")
                }
                "Soup" -> {
                    Chips(text = "Soup")
                }
                "Desert" -> {
                    Chips(text = "Desert")
                }
                "Vegetarian" -> {
                    Chips(text = "Vegetarian")
                }
                "Eggs" -> {
                    Chips(text = "Eggs")
                }
            }

        })

    }

}


@Composable
fun Chips(text: String) {
    Spacer(modifier = Modifier.width(6.dp))
    Card(
        backgroundColor = colorResource(
            id = R.color.colorBlue
        ), modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable {

            }
    )
    {

        Text(text = text, modifier = Modifier.padding(6.dp), color = Color.White, fontSize = 14.sp)

    }

    Spacer(modifier = Modifier.width(6.dp))
}

@Composable
fun FoodList(
    rowIndex: Int,
    navController: NavController,
    entries: List<FoodReciepeListEntry>
) {
    Column()
    {
        FoodEntry(entry = entries[rowIndex], navController = navController)
    }
}

@Composable
fun FoodEntry(
    entry: FoodReciepeListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FoodReciepeViewModel = hiltNavGraphViewModel()
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    )
    {
        Column()
        {
            Spacer(modifier = Modifier
                .height(4.dp)
                .background(Color.DarkGray))

            CoilImage(
                data = ImageRequest.Builder(LocalContext.current)
                    .data(entry.reciepe_img).target { }.build(),
                contentDescription = entry.reciepe_name,
                modifier = modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
                    .background(Color.White),
                contentScale = ContentScale.FillWidth
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.scale(0.1f)
                )
            }
            Text(
                text = entry.reciepe_name,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )

        }
    }

}

@Composable
fun FoodListing(
    navController: NavController,
    viewModel: FoodReciepeViewModel = hiltNavGraphViewModel()
) {


    val foodList by remember {
        viewModel.foodList
    }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn()
    {
        val itemscount = foodList.size
        items(itemscount) {
            if (it >= itemscount - 1 && !endReached && !isLoading && !isSearching) {
                LaunchedEffect(key1 = true) {
                    viewModel.getAllFood()
                }
            }
            FoodList(rowIndex = it, navController = navController, entries = foodList)
        }

    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.getAllFood()
            }
        }
    }

}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}





