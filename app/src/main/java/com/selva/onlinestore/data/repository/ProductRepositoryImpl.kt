package com.selva.onlinestore.data.repository

import com.selva.onlinestore.data.remote.ApiService
import com.selva.onlinestore.data.remote.DuckItem
import com.selva.onlinestore.domain.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun getProductFromServer(): Flow<Result<DuckItem>> {
        return flow {
            val productsFromApi = try {
                apiService.getProducts()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }
            emit(Result.Success(productsFromApi.body()))
        }
    }
}