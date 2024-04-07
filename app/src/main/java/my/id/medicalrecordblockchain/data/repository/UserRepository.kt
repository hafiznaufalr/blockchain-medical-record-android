package my.id.medicalrecordblockchain.data.repository

import my.id.medicalrecordblockchain.data.requests.SignInRequest
import my.id.medicalrecordblockchain.data.requests.SignUpRequest
import my.id.medicalrecordblockchain.data.response.SignInResponse
import my.id.medicalrecordblockchain.utils.ResultData

interface UserRepository {
    suspend fun postSignIn(signInRequest: SignInRequest): ResultData<SignInResponse>
    suspend fun postSignUp(signUpRequest: SignUpRequest): ResultData<SignInResponse>
}