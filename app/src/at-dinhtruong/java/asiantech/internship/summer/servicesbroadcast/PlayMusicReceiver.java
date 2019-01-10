package asiantech.internship.summer.servicesbroadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import asiantech.internship.summer.R;

@SuppressLint("Registered")
public class PlayMusicReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(ServicesBroadcastActivity.EXTRA_BUTTON_CLICKED, -1);
        Log.d("xxxxxxx", "onReceive: "+id);
        switch (id) {
            case R.id.btnPlayOrPause:
                Toast.makeText(context, "Play", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnClose:
                Toast.makeText(context, "close", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
