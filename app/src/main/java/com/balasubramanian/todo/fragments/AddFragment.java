package com.balasubramanian.todo.fragments;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.balasubramanian.todo.AlarmHelper;
import com.balasubramanian.todo.AlarmReceiverService;
import com.balasubramanian.todo.R;
import com.balasubramanian.todo.TodoApplication;
import com.balasubramanian.todo.db.DbGateway;
import com.balasubramanian.todo.tables.TasksTable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddFragment extends Fragment {

    private static final String TAG = "AddFragment";

    private DbGateway dbGateway;
    private EditText title;
    private EditText description;
    private TextView due_date;
    private TextView due_time;
    private Button save;
    private Button cancel;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        dbGateway = ((TodoApplication) getActivity().getApplication()).getDbGateway();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_add, container, false);
        setWidgets(fragmentView);
        setListeners();

        return fragmentView;
    }

    private void setWidgets(View fragmentView) {

        title = (EditText) fragmentView.findViewById(R.id.titleEditText);
        description = (EditText) fragmentView.findViewById(R.id.descriptionEditText);
        due_date = (TextView) fragmentView.findViewById(R.id.dateTextView);
        due_time = (TextView) fragmentView.findViewById(R.id.timeTextView);
        save = (Button) fragmentView.findViewById(R.id.saveButton);
        cancel = (Button) fragmentView.findViewById(R.id.cancelButton);
    }

    private void setListeners() {

        setSaveButtonListener();
        setCancelButtonListener();
        setDatePickerListener();
        setTimePickerListener();
    }

    private void setSaveButtonListener() {

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i(TAG, "Saving the task to database");
                saveTaskToDb();
                setReminder();

                showToastNotification();

                getFragmentManager().popBackStack();
            }
        });
    }

    private void setReminder() {

        String date = due_date.getText().toString();
        String time = due_time.getText().toString();

        int day = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]) - 1;
        int year = Integer.parseInt(date.split("-")[2]);
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        Map<String, String> extras = new HashMap<>();
        extras.put("TITLE", title.getText().toString());
        extras.put("DESCRIPTION", description.getText().toString());

        AlarmHelper.setSystemAlarm(getContext(), AlarmReceiverService.class, calendar, extras);
    }

    private void showToastNotification() {

        Toast message = Toast.makeText(getActivity().getApplicationContext(), "Task saved", Toast.LENGTH_SHORT);
        message.show();
    }

    private void setCancelButtonListener() {

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();
            }
        });
    }

    private void setDatePickerListener() {

        due_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDatePickerDialog();
            }
        });
    }

    private void setTimePickerListener() {

        due_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showTimePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setTextView(due_date);
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog() {

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setTextView(due_time);
        timePickerFragment.show(getFragmentManager(), "timePicker");
    }


    private void saveTaskToDb() {

        ContentValues row = new ContentValues();
        row.put(TasksTable.TITLE, title.getText().toString());
        row.put(TasksTable.DESCRIPTION, description.getText().toString());
        row.put(TasksTable.DUE_DATE, due_date.getText().toString());
        row.put(TasksTable.DUE_TIME, due_time.getText().toString());

        dbGateway.getWritableDatabase().insert(TasksTable.TABLE_NAME, null, row);

        Log.i(TAG, "New task saved successfully");
    }
}
