package com.example.hiringcodetest.data.repository

import com.example.hiringcodetest.data.source.remote.ItemApi
import com.example.hiringcodetest.domain.model.Item
import com.example.hiringcodetest.domain.model.ServerResult
import com.example.hiringcodetest.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val api: ItemApi
): ItemsRepository {
    override suspend fun getItems(): Flow<ServerResult<List<Item>?>> {
        return flow {
            emit(ServerResult.loading())
            val result = api.getItemsList()
            emit(result)
        }
    }
}