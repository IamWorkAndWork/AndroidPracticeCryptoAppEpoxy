package com.features.crypto.model

sealed class UiAction {
    data class Search(val query: String) : UiAction()
}