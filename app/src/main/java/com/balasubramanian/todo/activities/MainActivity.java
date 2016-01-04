package com.balasubramanian.todo.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.balasubramanian.todo.R;
import com.balasubramanian.todo.SwitchToAddFragmentDelegate;
import com.balasubramanian.todo.fragments.AddFragment;
import com.balasubramanian.todo.fragments.ListFragment;

import static com.balasubramanian.todo.Constants.ADD_FRAGMENT_TAG;
import static com.balasubramanian.todo.Constants.LIST_FRAGMENT_TAG;
import static com.balasubramanian.todo.Constants.LIST_TO_ADD_FRAGMENT_CODE;

public class MainActivity extends AppCompatActivity implements SwitchToAddFragmentDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            loadListFragment();
        } else {

            ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT_TAG);
            listFragment.setDelegate(this);
        }

    }

    @Override
    public void switchToAddFragment() {

        AddFragment addFragment = new AddFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment listFragment = fragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG);

        if (listFragment != null) {

            addFragment.setTargetFragment(listFragment, LIST_TO_ADD_FRAGMENT_CODE);
            fragmentTransaction.remove(listFragment);
            fragmentTransaction.add(R.id.mainLayout, addFragment, ADD_FRAGMENT_TAG);
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    private void loadListFragment() {

        ListFragment listFragment = new ListFragment();
        listFragment.setDelegate(this);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainLayout, listFragment, LIST_FRAGMENT_TAG);
        fragmentTransaction.commit();
    }
}
