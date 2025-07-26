package com.example.data.repository

import com.example.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        return Result.success(Unit)
    }
}
