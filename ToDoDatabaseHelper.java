package fpl.quangnm.lab1.demo2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {
    public ToDoDatabaseHelper( Context context) {
        super(context, "todoList.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE todos (\n" +
                "  id integer PRIMARY KEY AUTOINCREMENT,\n" +
                "  title text,\n" +
                "  content text,\n" +
                "  dates text,\n" +
                "  type text,\n" +
                "  status integer\n" +
                ");";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS todos");
    }
}
