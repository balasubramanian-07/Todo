package com.balasubramanian.todo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.balasubramanian.todo.R;
import com.balasubramanian.todo.entities.Task;

import java.util.List;

public class TaskListAdapter extends BaseAdapter {

    private static final String TAG = "TaskListAdapter";
    private Context context;
    private List<Task> tasks;

    public TaskListAdapter(Context context, List<Task> tasks) {

        this.context = context;
        this.tasks = tasks;
    }

    public void setTaskList(List<Task> tasks) {

        this.tasks = tasks;
    }

    @Override
    public int getCount() {

        return tasks.size();
    }

    @Override
    public Object getItem(int position) {

        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View recycledView, ViewGroup parent) {

        View mainView = (recycledView == null ? createView(LayoutInflater.from(context)) : recycledView);

        ViewHolder viewHolder = (ViewHolder) mainView.getTag();
        viewHolder.titleTextView.setText(tasks.get(position).getTitle());
        viewHolder.descriptionTextView.setText(tasks.get(position).getDescription());
        viewHolder.dueDateTextView.setText(tasks.get(position).getDueDate());
        viewHolder.dueTimeTextView.setText(tasks.get(position).getDueTime());

        return mainView;
    }

    private View createView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.row, null);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.titleTextView = (TextView) view.findViewById(R.id.titleRowTextView);
        viewHolder.descriptionTextView = (TextView) view.findViewById(R.id.descriptionRowTextView);
        viewHolder.dueDateTextView = (TextView) view.findViewById(R.id.dueDateRowTextView);
        viewHolder.dueTimeTextView = (TextView) view.findViewById(R.id.dueTimeRowTextView);

        view.setTag(viewHolder);

        return view;
    }

    private static class ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        TextView dueDateTextView;
        TextView dueTimeTextView;
    }
}
