package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.database.data.TabletContract.TabletEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Med extends AppCompatActivity {
    //152
    ListView dataListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med);
        dataListView=findViewById(R.id.dataListView);
        FloatingActionButton floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Med.this,MainActivity2.class);
                startActivity(intent);

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    //152
    private void  displayData(){
        String [] projection = {
               TabletEntry._ID,
                TabletEntry.COLUMN_NAME,
                TabletEntry.COLUMN_QUNTITY,
                TabletEntry.COLUMN_GEN
        };
        //152
        Cursor cursor=getContentResolver().query(
                TabletEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
        TabletCursorAdapter tabletCursorAdapter =  new TabletCursorAdapter(this,cursor,false);
        dataListView.setAdapter(tabletCursorAdapter);
    }

}