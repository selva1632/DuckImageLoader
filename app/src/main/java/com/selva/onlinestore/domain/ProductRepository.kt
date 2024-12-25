package com.selva.onlinestore.domain

import com.selva.onlinestore.data.remote.DuckItem
import com.selva.onlinestore.data.repository.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductFromServer(): Flow<Result<DuckItem>>
}