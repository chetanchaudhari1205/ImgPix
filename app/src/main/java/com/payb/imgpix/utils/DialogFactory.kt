package com.payb.imgpix.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import javax.inject.Singleton

/**
 * DialogFactory class to provide alert dialog related utility methods
 */
@Singleton
class DialogFactory {

    /**
     * Method to create Material Alert Dialog
     * @param context
     * @param title : Dialog title
     * @param message : Dialog description
     * @param buttonPositiveText : text of positive button
     * @param buttonNegativeText : text of negative button
     * @param positiveListener : positive button click listener
     * @param negativeListener : negative button click listener
     * @return [AlertDialog]
     */
    fun createAlertMaterialDialog(
        context: Context?,
        title: String,
        message: String,
        buttonPositiveText: String,
        buttonNegativeText: String,
        positiveListener: DialogInterface.OnClickListener,
        negativeListener: DialogInterface.OnClickListener
    ): AlertDialog {
        val customAlertDialog: AlertDialog = this.createAlertDialogBuilder(context).create()
        customAlertDialog.setTitle(title)
        customAlertDialog.setMessage(message)
        customAlertDialog.setButton(-1, buttonPositiveText, positiveListener)
        customAlertDialog.setButton(-2, buttonNegativeText, negativeListener)
        return customAlertDialog
    }

    /**
     * Method to create a builder for the alert dialog
     * @param context
     * @return [AlertDialog.Builder]
     */
    fun createAlertDialogBuilder(context: Context?): AlertDialog.Builder {
        return AlertDialog.Builder(context!!)
    }

    /**
     * Method to show alert dialog
     * @param alertDialog
     */
    fun showDialog(alertDialog: Dialog) {
        alertDialog.show()
    }

    /**
     * Method to dismiss the alert dialog
     * @param alertDialog
     */
    fun dismissDialog(alertDialog: Dialog) {
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }

}