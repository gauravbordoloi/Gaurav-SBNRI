package `in`.gauravbordoloi.gauravsbnri.ui.view

import `in`.gauravbordoloi.gauravsbnri.AppController
import `in`.gauravbordoloi.gauravsbnri.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_settings.*

class SettingsDialog(val ctx: Context, val accountError: Boolean, val callback: () -> Unit) :
    Dialog(ctx) {

    private val pref = AppController.sharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_settings)
        window?.setBackgroundDrawableResource(R.color.light_transparent)
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        if (accountError) {
            inputUsername.error = "Doesn't exist"
        }
        themeSwitcher.setIsNight(pref.isNightMode())
        inputUsername.editText?.setText(pref.getUsername())
        ArrayAdapter.createFromResource(
            ctx,
            R.array.account_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            accountChooser.adapter = adapter
            if (pref.getType() == "orgs") {
                accountChooser.setSelection(1)
            }
        }

        buttonSave.setOnClickListener {
            val username = inputUsername.editText?.text.toString().trim()
            if (username.isEmpty()) {
                Toast.makeText(ctx, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val type = accountChooser.selectedItem as String
            pref.setType(type)
            pref.setUsername(username)
            val mode = pref.isNightMode()
            if (mode != themeSwitcher.isNight) {
                pref.setNightMode(themeSwitcher.isNight)
            }
            callback()
            dismiss()
        }

        buttonDefault.setOnClickListener {
            pref.clear()
            callback()
            dismiss()
        }

        btnClose.setOnClickListener {
            dismiss()
        }
    }

}