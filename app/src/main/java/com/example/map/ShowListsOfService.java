package com.example.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ShowListsOfService extends AppCompatActivity {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lists_of_service);
        listView=findViewById(R.id.listview);
        CustomListForServices obj=new CustomListForServices(getApplicationContext());
        listView.setAdapter(obj);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ShowListsOfService.this,ShowItemsOnClickOnList.class);
                intent.putExtra("position",i);
                startActivity(intent);
            }
        });
    }
}