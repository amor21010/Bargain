package com.omaraboesmail.bargain.data

import com.omaraboesmail.bargain.pojo.AuthState
import com.omaraboesmail.bargain.pojo.User

interface UserInterface {
    suspend fun insertUSer(user: User): AuthState
    suspend fun authenticateUser(userEmail: String, password: String)
    suspend fun updateUser(id: String, newUser: User)
    suspend fun deleteUser(id: String)
}