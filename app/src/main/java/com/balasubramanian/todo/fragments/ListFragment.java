package com.balasubramanian.todo.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.balasubramanian.todo.db.DbGateway;
import com.balasubramanian.todo.R;
import com.balasubramanian.todo.SwitchToAddFragmentDelegate;
import com.balasubramanian.todo.TodoApplication;
import com.balasubramanian.todo.adapters.TaskListAdapter;
import com.balasubramanian.todo.entities.Task;
import com.balasubramanian.todo.mappers.TasksMapper;
import com.balasubramanian.todo.tables.TasksTable;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";

    private DbGateway dbGateway;
    private SwitchToAddFragmentDelegate delegate;
    private ListView taskListView;
    private Button addButton;
    private TaskListAdapter taskListAdapter;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        dbGateway = ((TodoApplication) (getActivity().getApplication())).getDbGateway();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);

        taskListAdapter = new TaskListAdapter(getContext(), new ArrayList<Task>());

        setWidgets(fragmentView);
        setListeners();

        return fragmentView;
    }

    @Override
    public void onResume() {

        super.onResume();

        populateTasksInUI();
    }

    public void setDelegate(SwitchToAddFragmentDelegate delegate) {

        this.delegate = delegate;
    }

    private void setWidgets(View fragmentView) {

        taskListView = (ListView) fragmentView.findViewById(R.id.taskListView);
        taskListView.setAdapter(taskListAdapter);

        addButton = (Button) fragmentView.findViewById(R.id.addButton);
    }

    private void setListeners() {

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (delegate != null) {

                    delegate.switchToAddFragment();
                } else {
                    Log.e(TAG, "Delegate is null");
                }
            }
        });
    }

    private void populateTasksInUI() {

        List<Task> tasks = fetchTasksFromDb();
        taskListAdapter.setTaskList(tasks);
        taskListAdapter.notifyDataSetChanged();
    }

    private List<Task> fetchTasksFromDb() {

        Cursor results = dbGateway.getWritableDatabase().query(TasksTable.TABLE_NAME, null, null, null, null, null, null);
        List<Task> tasks = TasksMapper.map(results);
        results.close();

        return tasks;
    }
}
