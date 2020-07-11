package `in`.gauravbordoloi.gauravsbnri.ui.view

import `in`.gauravbordoloi.gauravsbnri.R
import `in`.gauravbordoloi.gauravsbnri.data.model.Account
import `in`.gauravbordoloi.gauravsbnri.ui.adapter.ReposAdapter
import `in`.gauravbordoloi.gauravsbnri.ui.viewmodel.AccountViewModel
import `in`.gauravbordoloi.gauravsbnri.ui.viewmodel.ReposViewModel
import `in`.gauravbordoloi.gauravsbnri.utils.Helper
import `in`.gauravbordoloi.gauravsbnri.utils.ItemDecoration
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var root: View? = null
    private var account: Account? = null

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var reposViewModel: ReposViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_home, container, false)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ReposAdapter()
        recyclerView.addItemDecoration(ItemDecoration(requireContext(), 16))
        recyclerView.adapter = adapter

        reposViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(ReposViewModel::class.java)

        accountViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(AccountViewModel::class.java)

        accountViewModel.getAccount().observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                account = it
                updateUI(view, it)
            }
        })

        reposViewModel.getRepos().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        reposViewModel.getNetworkState().observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })

        buttonOpen.setOnClickListener {
            if (account == null) {
                Toast.makeText(context, "Account is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Helper.openLink(it.context, account!!.url)) {
                Toast.makeText(
                    it.context,
                    "No activity found to handle link",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        buttonSettings.setOnClickListener {
            showSettings(false)
        }

    }

    private fun showSettings(accountError: Boolean) {
        SettingsDialog(requireContext(), accountError) {
            Toast.makeText(context, "Restarting to make changes", Toast.LENGTH_SHORT).show()
            accountViewModel.clear()
            reposViewModel.clear()
            lifecycleScope.launch {
                delay(1000)
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }.show()
    }

    private fun updateUI(view: View, account: Account?) {
        if (account == null) {
            Snackbar.make(view, "The provided username or type is invalid.", Snackbar.LENGTH_INDEFINITE).show()
            showSettings(true)
            return
        }

        Glide.with(accountImage).load(account.image)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
            .into(accountImage)
        accountName.text = account.name
        if (account.type == "User") {
            accountDescription.text = account.bio
            accountType.text = "Individual"
            accountType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user, 0, 0, 0)
        } else {
            accountDescription.text = account.description
            accountType.text = account.type
            accountType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_organization, 0, 0, 0)
        }
        accountRepos.text = "${account.reposCount} repos"
        accountGists.text = "${account.gistsCount} gists"
    }

}