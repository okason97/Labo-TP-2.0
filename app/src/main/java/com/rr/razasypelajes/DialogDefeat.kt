package com.rr.razasypelajes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment

class DialogDefeat : DialogFragment() {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface DefeatDialogListener {
        fun onDefeatDialogPositiveClick(dialog : DialogFragment)
        fun onDefeatDialogNegativeClick(dialog : DialogFragment)
    }

    private lateinit var mListener: DefeatDialogListener

    override fun onAttach(context : Context) {
        super.onAttach(context)
        // Verify that the host context implements the callback interface
        try {
            // Instantiate the DefeatDialogListener so we can send events to the host
            mListener = context as DefeatDialogListener
        } catch (e : ClassCastException) {
            // The context doesn't implement the interface, throw exception
            throw ClassCastException(context.toString()
                    + " must implement DefeatDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Build the dialog and set up the button click handlers
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.defeat_message)
                .setPositiveButton(R.string.defeat_finish) { _, _ ->
                    // Send the positive button event back to the host activity
                    mListener.onDefeatDialogPositiveClick(this@DialogDefeat)
                }
                .setNegativeButton(R.string.defeat_restart) { _, _ ->
                    // Send the negative button event back to the host activity
                    mListener.onDefeatDialogNegativeClick(this@DialogDefeat)
                }
        return builder.create()
    }
}
