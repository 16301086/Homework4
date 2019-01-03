package com.example.lenovo.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyEvents extends AppCompatActivity {
    int z = 0;
    private DBOpenHelper dbOpenHelper;
    private TextView cname,cname1,cname2,tname,tname1,tname2,time,time1,time2,taken,taken1,taken2,left,left1,left2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_events);
        cname = findViewById(R.id.cname);
        cname1 = findViewById(R.id.cname1);
        cname2 = findViewById(R.id.cname2);
        tname = findViewById(R.id.tname);
        tname1 = findViewById(R.id.tname1);
        tname2 = findViewById(R.id.tname2);
        taken = findViewById(R.id.taken);
        taken1 = findViewById(R.id.taken1);
        taken2 = findViewById(R.id.taken2);
        time = findViewById(R.id.time);
        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        left = findViewById(R.id.left);
        left1 = findViewById(R.id.left1);
        left2 = findViewById(R.id.left2);
        boolean net = NetworkConnected(MyEvents.this);
        if (net){
            this.dbOpenHelper = new DBOpenHelper(MyEvents.this);
            Toast.makeText(MyEvents.this, "Up to date!", Toast.LENGTH_LONG).show();
            loadData();
        }
        else{
            Toast.makeText(MyEvents.this, "Network unavailable!Read local cache!", Toast.LENGTH_LONG).show();
            //readCache();
        }
    }

    /**
     * 判断当前是否有网络连接
     * @param context
     * @return
     */
    public boolean NetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()){   // 判断网络连接是否打开
            return networkInfo.isConnected();
        }
        return false;
    }

    public void loadData() {
        BmobQuery<events> bmobQuery = new BmobQuery<events>();
        bmobQuery.findObjects(new FindListener<events>() {
            @Override
            public void done(List<events> list, BmobException e) {
                events r;
                if (e == null) {
                    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                    int n = list.size();
                    System.out.println(n);
                    for (int i = 0; i < n; i++) {
                        r = list.get(i);
                        //String stu_sql = "insert into events(id,name,coach,time,taken,left) values('%d','%s','%s','%s','%s','%s')",i,name,coach,time,taken,left;
                        ContentValues cValue = new ContentValues();
                        cValue.put("id",i);cValue.put("name",r.name);cValue.put("coach",r.coach);cValue.put("time",r.time);cValue.put("taken",r.taken);cValue.put("left",r.left);
                        db.insert("events",null,cValue);
                        switch (i){
                            case 0:
                                cname1.setText(r.name);
                                tname1.setText(r.coach);
                                time1.setText(r.time);
                                taken1.setText(r.taken);
                                left1.setText(r.left);
                                break;
                            case 1:
                                cname2.setText(r.name);
                                tname2.setText(r.coach);
                                time2.setText(r.time);
                                taken2.setText(r.taken);
                                left2.setText(r.left);
                                break;
                            case 2:
                                cname.setText(r.name);
                                tname.setText(r.coach);
                                time.setText(r.time);
                                taken.setText(r.taken);
                                left.setText(r.left);
                                break;
                        }
                    }
                } else {
                    System.out.println(e.getErrorCode());
                }
            }
        });
    }

    public void readCache(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();//.getReadableDatabase();
        for (int i = 0; i < z ;i++){
            Cursor cursor = db.rawQuery("select * from events where id=?", new String[]{String.valueOf(i)});
            if(cursor.moveToFirst()) {
                String name_ = cursor.getString(cursor.getColumnIndex("name"));
                String coach_ = cursor.getString(cursor.getColumnIndex("coach"));
                String time_ = cursor.getString(cursor.getColumnIndex("time"));
                String taken_ = cursor.getString(cursor.getColumnIndex("taken"));
                String left_ = cursor.getString(cursor.getColumnIndex("left"));
                switch (i){
                    case 0:
                        cname1.setText(name_);
                        tname1.setText(coach_);
                        time1.setText(time_);
                        taken1.setText(taken_);
                        left1.setText(left_);
                        break;
                    case 1:
                        cname2.setText(name_);
                        tname2.setText(coach_);
                        time2.setText(time_);
                        taken2.setText(taken_);
                        left2.setText(left_);
                        break;
                    case 2:
                        cname.setText(name_);
                        tname.setText(coach_);
                        time.setText(time_);
                        taken.setText(taken_);
                        left.setText(left_);
                        break;
                }
            }
        }
    }
}

class events extends BmobObject {
    String name;
    String coach;
    String time;
    String taken;
    String left;
}

//创建和更新数据库
class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASENAME = "sample.db"; //数据库名称
    private static final int DATABASEVERSION = 1;//数据库版本

    public DBOpenHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String stu_table="create table events(id int,name VARCHAR(50),coach VARCHAR(50),time VARCHAR(50),taken VARCHAR(50), left_ VARCHAR(50))";
        db.execSQL(stu_table);
    }
    //数据库版本或表结构改变会被调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
