package t.com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Date: 2018/6/26
 * Time: 0:02
 * author :mark lee
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_BOOK = "create table Book(" + "id integer primary key autoincrement,"
            + "author text," + "price integer," + "pages integer," + "name text," + "category_id integer)";

    public static final String CREATE_CATEGORY = "create table Category(" + "id integer primary key autoincrement,"
            + "category text," + "category_code integer)";


    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//建表
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);//创建另一个表
        Toast.makeText(mContext, "创建数据库成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//数据库版本更新 //升级数据库的最佳写法
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_CATEGORY);
            case 2:
                db.execSQL("alter table Book add column category_id integer");
            default:
        }

    }
}
