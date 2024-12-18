package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class WorkplacesResponse(
    val list: List<Workplace>
)