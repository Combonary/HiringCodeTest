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

    val uiState: MutableState<UIState> = mutableStateOf(UIState.Loading)

    init {
        viewModelScope.launch {
            getItems()
        }
    }

    private suspend fun getItems() {
        itemListUseCase.invoke().collect { serverData ->
            if (serverData.status == ServerResult.Status.ERROR) {
                uiState.value = UIState.Error
            } else if (serverData.status == ServerResult.Status.SUCCESS) {
                uiState.value = UIState.Success(getSortedItems(serverData.data))
            }
        }
    }

    private fun getSortedItems(items: List<Item>?): List<Item> {
        return items?.sortedWith(compareBy<Item> { it.listId }.thenBy { it.name })
            ?.filter { !it.name.isNullOrBlank() } ?: emptyList()
    }

}

sealed interface UIState {
    data object Loading: UIState
    data object Error: UIState
    data class Success(val items: List<Item>): UIState
}