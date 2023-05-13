package com.example.database;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.database.dataDoctor.DoctorDataBaseHelper;

public class AddDoctorActivity extends AppCompatActivity {
    EditText name_input, speciality_input, lastname_input,surname_input ,number_input;
    Button addDoctorBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        name_input = findViewById(R.id.name_input);
        speciality_input = findViewById(R.id.speciality_input);
        number_input = findViewById(R.id.number_input);
        lastname_input=findViewById(R.id.lastname_input);
        surname_input=findViewById(R.id.surname_input);

        addDoctorBtn=findViewById(R.id.addDoctorBtn);

        addDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    DoctorDataBaseHelper myDB= new DoctorDataBaseHelper(AddDoctorActivity.this);
                     myDB.addDoctor(name_input.getText().toString().trim(),
                        lastname_input.getText().toString().trim(),
                        surname_input.getText().toString().trim(),
                        speciality_input.getText().toString().trim(),
                        number_input.getText().toString().trim());
            }
        });


    }
}