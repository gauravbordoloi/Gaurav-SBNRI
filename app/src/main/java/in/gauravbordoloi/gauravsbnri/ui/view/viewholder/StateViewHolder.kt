package `in`.gauravbordoloi.gauravsbnri.ui.view.viewholder

import `in`.gauravbordoloi.gauravsbnri.utils.NetworkState
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_state.view.*

class StateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val errorMessage = view.errorMessage
    private val progressBar = view.progressBar

    fun bind(item: NetworkState?) {
        if (item == null) {
            errorMessage.visibility = View.GONE
            progressBar.visibility = View.GONE
        } else {
            when (item.status) {
                NetworkState.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
                NetworkState.Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = item.message
                }
                else -> {
                    errorMessage.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

}