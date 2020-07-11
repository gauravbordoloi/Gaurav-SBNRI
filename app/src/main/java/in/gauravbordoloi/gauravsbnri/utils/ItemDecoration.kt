package `in`.gauravbordoloi.gauravsbnri.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(
    context: Context,
    margin: Int
) : RecyclerView.ItemDecoration() {

    private val margin = Helper.dpToPx(context, margin)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            when {
                parent.getChildAdapterPosition(view) == 0 && parent.childCount == 1 -> {
                    top = margin
                    bottom = margin
                }
                parent.getChildAdapterPosition(view) == 0 -> {
                    top = margin
                    bottom = margin / 2
                }
                parent.getChildAdapterPosition(view) == (parent.childCount - 1) -> {
                    top = margin / 2
                    bottom = margin
                }
                else -> {
                    top = margin / 2
                    bottom = margin / 2
                }
            }
            left = margin
            right = margin
        }
    }

}