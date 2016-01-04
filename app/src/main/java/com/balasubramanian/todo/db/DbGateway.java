package com.balasubramanian.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.balasubramanian.todo.tables.TasksTable;

public class DbGateway extends SQLiteOpenHelper {

    public DbGateway(Context context) {

        super(context, "todo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(new TasksTable().createQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(new TasksTable().dropQuery());
        onCreate(db);
    }
}
