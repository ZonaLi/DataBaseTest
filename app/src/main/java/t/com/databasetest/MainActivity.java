package t.com.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import t.com.database.MyDataBaseHelper;

public class MainActivity extends AppCompatActivity {

    private Button byId;
    private MyDataBaseHelper myDataBaseHelper;
    private Button addData;
    private Button upDate;
    private Button deleteDate;
    private Button queryDate;
    private Button replaceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDataBaseHelper =new MyDataBaseHelper(getApplication(),"BookStore.db",null,3);
         byId = findViewById(R.id.btn);
        addData =findViewById(R.id.addData);
        upDate =findViewById(R.id.upDate);
        deleteDate =findViewById(R.id.deleteDate);
        queryDate =findViewById(R.id.queryDate);
        replaceData =findViewById(R.id.replaceData);
         byId.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 myDataBaseHelper.getWritableDatabase();

             }
         });
         addData.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {//增加数据
                 SQLiteDatabase database = myDataBaseHelper.getWritableDatabase();//这一步创建数据库
                 ContentValues values =new ContentValues();
                 values.put("name","The first Book");
                 values.put("author","Dan Brown");
                 values.put("pages","4456");
                 values.put("price","100.03");
                 database.insert("Book",null,values);
                 values.clear();
                 values.put("name","Android Book");
                 values.put("author","Lee");
                 values.put("pages","509");
                 values.put("price","20.09");
                 database.insert("Book",null,values);

             }
         });
         upDate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SQLiteDatabase database = myDataBaseHelper.getWritableDatabase();//打开数据库
                 ContentValues values =new ContentValues();
                 values.put("price","34.56");
                 database.update("Book",values,"name =?",new String[]{"Android Book"});

             }
         });
        deleteDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = myDataBaseHelper.getWritableDatabase();
                database.delete("Book","name=?",new String[]{"Jack"});

            }
        });
        queryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = myDataBaseHelper.getWritableDatabase();
                Cursor cursor = database.query("Book", null, null, null, null, null, null, null);//查询所有表
                if(cursor.moveToFirst()){
                    do {
                        /*遍历Cursor对象，取出并打印*/
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity",name);
                        Log.d("MainActivity",author);
                        Log.d("MainActivity",String.valueOf(pages));
                        Log.d("MainActivity",String.valueOf(price));


                    }while (cursor.moveToNext());


                }
                cursor.close();

            }
        });
        replaceData.setOnClickListener(new View.OnClickListener() {//数据替换操作 删除和添加数据一起完成
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = myDataBaseHelper.getWritableDatabase();
                database.beginTransaction();//开启事务
                try {
                    database.delete("Book", null, null);

                    ContentValues values = new ContentValues();
                    values.put("name", "Game of Thrones");
                    values.put("author", "George Martin");
                    values.put("pages", "600");
                    values.put("price", "50.43");
                    database.insert("Book", null, values);
                    database.setTransactionSuccessful();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    database.endTransaction();
                }

            }
        });
    }
}
