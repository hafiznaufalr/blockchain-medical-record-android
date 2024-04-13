package my.id.medicalrecordblockchain.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.DialogLayoutBinding

open class LoadingDialog(context: Context) : Dialog(context), LifecycleEventObserver {

    private var binding: DialogLayoutBinding =
        DialogLayoutBinding.inflate(LayoutInflater.from(context))
    private val mainHandler = Handler(Looper.getMainLooper())

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
        mainHandler.post {
            context.activity()?.getLifecycleOwner()?.lifecycle?.addObserver(this)
        }
    }

    private var isOnRefreshing = false
    fun setRefreshing(show: Boolean) {
        mainHandler.removeCallbacks(this::dismiss)
        if (show)
            show()
        else
            mainHandler.postDelayed(this::dismiss, 500)
    }

    override fun dismiss() {
        if (isOnRefreshing.not()) return

        isOnRefreshing = false
        super.dismiss()
        mainHandler.post {
            context.activity()?.getLifecycleOwner()?.lifecycle?.removeObserver(this)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event.targetState == Lifecycle.State.DESTROYED) {
            super.dismiss()
        }

        // restart the dialog because frozen CircularProgressIndicator
        // https://github.com/material-components/material-components-android/issues/2355
        when {
            event == Lifecycle.Event.ON_START && isOnRefreshing -> {
                Log.d("Spinner Dialog", "STARTED")
                super.show()
            }
            event == Lifecycle.Event.ON_PAUSE && isOnRefreshing -> {
                Log.d("Spinner Dialog", "HIDDEN")
                super.dismiss()
            }
            event == Lifecycle.Event.ON_STOP -> {
                Log.d("Spinner Dialog", "STOPPED")
                super.dismiss()
            }
        }
    }

}