package com.payb.imgpix.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

/**
 * DialogFactoryTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class DialogFactoryTest {

    private lateinit var dialogFactory: DialogFactory

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var dialogPositiveOnClickListener: DialogInterface.OnClickListener

    @Mock
    lateinit var dialogNegativeOnClickListener: DialogInterface.OnClickListener

    @Mock
    lateinit var dialogBuilder: AlertDialog.Builder

    @Mock
    lateinit var alertDialog: AlertDialog

    @Before
    fun setup() {
        dialogFactory = spy(DialogFactory())
    }

    @Test
    fun testCreateAlertMaterialDialog() {
        doReturn(dialogBuilder).whenever(dialogFactory).createAlertDialogBuilder(context)
        doReturn(alertDialog).whenever(dialogBuilder).create()
        Assert.assertNotNull(
            dialogFactory.createAlertMaterialDialog(
                context,
                "title",
                "message",
                "Yes",
                "No",
                dialogPositiveOnClickListener,
                dialogNegativeOnClickListener
            )
        )
    }

    @Test
    fun testShowDialog() {
        dialogFactory.showDialog(alertDialog)
        verify(alertDialog, times(1)).show()
    }

    @Test
    fun testDismissDialog() {
        doReturn(true).whenever(alertDialog).isShowing
        dialogFactory.dismissDialog(alertDialog)
        verify(alertDialog, times(1)).dismiss()
    }

    @Test
    fun testDismissDialogNotShowing() {
        doReturn(false).whenever(alertDialog).isShowing
        dialogFactory.dismissDialog(alertDialog)
        verify(alertDialog, never()).dismiss()
    }
}