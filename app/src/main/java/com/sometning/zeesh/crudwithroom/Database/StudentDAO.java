package com.sometning.zeesh.crudwithroom.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDAO {

    @Insert
    public  long addStudent(StudentEntity student);

    @Update
    public void updateStudent(StudentEntity student);

    @Delete
    public void deleteStudent(StudentEntity student);

    @Query("select * from students")
    public List<StudentEntity> getStudents();

    @Query("select * from students where student_id ==:studentId")
    public StudentEntity getStudent(long studentId);


}
