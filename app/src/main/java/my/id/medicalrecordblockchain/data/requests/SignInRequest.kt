package my.id.medicalrecordblockchain.data.requests

data class SignInRequest(
    val email: String,
    val password: String,
    val type: String
)