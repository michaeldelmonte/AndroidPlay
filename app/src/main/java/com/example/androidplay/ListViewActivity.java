package com.example.androidplay;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewActivity extends ListActivity {
    String[] activityList;
    ListView brand_list;
    ArrayList arrayList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view2);

        arrayList=new ArrayList<String>();


//        activityList = getResources().getStringArray(R.array.list_view_array);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_checked, arrayList);
    }

    public void onListItemClick(android.widget.ListView parent, View v, int position, long id)
    {
        String itemSelected = arrayList.get(position).toString();
        Log.d("onListItemClick: ",itemSelected);

        Fragment1 dialogFragment = Fragment1.newInstance("Viewed",itemSelected, "list_view");
        dialogFragment.show(getFragmentManager(), "dialog");

//        Toast.makeText(this,"Selected: " + itemSelected,Toast.LENGTH_SHORT).show();
    }

    public void handleSaveToList(View view) {
        Fragment1 dialogFragment = Fragment1.newInstance("Add Brand To List","", "");
        dialogFragment.show(getFragmentManager(), "dialog");
        //        Toast.makeText(this,"Selected: " + itemSelected,Toast.LENGTH_SHORT).show();
    }

    public void doPositiveClick(String brand_name) {
        String brand_name_text=brand_name;

        brand_list = (ListView) findViewById(android.R.id.list);
        brand_list.setAdapter(adapter);
        arrayList.add(brand_name_text);
        adapter.notifyDataSetChanged();


        Toast.makeText(this,"Saved Item: " +brand_name_text,Toast.LENGTH_SHORT).show();

        Log.d("@@User clicks OK: ",brand_name_text);
    }

    public void doNegativeClick() {
        Log.d("DialogFragment", "User clicks Cancel");
    }
}
