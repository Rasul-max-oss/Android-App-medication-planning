package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
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
public class MainActivity2 extends AppCompatActivity {

    private EditText nameEdit;
    private EditText quntityEdit;
    private Spinner  spinner;
    private  String gen="Не указано";

    private ArrayAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
                insertTablet();//150
                return  true;
            case R.id.delete_member:
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    //150
    private  void insertTablet(){
        String name=nameEdit.getText().toString().trim();
        //int quntity=quntityEdit.getText().toString().
        int quntity= Integer.parseInt(quntityEdit.getText().toString().trim());

        ContentValues contentValues = new ContentValues();
        contentValues.put(TabletEntry.COLUMN_NAME,name);
        contentValues.put(TabletEntry.COLUMN_QUNTITY,quntity);
        contentValues.put(TabletEntry.COLUMN_GEN,gen);

        ContentResolver contentResolver = getContentResolver();
        Uri uri= contentResolver.insert(TabletEntry.CONTENT_URI,contentValues);
        if (uri == null){
            Toast.makeText(this, "Insertion of data in the table failed for", Toast.LENGTH_LONG).show();
        }
        else{ Toast.makeText(this, "data save", Toast.LENGTH_LONG).show();}
    }
}