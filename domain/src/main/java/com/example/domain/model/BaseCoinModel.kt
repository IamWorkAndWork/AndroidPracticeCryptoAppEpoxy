package com.example.domain.model

open class BaseCoinModel(
    open val uuid: String,
    open val iconUrl: String,
    open val name: String,
    open val symbol: String,
    open val coinColor: String,
    open val change: String,
    open val price: String,
)