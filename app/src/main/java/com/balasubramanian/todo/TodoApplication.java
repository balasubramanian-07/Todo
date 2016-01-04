package com.balasubramanian.todo;

import android.app.Application;
import android.content.Context;

import com.balasubramanian.todo.db.DbGateway;

public class TodoApplication extends Application {

    private Context context;
    private DbGateway dbGateway;

    public DbGateway getDbGateway() {

        return dbGateway;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();
        dbGateway = new DbGateway(context);
    }
}
