package com.example.database;

import static android.content.Intent.getIntent;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.data.TabletContract.TabletEntry;

public class TabletCursorAdapter  extends CursorAdapter {

    public TabletCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.tablet_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView= view.findViewById(R.id.imageViewTablet);
        TextView nameTextView=view.findViewById(R.id.nameTextView);
        TextView quntityTextView=view.findViewById(R.id.quntityTextView);
        TextView genTextView=view.findViewById(R.id.genTextView);

        ImageView imageView1=view.findViewById(R.id.imageView);

        String name=cursor.getString(cursor.getColumnIndexOrThrow(TabletEntry.COLUMN_NAME));
        //int guntity=cursor.getInt(cursor.getColumnIndexOrThrow(TabletEntry.COLUMN_QUN));
        String quntity=cursor.getString(cursor.getColumnIndexOrThrow(TabletEntry.COLUMN_QUN));
        String gen=cursor.getString(cursor.getColumnIndexOrThrow(TabletEntry.COLUMN_GEN));

        if (gen.equals("Таб")){
            imageView.setImageResource(R.drawable.freetabletka);
        }
        else if (gen.equals("Мл")){
            imageView.setImageResource(R.drawable.freeicontabletbuttleredtwo);
        }

        nameTextView.setText(name);
        quntityTextView.setText(quntity);
        genTextView.setText(gen);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Вы приняли таблетку!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
