package com.example.database.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class TabletContract {
    private TabletContract(){

    }
    public static final  int DATABASE_VERSION =1;
    public static final  String DATABASE_NAME ="TabletsDB";

    public static  final  String SCHEME="content://";

    public  static  final  String AUTHORITY="com.example.database";

    public  static  final  String PATH_TABLETS="tablets";

    public  static  final Uri BASE_CONTENT_URI= Uri.parse(SCHEME+AUTHORITY);

    public static  final  class TabletEntry implements BaseColumns {
        public  static  final  String TABLE_NAME="tablets";
        public  static  final  String _ID=BaseColumns._ID;//BaseColumns интерфейс с константами и заготовками
        public  static  final  String COLUMN_NAME="name";
        public  static  final  String COLUMN_QUNTITY="quntity";
        public  static  final  String COLUMN_GEN="gen";

        public  static  final  String GEN_UNKNOWN ="Не указано";
        public  static  final  String GEN_ML ="Мл";
        public  static  final  String GEN_MG ="Мг";
        public  static  final  String GEN_TAB="Таб";
       //155
        public  static  final  String CONTENT_MULTIPLE_ITEMS= ContentResolver.CURSOR_DIR_BASE_TYPE+ "/" + AUTHORITY+
               " /" + PATH_TABLETS;
        //155
        public  static  final  String CONTENT_SINGLE_ITEMS= ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+ AUTHORITY +
                " /"  + PATH_TABLETS;

        public  static  final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_TABLETS);

    }
}
