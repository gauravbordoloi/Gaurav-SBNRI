package `in`.gauravbordoloi.gauravsbnri.ui.view.viewholder

import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import `in`.gauravbordoloi.gauravsbnri.utils.Helper
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val repoTitle = view.repoTitle
    private val repoDate = view.repoDate
    private val repoDescription = view.repoDescription
    private val repoIssues = view.repoIssues
    private val repoLicense = view.repoLicense
    private val buttonOpen = view.buttonOpen
    private val permissionsContainer = view.permissionsContainer
    private val permissionAdmin = view.permissionAdmin
    private val permissionPush = view.permissionPush
    private val permissionPull = view.permissionPull

    fun bind(item: Repo?) {

        if (item == null) {
            return
        }

        repoTitle.text = item.name
        repoDate.text = Helper.formatDate(item.createdAt)
        if (item.description.isNullOrEmpty()) {
            repoDescription.visibility = View.GONE
        } else {
            repoDescription.visibility = View.VISIBLE
            repoDescription.text = item.description
        }
        repoIssues.text = "${item.openIssuesCount} open issues"
        if (item.permissions == null) {
            permissionsContainer.visibility = View.GONE
        } else {
            permissionsContainer.visibility = View.VISIBLE
            permissionAdmin.isChecked = item.permissions.admin
            permissionPush.isChecked = item.permissions.push
            permissionPull.isChecked = item.permissions.pull
        }

        if (item.license == null) {
            repoLicense.visibility = View.GONE
        } else {
            repoLicense.visibility = View.VISIBLE
            repoLicense.text = item.license.name
            repoLicense.setOnClickListener {
                if (!Helper.openLink(it.context, item.license.url)) {
                    Toast.makeText(
                        it.context,
                        "No activity found to handle link",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        buttonOpen.setOnClickListener {
            if (!Helper.openLink(it.context, item.url)) {
                Toast.makeText(
                    it.context,
                    "No activity found to handle link",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}