package com.balasubramanian.todo.mappers;

import android.database.Cursor;

import com.balasubramanian.todo.entities.Task;
import com.balasubramanian.todo.tables.TasksTable;

import java.util.ArrayList;
import java.util.List;

public class TasksMapper {

    public static List<Task> map(Cursor resultSet) {

        if(resultSet == null) {

            return null;
        }

        List<Task> tasks = new ArrayList<>();

        while(resultSet.moveToNext()) {

            String title = resultSet.getString(resultSet.getColumnIndex(TasksTable.TITLE));
            String description = resultSet.getString(resultSet.getColumnIndex(TasksTable.DESCRIPTION));
            String due_date = resultSet.getString(resultSet.getColumnIndex(TasksTable.DUE_DATE));
            String due_time = resultSet.getString(resultSet.getColumnIndex(TasksTable.DUE_TIME));

            tasks.add(new Task(title, description, due_date, due_time));
        }

        return tasks;
    }
}
