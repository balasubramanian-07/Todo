package com.balasubramanian.todo.tables;

public class TasksTable implements BaseTable {

    public static final String TABLE_NAME = "tasks";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DUE_DATE = "due_date";
    public static final String DUE_TIME = "due_time";

    public String createQuery() {

        return "CREATE TABLE tasks("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + TITLE + " TEXT NOT NULL,"
                + DESCRIPTION + " TEXT NOT NULL,"
                + DUE_DATE + " TEXT NOT NULL,"
                + DUE_TIME + " TEXT NOT NULL"
                + ")";
    }

    public String dropQuery() {

        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
