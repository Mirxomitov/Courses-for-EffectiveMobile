package com.example.data.repository

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(500)
        return Result.success(Unit)
    }
}
