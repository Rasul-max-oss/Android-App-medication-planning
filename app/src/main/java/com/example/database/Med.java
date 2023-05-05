package com.example.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.database.data.TabletContract.TabletEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Med extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private  static  final int TABLET_LOADER=123;
    TabletCursorAdapter tabletCursorAdapter;
    //Класс с отображением  лекарств (Аптечка)
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

        tabletCursorAdapter = new TabletCursorAdapter(this,null,false);
        dataListView.setAdapter(tabletCursorAdapter);
        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Med.this,MainActivity2.class);
                Uri currentTabletUri = ContentUris.withAppendedId(TabletEntry.CONTENT_URI,id);
                intent.setData(currentTabletUri);
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(TABLET_LOADER,null,this);

    }


    /* //больше не нужен так как есть Loader  160
    @Override
    protected void onStart() {
        super.onStart();

    }
     */



    //152
    /*
    private void  displayData(){
        String [] projection = {
               TabletEntry._ID,
                TabletEntry.COLUMN_NAME,
                TabletEntry.COLUMN_QUN,
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
        TabletCursorAdapter cursorAdapter =  new TabletCursorAdapter(this,cursor,false);
        dataListView.setAdapter(cursorAdapter);
    }

     */

    @NonNull
    @Override//160
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String [] projection = {
                TabletEntry._ID,
                TabletEntry.COLUMN_NAME,
                TabletEntry.COLUMN_QUN,
                TabletEntry.COLUMN_GEN
        };
        //160
        CursorLoader cursorLoader =new CursorLoader(this,
                TabletEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
        return cursorLoader;
    }
//160
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        tabletCursorAdapter.swapCursor(cursor);
    }
//160
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        tabletCursorAdapter.swapCursor(null);

    }
}