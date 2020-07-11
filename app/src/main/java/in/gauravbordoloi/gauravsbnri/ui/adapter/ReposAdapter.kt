package `in`.gauravbordoloi.gauravsbnri.ui.adapter

import `in`.gauravbordoloi.gauravsbnri.R
import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import `in`.gauravbordoloi.gauravsbnri.ui.view.viewholder.RepoViewHolder
import `in`.gauravbordoloi.gauravsbnri.ui.view.viewholder.StateViewHolder
import `in`.gauravbordoloi.gauravsbnri.utils.NetworkState
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView


class ReposAdapter : PagedListAdapter<Repo, RecyclerView.ViewHolder>(Repo.DIFF_CALLBACK) {

    private var networkState: NetworkState? = null

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_state
        } else {
            R.layout.item_repo
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = networkState
        val previousExtraRow = hasExtraRow()
        networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_repo -> {
                val view = inflater.inflate(R.layout.item_repo, parent, false)
                RepoViewHolder(view)
            }
            R.layout.item_state -> {
                val view = inflater.inflate(R.layout.item_state, parent, false)
                StateViewHolder(view)
            }
            else -> {
                throw IllegalArgumentException("View type not defined")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_repo -> (holder as RepoViewHolder).bind(getItem(position))
            R.layout.item_state -> (holder as StateViewHolder).bind(networkState)
        }
    }

}