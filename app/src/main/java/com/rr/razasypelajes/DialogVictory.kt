package com.rr.razasypelajes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment

class DialogVictory : DialogFragment() {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */

    interface VictoryDialogListener {
        fun onVictoryDialogPositiveClick(dialog : DialogFragment)
        fun onVictoryDialogNegativeClick(dialog : DialogFragment)
    }

    // Use this instance of the interface to deliver action events
    private lateinit var mListener : VictoryDialogListener

    // Override the Fragment.onAttach() method to instantiate the ExitConfigDialogListener
    override fun onAttach(context : Context) {
        super.onAttach(context)
        // Verify that the host context implements the callback interface
        try {
            // Instantiate the ExitConfigDialogListener so we can send events to the host
            mListener = context as VictoryDialogListener
        } catch (e : ClassCastException) {
            // The context doesn't implement the interface, throw exception
            throw ClassCastException(context.toString()
                    + " must implement ExitConfigDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Build the dialog and set up the button click handlers
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.victory_message)
                .setPositiveButton(R.string.victory_finish) { _, _ ->
                    // Send the positive button event back to the host activity
                    mListener.onVictoryDialogPositiveClick(this@DialogVictory)
                }
                .setNegativeButton(R.string.victory_restart) { _, _ ->
                    // Send the negative button event back to the host activity
                    mListener.onVictoryDialogNegativeClick(this@DialogVictory)
                }
        return builder.create()
    }
}