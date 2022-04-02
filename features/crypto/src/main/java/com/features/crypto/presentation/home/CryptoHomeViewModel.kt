package com.features.crypto.presentation.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.request.CoinsRequestModel
import com.example.domain.usecase.GetCoinDataUseCase
import com.features.crypto.model.UiAction
import com.features.crypto.model.UiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


@FlowPreview
@ExperimentalCoroutinesApi
class CryptoHomeViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getCoinDataUseCase: GetCoinDataUseCase,
) : ViewModel() {

    val state: StateFlow<UiState>
    val coinsPagingDataFlow: Flow<PagingData<CoinHomeUiModel>>
    val accept: (UiAction) -> Unit

    private var query: String = ""

    companion object {
        const val DELAY_10_SEC = 10000L
        private const val SEARCH_DELAY = 500L
    }

    init {
        val initialQuery: String = savedStateHandle.get(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY

        val actionStateFlow = MutableSharedFlow<UiAction>()

        val searches = actionStateFlow
            .distinctUntilChanged()
            .debounce(SEARCH_DELAY)
            .filterIsInstance<UiAction.Search>()
            .onStart {
                emit(UiAction.Search(query = initialQuery))
            }

        coinsPagingDataFlow = searches.flatMapLatest {
            query = it.query
            fetchCoinPagingData(query = query)
        }.cachedIn(viewModelScope)

        state = searches.map { search ->
            UiState(
                query = search.query
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = UiState()
        )

        accept = { action ->
            viewModelScope.launch {
                actionStateFlow.emit(action)
            }
        }

        viewModelScope.launch {

            launchPeriodicAsync(DELAY_10_SEC) {

                //need to implement for refresh every 10 seconds, the ideas will using paing3 library with ROOM Database

            }

        }

    }

    fun CoroutineScope.launchPeriodicAsync(
        repeatMillis: Long,
        action: () -> Unit
    ) = this.async {
        if (repeatMillis > 0) {
            while (isActive) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    private suspend fun fetchCoinPagingData(query: String): Flow<PagingData<CoinHomeUiModel>> {
        val request = CoinsRequestModel(
            searchQuery = query
        )
        return getCoinDataUseCase.execute(request = request)
    }

    suspend fun getBitmap(context: Context, drawableRes: Int): Bitmap? {
        return withContext(Dispatchers.Default) {

            val drawable = ContextCompat.getDrawable(
                context,
                drawableRes
            )
            if (drawable != null) {
                val canvas = Canvas()
                val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                canvas.setBitmap(bitmap)
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                drawable.draw(canvas)
                return@withContext bitmap
            }

            return@withContext null

        }
    }

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        super.onCleared()
    }

}

const val LAST_SEARCH_QUERY: String = "last_search_query"
const val DEFAULT_QUERY = ""