package com.example.androidplay;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainListActivity extends ListActivity {

    String[] activityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        activityList = getResources().getStringArray(R.array.android_activities);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, activityList));

    }

    public void onListItemClick(ListView parent, View v, int position, long id)
    {
        String activityType=activityList[position];

        switch (activityType) {
            case "Student Information":
                Intent intent = new Intent(this, DisplayLoginDetailsActivity.class);
                startActivity(intent);
                break;
            case "List View":
                Intent intentList = new Intent(this, ListViewActivity.class);
                startActivity(intentList);
                break;
        }
    }
}
