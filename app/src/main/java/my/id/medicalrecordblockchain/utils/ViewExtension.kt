package my.id.medicalrecordblockchain.utils

import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import my.id.medicalrecordblockchain.R

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    )

fun Activity.showSnackBar(
    message: String,
    snackBarType: SnackBarType
) {
    val snackbar = Snackbar.make(this.window.decorView.rootView, "", Snackbar.LENGTH_LONG)

    val customView =
        this.layoutInflater.inflate(R.layout.base_snack_bar, null)

    when (snackBarType) {
        SnackBarType.SUCCESS -> {
            val rootLayout = customView.findViewById<ConstraintLayout>(R.id.constraint_layout_root)
            rootLayout.setBackgroundColor(getColorResources(R.color.success_300))

            val imageViewIcon = customView.findViewById<ImageView>(R.id.image_view_icon)
            imageViewIcon.setImageDrawable(getDrawableResources(R.drawable.ic_snack_bar_success))
        }

        SnackBarType.ERROR -> {
            val rootLayout = customView.findViewById<ConstraintLayout>(R.id.constraint_layout_root)
            rootLayout.setBackgroundColor(getColorResources(R.color.destructive_300))

            val imageViewIcon = customView.findViewById<ImageView>(R.id.image_view_icon)
            imageViewIcon.setImageDrawable(getDrawableResources(R.drawable.ic_snack_bar_error))
        }

        SnackBarType.INFO -> {
            val rootLayout = customView.findViewById<ConstraintLayout>(R.id.constraint_layout_root)
            rootLayout.setBackgroundColor(getColorResources(R.color.neutral_900))

            val imageViewIcon = customView.findViewById<ImageView>(R.id.image_view_icon)
            imageViewIcon.setImageDrawable(getDrawableResources(R.drawable.ic_snack_bar_info))
        }
    }

    val textView = customView.findViewById<TextView>(R.id.text_view_message)
    textView.text = message

    snackbar.view.setBackgroundColor(Color.TRANSPARENT)

    val snackBarLayout = snackbar.view as (Snackbar.SnackbarLayout)
    snackBarLayout.setPadding(0, 0, 0, 0)

    snackBarLayout.addView(customView, 0)
    snackbar.view.translationY = (-64).toPx
    snackbar.show()
}

fun Fragment.showSnackBar(
    message: String,
    snackBarType: SnackBarType
) {
    val snackbar = Snackbar.make(this.requireView().rootView, "", Snackbar.LENGTH_LONG)

    val customView =
        this.layoutInflater.inflate(R.layout.base_snack_bar, null)

    when (snackBarType) {
        SnackBarType.SUCCESS -> {
            val rootLayout = customView.findViewById<ConstraintLayout>(R.id.constraint_layout_root)
            rootLayout.setBackgroundColor(this.requireContext().getColorResources(R.color.success_300))

            val imageViewIcon = customView.findViewById<ImageView>(R.id.image_view_icon)
            imageViewIcon.setImageDrawable(this.requireContext().getDrawableResources(R.drawable.ic_snack_bar_success))
        }

        SnackBarType.ERROR -> {
            val rootLayout = customView.findViewById<ConstraintLayout>(R.id.constraint_layout_root)
            rootLayout.setBackgroundColor(this.requireContext().getColorResources(R.color.destructive_300))

            val imageViewIcon = customView.findViewById<ImageView>(R.id.image_view_icon)
            imageViewIcon.setImageDrawable(this.requireContext().getDrawableResources(R.drawable.ic_snack_bar_error))
        }

        SnackBarType.INFO -> {
            val rootLayout = customView.findViewById<ConstraintLayout>(R.id.constraint_layout_root)
            rootLayout.setBackgroundColor(this.requireContext().getColorResources(R.color.neutral_900))

            val imageViewIcon = customView.findViewById<ImageView>(R.id.image_view_icon)
            imageViewIcon.setImageDrawable(this.requireContext().getDrawableResources(R.drawable.ic_snack_bar_info))
        }
    }

    val textView = customView.findViewById<TextView>(R.id.text_view_message)
    textView.text = message

    snackbar.view.setBackgroundColor(Color.TRANSPARENT)

    val snackBarLayout = snackbar.view as (Snackbar.SnackbarLayout)
    snackBarLayout.setPadding(0, 0, 0, 0)

    snackBarLayout.addView(customView, 0)
    snackbar.view.translationY = (-64).toPx
    snackbar.show()
}

enum class SnackBarType {
    SUCCESS, ERROR, INFO
}