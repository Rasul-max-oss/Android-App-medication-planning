package com.example.database;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.dataDoctor.DoctorDataBaseHelper;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> book_id, doctor_name,doctor_lastname,doctor_surname, doctor_spec, doctor_number;
    DoctorDataBaseHelper myDB;
    Button doctor_addBtn;
    Adapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        recyclerView=findViewById(R.id.recyclerViewDoctor);
        doctor_addBtn=findViewById(R.id.doctor_addBtn);

        doctor_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DoctorActivity.this, AddDoctorActivity.class);
                startActivity(intent);
            }
        });
        myDB = new DoctorDataBaseHelper(DoctorActivity.this);
        book_id=new ArrayList<>();
        doctor_name = new ArrayList<>();
        doctor_lastname=new ArrayList<>();
        doctor_surname=new ArrayList<>();
        doctor_spec = new ArrayList<>();
        doctor_number = new ArrayList<>();
        storeDataInArrays();
        adapter = new Adapter(DoctorActivity.this,book_id,doctor_name,doctor_lastname,doctor_surname,doctor_spec,
                doctor_number);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorActivity.this));
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.DisplayAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                doctor_name.add(cursor.getString(1));
                doctor_lastname.add(cursor.getString(2));
                doctor_surname.add(cursor.getString(3));
                doctor_spec.add(cursor.getString(4));
                doctor_number.add(cursor.getString(5));
            }
        }
    }
}