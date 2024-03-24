package my.id.medicalrecordblockchain.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class ItemHorizontalMarginDecoration(
    private val leftMarginInPx: Int,
    private val firstLeftMarginInPx: Int,
    private val rightMarginInPx: Int,
    private val lastRightMarginInPx: Int
) : RecyclerView.ItemDecoration() {
    constructor(
        context: Context,
        @DimenRes horizontalMargin: Int
    ) : this(context.resources.getDimensionPixelSize(horizontalMargin))

    constructor(
        context: Context,
        @DimenRes horizontalMargin: Int,
        @DimenRes firstLeftItemMargin: Int,
        @DimenRes lastRightItemMargin: Int
    ) : this(
        context.resources.getDimensionPixelSize(horizontalMargin),
        context.resources.getDimensionPixelSize(firstLeftItemMargin),
        context.resources.getDimensionPixelSize(lastRightItemMargin)
    )

    constructor(
        horizontalMarginInPx: Int,
        firstLeftMarginInPx: Int = horizontalMarginInPx,
        lastRightMarginInPx: Int = horizontalMarginInPx
    ) : this(horizontalMarginInPx, firstLeftMarginInPx, horizontalMarginInPx, lastRightMarginInPx)

    constructor(
        context: Context,
        @DimenRes leftMargin: Int,
        @DimenRes rightMargin: Int
    ) : this(
        context.resources.getDimensionPixelSize(leftMargin),
        context.resources.getDimensionPixelSize(leftMargin),
        context.resources.getDimensionPixelSize(rightMargin),
        context.resources.getDimensionPixelSize(rightMargin)
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        val isFirstItem = position == 0
        val isLastItem = position == itemCount - 1

        outRect.left = if (isFirstItem) {
            firstLeftMarginInPx
        } else {
            leftMarginInPx
        }
        outRect.right = if (isLastItem) {
            lastRightMarginInPx
        } else {
            rightMarginInPx
        }
    }

}