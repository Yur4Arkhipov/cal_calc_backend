package com.jacqulin.feature.usage.domain.repository

import com.jacqulin.feature.usage.domain.model.User

interface UserRepository {
    suspend fun getByDeviceId(deviceId: String): User?
    suspend fun create(deviceId: String): User
    suspend fun getOrCreate(deviceId: String): User
}