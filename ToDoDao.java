package fpl.quangnm.lab1.demo2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ToDoDao {
    private SQLiteDatabase db;
    public ToDoDao(Context context){
        ToDoDatabaseHelper helper = new ToDoDatabaseHelper(context);  // goi ham CSDL
        db = helper.getWritableDatabase();  // ghi du lieu vao db
    }

    public long addToDo(Todo todo){  //  no se tra ve con int se loi con void la kh quan tam ket qua tra ve
        ContentValues values = new ContentValues();  // chua du lieu
        values.put("title", todo.getTitle());
        values.put("content", todo.getContent());
        values.put("dates", todo.getDate());
        values.put("type", todo.getType());
        values.put("status", todo.getStatus());
        return db.insert("todos", null, values);
    }
    public List<Todo> getAllTodos(){
        List<Todo> todos = new ArrayList<>();
        Cursor cursor = db.query("todos",null,null,
                null,null,null,"id DESC");

        if (cursor.moveToFirst()){  // di chyen den ban ghi dau tien
            do {
                @SuppressLint("Range") Todo todo = new Todo(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("content")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("type")),
                        cursor.getInt(cursor.getColumnIndex("status"))
                );
                todos.add(todo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return todos;
    }
    public void updateToDOStatus (int id, int status){
        ContentValues values = new ContentValues();
        values.put("status", status);
        db.update("todos", values,"id=?", new String[]{String.valueOf(id)});
    }

    public void updataToDO(Todo todo){   // void k qtam den trang thai
        ContentValues values = new ContentValues(); // doi tuong chua du lieu can update
        values.put("title", todo.getTitle());
        values.put("content", todo.getContent());
        values.put("dates", todo.getDate());
        values.put("type", todo.getType());
        values.put("status", todo.getStatus());
        db.update("todos",values,"id=?", new String[]{String.valueOf(todo.getId())});
    }
    public void deleteTodo(int id){
        db.delete("todos","id=?",new String[]{String.valueOf(id)});
    }
}
