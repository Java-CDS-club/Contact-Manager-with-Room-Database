package com.sometning.zeesh.crudwithroom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sometning.zeesh.crudwithroom.AllStudentsActivity;
import com.sometning.zeesh.crudwithroom.Database.StudentEntity;
import com.sometning.zeesh.crudwithroom.R;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {


    private Context context;
    private ArrayList<StudentEntity> studentEntitiesList;
    private AllStudentsActivity allStudentsActivity;

    public StudentAdapter(Context context, ArrayList<StudentEntity> studentEntitiesList, AllStudentsActivity allStudentsActivity) {
        this.context = context;
        this.studentEntitiesList = studentEntitiesList;
        this.allStudentsActivity = allStudentsActivity;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, final int position) {

        final StudentEntity studentEntity = studentEntitiesList.get(position);

        holder.name.setText("Name              : " + studentEntity.getName());
        holder.roll.setText("Roll No            : " + studentEntity.getRoll_no());
        holder.cnic.setText("CNIC                : " + studentEntity.getCnic());
        holder.dept.setText("Department  : " + studentEntity.getDepartment());
        holder.gender.setText("Gender           : " + studentEntity.getGender());
        holder.age.setText("Age                  : " + studentEntity.getAge());

        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                allStudentsActivity.addAndEditStudents( studentEntity, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentEntitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name , roll , cnic , dept , gender , age;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.studentName);
            roll = itemView.findViewById(R.id.studentRollNo);
            cnic = itemView.findViewById(R.id.studentCNIC);
            dept = itemView.findViewById(R.id.studentDept);
            gender = itemView.findViewById(R.id.studentGender);
            age = itemView.findViewById(R.id.studentAge);


        }
    }
}
