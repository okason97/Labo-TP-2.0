package com.rr.razasypelajes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment

class ExitDialog : DialogFragment() {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */

    interface ExitDialogListener {
        fun onExitDialogPositiveClick(dialog : DialogFragment)
    }

    // Use this instance of the interface to deliver action events
    private lateinit var mListener : ExitDialogListener

    // Override the Fragment.onAttach() method to instantiate the ExitConfigDialogListener
    override fun onAttach(context : Context) {
        super.onAttach(context)
        // Verify that the host context implements the callback interface
        try {
            // Instantiate the ExitConfigDialogListener so we can send events to the host
            mListener = context as ExitDialogListener
        } catch (e : ClassCastException) {
            // The context doesn't implement the interface, throw exception
            throw ClassCastException(context.toString()
                    + " must implement ExitConfigDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Build the dialog and set up the button click handlers
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.quit_message)
                .setPositiveButton(R.string.accept) { _, _ ->
                    // Send the positive button event back to the host activity
                    mListener.onExitDialogPositiveClick(this@ExitDialog)
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    // Close the dialog
                    dismiss()
                }
        return builder.create()
    }
}