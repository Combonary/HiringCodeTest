package com.example.hiringcodetest.domain.repository

import com.example.hiringcodetest.domain.model.Item
import com.example.hiringcodetest.domain.model.ServerResult
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    suspend fun getItems(): Flow<ServerResult<List<Item>?>>
}