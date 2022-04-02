package com.features.crypto.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.ResultState
import com.example.domain.model.CoinDetailsModel
import com.example.domain.usecase.GetCoinDetailsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CryptoDetailsViewModel(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase
) : ViewModel() {

    private val _coinDetailsModel = MutableLiveData<ResultState<CoinDetailsModel>>()
    val coinDetailsModel: LiveData<ResultState<CoinDetailsModel>> get() = _coinDetailsModel

    fun fetchCoinDetails(uuid: String) {

        viewModelScope.launch {

            _coinDetailsModel.value = ResultState.Loading

            getCoinDetailsUseCase.execute(uuid = uuid)
                .catch {
                    _coinDetailsModel.value = ResultState.Error(it)
                }
                .collect {
                    _coinDetailsModel.value = it
                }

        }

    }

}