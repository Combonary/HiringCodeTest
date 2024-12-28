package com.example.hiringcodetest.domain.usecase

import com.example.hiringcodetest.domain.model.Item
import com.example.hiringcodetest.domain.model.ServerResult
import com.example.hiringcodetest.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemListUseCase @Inject constructor(
    private val repository: ItemsRepository
) {
    suspend operator fun invoke(): Flow<ServerResult<List<Item>?>> {
        return repository.getItems()
    }
}