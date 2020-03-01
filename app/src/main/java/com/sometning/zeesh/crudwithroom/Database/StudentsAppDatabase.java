package com.sometning.zeesh.crudwithroom.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {StudentEntity.class},version = 1)
public abstract class StudentsAppDatabase extends RoomDatabase {

    public abstract StudentDAO getStudentDAO();
}
