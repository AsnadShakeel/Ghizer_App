package com.ese12.gilgitapp.repo

import com.ese12.gilgitapp.domain.MyResult
import com.ese12.gilgitapp.domain.models.UserModel

interface AuthRepository {
    suspend fun signInWithMailAndPassword(userModel: UserModel): MyResult
    suspend fun signInWithPhone(userModel: UserModel): MyResult
    suspend fun signUpWithMailAndPassword(userModel: UserModel): UserModel
    suspend fun signOut(): MyResult

}