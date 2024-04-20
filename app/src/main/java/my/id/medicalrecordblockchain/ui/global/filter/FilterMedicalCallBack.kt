package my.id.medicalrecordblockchain.ui.global.filter

interface FilterMedicalCallBack {
    fun onSelectedFilter(
        date: String?,
        status: String?,
        service: Int?
    )
}