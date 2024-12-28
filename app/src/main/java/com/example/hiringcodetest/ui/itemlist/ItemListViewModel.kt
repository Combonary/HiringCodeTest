package com.example.hiringcodetest.ui.itemlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiringcodetest.domain.model.Item
import com.example.hiringcodetest.domain.model.ServerResult
import com.example.hiringcodetest.domain.usecase.GetItemListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val itemListUseCase: GetItemListUseCase
) : ViewModel() {

    private val _itemList = mutableStateOf(listOf<Item>())
    val itemList: MutableState<List<Item>>
        get() = _itemList
    private var _apiStatus: MutableState<ServerResult.Status?> =
        mutableStateOf(ServerResult.Status.LOADING)
    var apiStatus: State<ServerResult.Status?> = _apiStatus

    init {
        viewModelScope.launch {
            getItems()
        }
    }

    private suspend fun getItems() {
        itemListUseCase.invoke().collect { serverData ->
            _apiStatus.value = serverData.status
            _itemList.value = getSortedItems(serverData.data)
        }
    }

    private fun getSortedItems(items: List<Item>?): List<Item> {
        return items?.sortedWith(compareBy<Item> { it.listId }.thenBy { it.name })
            ?.filter { !it.name.isNullOrBlank() } ?: emptyList()
    }
}