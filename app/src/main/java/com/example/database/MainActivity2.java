package com.example.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.data.TabletContract.TabletEntry;
public class MainActivity2 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    //Класс с  добавлением лекарства
    Uri currentTabletUri;//163
    private static final int  EDIT_TABLET_LOADER=111;//163
    private EditText nameEdit;
    private EditText quntityEdit;
    private Spinner  spinner;
    private  String gen="Не указано";

    private ArrayAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent= getIntent();//162

        currentTabletUri=intent.getData();//162

        if (currentTabletUri==null){
            setTitle("Добавление лекарств");
        }else {
            setTitle(" Редактирование ");
            getSupportLoaderManager().initLoader(EDIT_TABLET_LOADER,null,this);
        }
        nameEdit=findViewById(R.id.name);
        quntityEdit=findViewById(R.id.quntity);
        spinner=findViewById(R.id.spinner);
        spinnerAdapter=ArrayAdapter.createFromResource(this,R.array.array_spiner, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectValue=(String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selectValue)){
                    if(selectValue.equals("Таб")){
                        gen= TabletEntry.GEN_TAB;//ссылаемся к константом из класса бд
                    }else if(selectValue.equals("Мл")){
                        gen= TabletEntry.GEN_ML;//ссылаемся к константом из класса бд
                    }else if(selectValue.equals("Мг")){
                        gen= TabletEntry.GEN_MG;//ссылаемся к константом из класса бд
                    }else {gen=TabletEntry.GEN_UNKNOWN;}//ссылаемся к константом из класса бд
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gen= String.valueOf(0);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_tablets_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_member:
                saveTablet();//150
                return  true;
            case R.id.delete_member:
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    //150
    private  void saveTablet(){
        String name=nameEdit.getText().toString().trim();
        //int quntity=quntityEdit.getText().toString().
        int quntity= Integer.parseInt(quntityEdit.getText().toString().trim());
        //Валидация
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Введите название лекарства!", Toast.LENGTH_LONG).show();
            return;
        }
        //Валидация для количество. Не забудь сделать!

        //Валидация вида лекарства
        else if(gen==TabletEntry.GEN_UNKNOWN){
            Toast.makeText(this, "Выберите вид лекарства!", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TabletEntry.COLUMN_NAME,name);
        contentValues.put(TabletEntry.COLUMN_QUN,quntity);
        contentValues.put(TabletEntry.COLUMN_GEN,gen);


        //проверяем если у нас uri пуст то мы добавляем, иначе просто сохраняем данные
        if (currentTabletUri==null){
            ContentResolver contentResolver = getContentResolver();
            Uri uri= contentResolver.insert(TabletEntry.CONTENT_URI,contentValues);
            if (uri == null){
                Toast.makeText(this, "Insertion of data in the table failed for", Toast.LENGTH_LONG).show();
            }
            else{ Toast.makeText(this, "data save", Toast.LENGTH_LONG).show();}
        }else {
            int rowsChanged=getContentResolver().update(currentTabletUri,contentValues,null,null);
            if (rowsChanged==0){Toast.makeText(this, "save of data in the table failed for", Toast.LENGTH_LONG).show();}
            else {Toast.makeText(this, "tablet updated", Toast.LENGTH_LONG).show();}
        }
    }

    @NonNull
    @Override//163
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String [] projection= {
                TabletEntry._ID,
                TabletEntry.COLUMN_NAME,
                TabletEntry.COLUMN_QUN,
                TabletEntry.COLUMN_GEN
        };
        return new CursorLoader(this,
                currentTabletUri,
                projection,
                null,
                null,
                null
        );
    }
    //163
    @Override//163
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()){
            int nameColumnIndex=cursor.getColumnIndex(TabletEntry.COLUMN_NAME);
            int gunColumnIndex=cursor.getColumnIndex(TabletEntry.COLUMN_QUN);
            int genColumnIndex=cursor.getColumnIndex(TabletEntry.COLUMN_GEN);

            String name=cursor.getString(nameColumnIndex);
            String gun=cursor.getString(gunColumnIndex);
            String gen=cursor.getString(genColumnIndex);
            //int g=Integer.parseInt(gun)-5;
            //String g2=Integer.toString(g);
            nameEdit.setText(name);
            quntityEdit.setText(gun);

            switch (gen){
                case TabletEntry.GEN_TAB:
                    spinner.setSelection(1);
                    break;
                case TabletEntry.GEN_ML:
                    spinner.setSelection(2);
                    break;
                case TabletEntry.GEN_UNKNOWN:
                    spinner.setSelection(0);
            }
        }
    }
    //163
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}