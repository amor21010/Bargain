package com.omaraboesmail.bargain.data

import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.resultStats.AuthState

interface UserInterface {
    suspend fun insertUSer(user: User): AuthState
    suspend fun authenticateUser(userEmail: String, password: String)
    suspend fun updateUser(id: String, newUser: User)
    suspend fun deleteUser(id: String)
}