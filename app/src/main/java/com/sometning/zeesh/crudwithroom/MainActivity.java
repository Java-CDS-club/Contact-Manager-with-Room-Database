package com.sometning.zeesh.crudwithroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sometning.zeesh.crudwithroom.Database.StudentEntity;
import com.sometning.zeesh.crudwithroom.Database.StudentsAppDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button add;
    TextView allStudents;
    EditText name, rollNo , cnic , dept , age;
    RadioGroup gender;
    RadioButton male, female;

    StudentsAppDatabase studentsAppDatabase;
    ArrayList<StudentEntity> studentArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        studentsAppDatabase = Room.databaseBuilder(getApplicationContext() , StudentsAppDatabase.class , "Student_DB").addCallback(callback).build();

        add = findViewById(R.id.add);
        allStudents = findViewById(R.id.allStudents);
        name = findViewById(R.id.name);
        rollNo = findViewById(R.id.rollno);
        cnic = findViewById(R.id.cnic);
        dept = findViewById(R.id.dept);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);


        allStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AllStudentsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String finalname = name.getText().toString().trim();
                String finalroll = rollNo.getText().toString().trim();
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
                    rollNo.setError("Field Required");
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
                    Toast.makeText(MainActivity.this, "Select your Gender", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(finalage))
                {
                    age.setError("Field Required");
                }
                else
                {
                    new AddStudentAsyncTask().execute(new StudentEntity(0,finalname,finalroll, finalcnic , finaldept , finalgender  , finalage));
                }

            }
        });


    }

    RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i("tag" , "create");

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            Log.i("tag" , "open");
        }
    };


    private class AddStudentAsyncTask extends AsyncTask<StudentEntity,Void,Void> {


        @Override
        protected Void doInBackground(StudentEntity... studentEntities) {

            long id = studentsAppDatabase.getStudentDAO().addStudent(studentEntities[0]);


            StudentEntity studentEntity = studentsAppDatabase.getStudentDAO().getStudent(id);

            studentArrayList.add(0, studentEntity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(MainActivity.this, "Student adds", Toast.LENGTH_SHORT).show();
        }
    }
}
