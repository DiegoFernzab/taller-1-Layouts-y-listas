package com.example.tallerlayoutsylistas.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.tallerlayoutsylistas.R

fun launchDialer(context: Context, phone: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse(context.getString(R.string.tel, phone))
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}
