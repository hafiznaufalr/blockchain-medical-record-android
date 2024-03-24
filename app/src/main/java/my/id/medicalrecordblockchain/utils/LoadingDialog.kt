package my.id.medicalrecordblockchain.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Window
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.DialogLayoutBinding

open class LoadingDialog(context: Context) : Dialog(context) {

    private var binding: DialogLayoutBinding =
        DialogLayoutBinding.inflate(LayoutInflater.from(context))
    private val dismissHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun show() {
        if (isOnRefreshing) return

        isOnRefreshing = true
        super.show()
    }

    var isOnRefreshing = false
    fun setRefreshing(show: Boolean) {
        dismissHandler.removeCallbacks(this::dismiss)
        if (show)
            show()
        else
            dismissHandler.postDelayed(this::dismiss, 500)
    }

    override fun dismiss() {
        if (isOnRefreshing.not()) return

        isOnRefreshing = false
        super.dismiss()
    }

}