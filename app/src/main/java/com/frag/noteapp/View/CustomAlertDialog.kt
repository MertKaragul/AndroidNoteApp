package com.frag.noteapp.View

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.frag.noteapp.R
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.synthetic.main.custom_alert_view.*
import javax.inject.Inject

class CustomAlertDialog @Inject constructor(@ActivityContext context : Context): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_alert_view)

        dialog_close_button.setOnClickListener {
            dismiss()
        }

    }

    override fun dismiss() {
        super.dismiss()
    }

}