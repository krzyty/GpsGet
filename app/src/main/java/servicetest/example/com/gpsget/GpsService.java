package servicetest.example.com.gpsget;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Genrover on 2017/4/20.
 */

public class GpsService extends Service {
    public static String table = "gps_data";
    private Gps gps=null;
    private boolean threadDisable=false;
    private final static String TAG=GpsService.class.getSimpleName();
    private MySQLiteOpenHelper helper;
    public static SQLiteDatabase db;
    private String lati;
    private String longi;
    private String time;
    private int flags=0;
    @Override
    public void onCreate() {
        super.onCreate();
        helper = new MySQLiteOpenHelper(this, "infom.db");
        db = helper.getWritableDatabase();
        Log.d("service","----------------------------------------------------------------------");
        gps=new Gps(GpsService.this);
        new Thread(new Runnable(){
            @Override
            public void run() {
                while (!threadDisable) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(gps!=null){ //当结束服务时gps为空
                        //获取经纬度
                        Location location=gps.getLocation();
                        if(location==null){
                            flags++;
                            Log.d("xxxxxxxxxxx","----------------------------------------------------------------------");
                            if(flags>4){
                                System.exit(0);
                                flags=0;
                            }
                        }
                        Log.d("service3333333333333","----------------------------------------------------------------------");
                        ContentValues contentValues = new ContentValues();
                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        longi=  (location==null?"":location.getLatitude()+"");
                        lati= (location==null?"":location.getLongitude()+"");
                        time = sDateFormat.format(new Date());
                        contentValues.put("time",time);
                        contentValues.put("longitude", longi);
                        contentValues.put("latitude", lati);
                        db.insert(table, null, contentValues);
                        Log.d("getgps","-----------------------------------------------------------------------");
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
    }


    public GetGpsBinder mBinder=new GetGpsBinder();
    class GetGpsBinder extends Binder {
        public void startGetGps(){
            gps=new Gps(GpsService.this);
            new Thread(new Runnable(){
                @Override
                public void run() {
                    while (!threadDisable) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(gps!=null){ //当结束服务时gps为空
                            //获取经纬度
                            Location location=gps.getLocation();
                            if(location==null){
                                Log.d("gpsnull", "-------------------------------");
                            }
                            ContentValues contentValues = new ContentValues();
                            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String time = sDateFormat.format(new java.util.Date());
                            contentValues.put("time",time);
                            contentValues.put("longitude", location.getLatitude());
                            contentValues.put("latitude", location.getLongitude());
                            db.insert(table, null, contentValues);
                            Log.d("getgps","----------------------------------------------------------------------");
                        }
                    }
                }
            }).start();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("------------","----------------------------------------------------------------------");
        return mBinder;
    }
}
