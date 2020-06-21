package application.example.mytodo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import application.example.mytodo.model.ToDoModel;
import application.example.mytodo.model.UserModel;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ToDo.db";
    public static final String USER = "user";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String PASSWORD = "password";
    public static final String TODOITEM = "todoitem";
    public static final String TODOLIST = "todolist";
    public static final String ID = "id";


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table1 = "CREATE TABLE " + USER + "(" +
                USER_NAME + " TEXT PRIMARY KEY, " +
                USER_EMAIL + " TEXT NOT NULL, " +
                PASSWORD + " TEXT NOT NULL)";

        String create_table2 = "CREATE TABLE " + TODOLIST + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT NOT NULL, " +
                TODOITEM + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + USER_NAME + ") REFERENCES user(" + USER_NAME + "))";

        db.execSQL(create_table1);
        db.execSQL(create_table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String drop_table1 = "DROP TABLE IF EXISTS " + USER;
        String drop_table2 = "DROP TABLE IF EXISTS " + TODOLIST;

        db.execSQL(drop_table1);
        db.execSQL(drop_table2);

    }

    // Insertion in User Table
    public boolean insertUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        String username = user.getuserName();
        String email = user.getuserEmail();
        String password = user.getPassword();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, username);
        values.put(USER_EMAIL, email);
        values.put(PASSWORD, password);

        long success = db.insert(USER, null, values);
        if (success == -1) {
            return false;
        }
        return true;

    }

    public boolean userExists(String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER +" WHERE " + USER_NAME +" = ? AND " + PASSWORD +" = ?";


        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;

    }


    // Insertion in TODO Table
    public boolean insertTodo(ToDoModel todo) {
        String username = todo.getUserName();
        String todoItem = todo.getTodoItem();

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, username);
        values.put(TODOITEM, todoItem);

        long success = db.insert(TODOLIST, null, values);

        if (success == -1) {
            return false;
        }
        return true;

    }

    // Deletion in Todo table
    public boolean deleteTodo(ToDoModel todo) {

        int id = todo.getId();
        Log.e("ID", "deleteTodo: "+id);
        SQLiteDatabase db = getWritableDatabase();
        int success = db.delete(TODOLIST, ID + " = ?", new String[]{id+""});

        if (success == 1) {
            return true;
        }
        return false;

    }

    // Retrieve existing todo list for particular user
    public ArrayList<ToDoModel> getToDoList(String username) {
        ArrayList<ToDoModel> list = new ArrayList<>();

        String query = "SELECT * FROM " + TODOLIST + " WHERE " + USER_NAME + " = ?";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {username});
        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                Log.e("List", "id: " + cursor.getInt(0) + "name: " + cursor.getString(1) + " todo: " + cursor.getString(2));
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String todoItem = cursor.getString(2);
                list.add(new ToDoModel(id, name, todoItem));
            }
        }
        return list;

    }

}
