package com.example.database;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList book_id, doctor_name,doctor_lastname,doctor_surname,doctor_spec, doctor_number;


    Adapter(Context context, ArrayList book_id, ArrayList doctor_name,ArrayList doctor_lastname, ArrayList doctor_surname, ArrayList doctor_spec,
            ArrayList doctor_number){
        this.context = context;
        this.book_id = book_id;
        this.doctor_name = doctor_name;
        this.doctor_lastname=doctor_lastname;
        this.doctor_surname=doctor_surname;
        this.doctor_spec = doctor_spec;
        this.doctor_number = doctor_number;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_rowdoctor,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        holder.doctor_name_txt.setText(String.valueOf(doctor_name.get(position)));
        holder.doctor_lastname_txt.setText(String.valueOf(doctor_lastname.get(position)));
        holder.doctor_surname_txt.setText(String.valueOf(doctor_surname.get(position)));
        holder.doctor_spec_txt.setText(String.valueOf(doctor_spec.get(position)));
        holder.doctor_number_txt.setText(String.valueOf(doctor_number.get(position)));
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView book_id_txt, doctor_name_txt,doctor_lastname_txt, doctor_surname_txt,doctor_spec_txt, doctor_number_txt;
        LinearLayout mainLayout;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_name_txt = itemView.findViewById(R.id.doctor_name_txt);
            doctor_lastname_txt=itemView.findViewById(R.id.doctor_lastname_txt);
            doctor_surname_txt=itemView.findViewById(R.id.doctor_surname_txt);
            doctor_spec_txt = itemView.findViewById(R.id.doctor_spec_txt);
            doctor_number_txt = itemView.findViewById(R.id.doctor_number_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout2);

        }
    }
}
