package com.rr.razasypelajes

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View

class ConfigActivity : AppCompatActivity(), ExitDialog.ExitDialogListener {
    override fun onExitDialogPositiveClick(dialog: DialogFragment) {
        finish()
    }

    fun goBack(view: View?){
        // Create an instance of the dialog fragment and show it
        val dialog = ExitDialog() as DialogFragment
        dialog.show(supportFragmentManager, "NoticeDialogFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
    }


}