package com.example.database.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TabletContentProvider extends ContentProvider {

    TabletDbOpenHelper tabletDbOpenHelper;
    private  static  final  int TABLETS = 111;
    private  static  final  int TABLETS_ID = 222;
    private  static  final UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
    //147
    static {
        uriMatcher.addURI(TabletContract.AUTHORITY,TabletContract.PATH_TABLETS,TABLETS);
        uriMatcher.addURI(TabletContract.AUTHORITY,TabletContract.PATH_TABLETS+ "/#",TABLETS_ID);
    }

    @Override
    public boolean onCreate() {
        tabletDbOpenHelper= new TabletDbOpenHelper(getContext());
        return true;
    }
    //148
    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = tabletDbOpenHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match){
            case TABLETS:
                cursor=db.query(TabletContract.TabletEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case TABLETS_ID:
                selection= TabletContract.TabletEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor=db.query(TabletContract.TabletEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                Toast.makeText(getContext(),"incorrect URI",Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("CAN QUERY INCORECT URI" + uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }


    @Override
    //149
    public Uri insert( Uri uri, ContentValues contentValues) {

        String firstName=contentValues.getAsString(TabletContract.TabletEntry.COLUMN_NAME);//156
        if(firstName==null){
            throw new IllegalArgumentException("Введите значение для названия");
        }
        //******** возможно ошибка
        String quntity=contentValues.getAsString(TabletContract.TabletEntry.COLUMN_QUN);;
        if (quntity==null){
            throw new IllegalArgumentException("Введите значение для quntity");
        }
        String gen=contentValues.getAsString(TabletContract.TabletEntry.COLUMN_GEN);
        if (gen==null || !(gen== TabletContract.TabletEntry.GEN_UNKNOWN || gen== TabletContract.TabletEntry.GEN_TAB
                ||gen== TabletContract.TabletEntry.GEN_MG || gen== TabletContract.TabletEntry.GEN_ML)){
            throw new IllegalArgumentException("Введите правильное значение для gender");
        }
        SQLiteDatabase db = tabletDbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match){
            case TABLETS:
                long id =db.insert(TabletContract.TabletEntry.TABLE_NAME,null,contentValues);
                if (id==1){
                    Log.e("Insetr method","Insertion of data in the table failed for "+ uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,id);
            default:
                throw new IllegalArgumentException("Insertion of data in the table failed for" + uri);

        }

    }

    @Override
    //154
    public int delete(Uri uri,  String selection, String[] selectionArgs) {
        SQLiteDatabase db= tabletDbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsDeleted;
        switch (match){
            case TABLETS:
                rowsDeleted =db.delete(TabletContract.TabletEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case TABLETS_ID:
                selection= TabletContract.TabletEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted =db.delete(TabletContract.TabletEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("CAN DELETE  THIS  URI" + uri);
        }
        if (rowsDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    //153
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        //************156 возможно ошибка
        if (contentValues.containsKey(TabletContract.TabletEntry.COLUMN_NAME)){
            String firstName=contentValues.getAsString(TabletContract.TabletEntry.COLUMN_NAME);//156
            if(firstName==null){
                throw new IllegalArgumentException("Введите значение для названия");
            }
        }
        if (contentValues.containsKey(TabletContract.TabletEntry.COLUMN_QUN)){
            String quntity=contentValues.getAsString(TabletContract.TabletEntry.COLUMN_QUN);;
            if (quntity==null){
                throw new IllegalArgumentException("Введите значение для quntity");
            }
        }
        //******** возможно ошибка

        if (contentValues.containsKey(TabletContract.TabletEntry.COLUMN_GEN)){
            String gen=contentValues.getAsString(TabletContract.TabletEntry.COLUMN_GEN);
            if (gen==null || !(gen== TabletContract.TabletEntry.GEN_UNKNOWN || gen== TabletContract.TabletEntry.GEN_TAB
                    ||gen== TabletContract.TabletEntry.GEN_MG || gen== TabletContract.TabletEntry.GEN_ML)){
                throw new IllegalArgumentException("Введите правильное значение для gender");
            }
        }

        SQLiteDatabase db= tabletDbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsUpdated;
        switch (match){
            case TABLETS:
                rowsUpdated= db.update(TabletContract.TabletEntry.TABLE_NAME,contentValues,selection,selectionArgs);
                break;
            case TABLETS_ID:
                selection= TabletContract.TabletEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated= db.update(TabletContract.TabletEntry.TABLE_NAME,contentValues,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("CAN UPDATE THIS URI" + uri);
        }
        if(rowsUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match){
            case TABLETS:
                return TabletContract.TabletEntry.CONTENT_MULTIPLE_ITEMS;
            case TABLETS_ID:
                return TabletContract.TabletEntry.CONTENT_SINGLE_ITEMS;
            default:
                throw new IllegalArgumentException("Unknown  URI: " + uri);

        }

    }

}
