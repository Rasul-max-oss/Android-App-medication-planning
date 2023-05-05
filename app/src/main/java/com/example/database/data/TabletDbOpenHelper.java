package com.example.database.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.database.data.TabletContract.TabletEntry;
import androidx.annotation.Nullable;

public class TabletDbOpenHelper extends SQLiteOpenHelper {


    public TabletDbOpenHelper( Context context) {
        super(context, TabletContract.DATABASE_NAME, null, TabletContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLETS_TABLE= "CREATE TABLE " + TabletEntry.TABLE_NAME + "("
                + TabletEntry._ID + " INTEGER PRIMARY KEY,"
                + TabletEntry.COLUMN_NAME + " TEXT,"
                + TabletEntry.COLUMN_QUN + " INTEGER NOT NULL, "
                + TabletEntry.COLUMN_GEN + " TEXT" + ")";
        db.execSQL(CREATE_TABLETS_TABLE);
     }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TabletContract.DATABASE_NAME);
        onCreate(db);
    }


}
