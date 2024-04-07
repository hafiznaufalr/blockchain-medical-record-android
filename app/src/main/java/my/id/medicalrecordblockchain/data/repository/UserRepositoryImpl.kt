package my.id.medicalrecordblockchain.data.repository

import my.id.medicalrecordblockchain.data.APIService
import my.id.medicalrecordblockchain.data.requests.SignInRequest
import my.id.medicalrecordblockchain.data.response.SignInResponse
import my.id.medicalrecordblockchain.utils.NetworkHandler
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: APIService
): UserRepository {
    override suspend fun postSignIn(signInRequest: SignInRequest): ResultData<SignInResponse> {
        return NetworkHandler.safeApiCall {
            apiService.postSignIn(signInRequest)
        }
    }

}