package servicetest.example.com.gpsget;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static servicetest.example.com.gpsget.GpsService.db;

public class MainActivity extends AppCompatActivity {
    private TextView show;
    private static String table = "gps_data";
    private GpsService.GetGpsBinder getGpsBinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            getGpsBinder =(GpsService.GetGpsBinder) service;
            getGpsBinder.startGetGps();
            Log.d("connection","----------------------------------------------------------------------");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("activity","----------------------------------------------------------------------");
        setContentView(R.layout.activity_main);
        show = (TextView) findViewById(R.id.show);
        //bindService(new Intent(this, GpsService.class),connection,BIND_AUTO_CREATE);
        startService(new Intent(this,GpsService.class));
    }

    public void readd(View v) {
        cursorWay(select());
        Log.d("------------","----------------------------------------------------------------------");
    }

    // 查询
    private Cursor select() {
        return db.query(table, new String[] {  "time", "longitude","latitude" },
                null, null, null, null, null);
    }

    private void cursorWay(Cursor cursor) {
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append("\nTime:"+cursor.getString(0)+"\nLongitude:" + cursor.getString(1)
                    + "\nLatitude:"+cursor.getString(2)+"\n");
        }
        show.setText(buffer);
    }
    @Override
    protected void onDestroy() {
        Log.d("closeactivity","-------------------------------------------");
        super.onDestroy();
    }
}
