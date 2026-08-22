package com.example.tallerlayoutsylistas.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun launchDialer(context: Context, phone: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phone")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}
