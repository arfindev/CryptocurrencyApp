package com.example.cryptocurrencyapp.domain.use_case.get_coin

import com.example.cryptocurrencyapp.common.Resource
import com.example.cryptocurrencyapp.data.remote.dto.toCoin
import com.example.cryptocurrencyapp.data.remote.dto.toCoinDetails
import com.example.cryptocurrencyapp.domain.model.Coin
import com.example.cryptocurrencyapp.domain.model.CoinDetails
import com.example.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinUseCases @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetails>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinsById(coinId).toCoinDetails()
            emit(Resource.Success(coin))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))

        } catch (e: IOException) {
            emit(Resource.Error("Could not reach server. Check your internet connection"))

        }
    }
}