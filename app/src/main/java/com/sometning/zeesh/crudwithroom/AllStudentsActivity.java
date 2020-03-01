package com.sometning.zeesh.crudwithroom;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sometning.zeesh.crudwithroom.Adapter.StudentAdapter;
import com.sometning.zeesh.crudwithroom.Database.StudentEntity;
import com.sometning.zeesh.crudwithroom.Database.StudentsAppDatabase;

import java.util.ArrayList;

public class AllStudentsActivity extends AppCompatActivity {

    StudentAdapter studentAdapter;
    ArrayList<StudentEntity> studentArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    StudentsAppDatabase studentsAppDatabase;
    FloatingActionButton fab;
    RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_students);

        recyclerView = findViewById(R.id.allStudentsRecyclerView);
        studentsAppDatabase = Room.databaseBuilder(getApplicationContext(), StudentsAppDatabase.class, "Student_DB").addCallback(callback).build();


        new GetAllStudentssAsyncTask().execute();

        studentAdapter = new StudentAdapter(this, studentArrayList, AllStudentsActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(studentAdapter);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AllStudentsActivity.this  , MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    public void addAndEditStudents(final StudentEntity studentEntity, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialoge_student, null);

        Button del = view.findViewById(R.id.ddel);
        Button update = view.findViewById(R.id.dupdate);
        final EditText name = view.findViewById(R.id.dname);
        final EditText roll = view.findViewById(R.id.drollno);
        final EditText cnic = view.findViewById(R.id.dcnic);
        final EditText dept = view.findViewById(R.id.ddept);
        final EditText age = view.findViewById(R.id.dage);
        final RadioGroup gender = view.findViewById(R.id.dgender);
        RadioButton male = view.findViewById(R.id.dmale);
        final RadioButton female = view.findViewById(R.id.dfemale);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(AllStudentsActivity.this);
        alertDialogBuilderUserInput.setView(view);



        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStudent(studentEntity , position);
                alertDialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String finalname = name.getText().toString().trim();
                String finalroll = roll.getText().toString().trim();
                String finalcnic = cnic.getText().toString().trim();
                String finaldept = dept.getText().toString().trim();
                String finalage = age.getText().toString().trim();
                String finalgender = "male";

                int selectedId = gender.getCheckedRadioButtonId();
                if (selectedId == female.getId()) {
                    finalgender = "female";
                }

                if (TextUtils.isEmpty(finalname)) {
                    name.setError("Field Required");
                }else if(TextUtils.isEmpty(finalroll))
                {
                    roll.setError("Field Required");
                }
                else if(TextUtils.isEmpty(finalcnic))
                {
                    cnic.setError("Field Required");
                }
                else if(TextUtils.isEmpty(finaldept))
                {
                    dept.setError("Field Required");
                }
                else if(gender.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(AllStudentsActivity.this, "Select your Gender", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(finalage))
                {
                    age.setError("Field Required");
                }
                else
                {
                    updateStudent(new StudentEntity(0,finalname,finalroll, finalcnic , finaldept , finalgender  , finalage) , position);
                }

            }
        });


    }

    private void deleteStudent(StudentEntity studentEntity, int position) {

        studentArrayList.remove(position);

        new DeleteStudentAsyncTask().execute(studentEntity);

    }

    private void updateStudent(StudentEntity studentEntity, int position) {



        new UpdateStudentAsyncTask().execute(studentEntity);

        studentArrayList.set(position, studentEntity);

    }

    private class GetAllStudentssAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            studentArrayList.addAll(studentsAppDatabase.getStudentDAO().getStudents());
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            studentAdapter.notifyDataSetChanged();
        }
    }

    private class UpdateStudentAsyncTask extends AsyncTask<StudentEntity, Void, Void> {


        @Override
        protected Void doInBackground(StudentEntity... studentEntities) {

            studentsAppDatabase.getStudentDAO().updateStudent(studentEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            studentAdapter.notifyDataSetChanged();
        }
    }

    private class DeleteStudentAsyncTask extends AsyncTask<StudentEntity, Void, Void> {

        @Override
        protected Void doInBackground(StudentEntity... studentEntities) {

            studentsAppDatabase.getStudentDAO().deleteStudent(studentEntities[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            studentAdapter.notifyDataSetChanged();
            Toast.makeText(AllStudentsActivity.this, "Student Delete", Toast.LENGTH_SHORT).show();
        }
    }


}
