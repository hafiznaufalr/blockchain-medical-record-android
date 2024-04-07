package my.id.medicalrecordblockchain.data.requests

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val address: String,
    val nik: String
)