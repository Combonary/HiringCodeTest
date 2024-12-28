package com.example.hiringcodetest.ui.itemlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hiringcodetest.R
import com.example.hiringcodetest.domain.model.Item
import com.example.hiringcodetest.domain.model.ServerResult
import com.example.hiringcodetest.ui.theme.HiringCodeTestTheme

@Composable
fun ItemListScreen(
    viewModel: ItemListViewModel = viewModel(),
    onBackClicked: () -> Unit
) {

    val items by remember {
        viewModel.itemList
    }
    val apiStatus by remember {
        viewModel.apiStatus
    }

    ItemListScreenContent(
        onBackClicked = onBackClicked,
        apiStatus = apiStatus,
        itemList = items
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreenContent(
    onBackClicked: () -> Unit = {},
    apiStatus: ServerResult.Status? = null,
    itemList: List<Item> = listOf()
) {
    var loading by remember {
        mutableStateOf(false)
    }

    var showError by remember {
        mutableStateOf(false)
    }

    when (apiStatus) {
        ServerResult.Status.SUCCESS -> {
            loading = false
        }

        ServerResult.Status.ERROR -> {
            loading = false
            showError = true
        }

        ServerResult.Status.LOADING -> {
            loading = true
        }

        null -> {}
    }

    HiringCodeTestTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.items_top_app_bar_text)) },
                    navigationIcon = {
                        IconButton(onClick = onBackClicked) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Close App"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->

            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(64.dp)
                                .align(Alignment.Center)
                                .align(Alignment.Center),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                    if (showError) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Error Loading Items",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        LazyColumn(Modifier.fillMaxSize()) {
                            items(itemList) { item ->
                                item.name?.let { ListItem(it) }
                                HorizontalDivider(color = Color.Cyan)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(
    name: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = name,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemListScreenPreview() {
    val sampleList = listOf(
        Item(755, 2, "Item 344"),
        Item(203, 2, "Item 359"),
        Item(684, 1, "Item 684"),
        Item(276, 1, "Item 276")
    )
    ItemListScreenContent(
        {},
        null,
        sampleList
    )
}