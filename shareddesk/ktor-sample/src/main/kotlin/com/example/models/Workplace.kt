package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Workplace(
    val id: String,
    val isBusy: Boolean,
)