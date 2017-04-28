package servicetest.example.com.gpsget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Genrover on 2017/4/26.
 */

public class AutoStart extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
            Intent intent1=new Intent(context,GpsService.class);
            context.startService(intent1);
            //Intent i = new Intent(context, MainActivity.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            //context.startActivity(i);
        }
    }
}
