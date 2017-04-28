package servicetest.example.com.gpsget;

/**
 * Created by Genrover on 2017/4/19.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    // 应用程序首次运行时调用，用以建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSql = "create table gps_data(_id integer primary key autoincrement,time,latitude,longitude)";
        db.execSQL(createSql);
    }

    // 版本号更新后调用，可以增加表或者修改表
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}