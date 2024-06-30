package coder.bekhzod

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Receiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val message = intent?.getStringExtra("MESSAGE")
        val stopMessage = intent?.getStringExtra("STOP")
        if (message != null) Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
        if (stopMessage != null) Toast.makeText(context,stopMessage, Toast.LENGTH_SHORT).show()
    }
}