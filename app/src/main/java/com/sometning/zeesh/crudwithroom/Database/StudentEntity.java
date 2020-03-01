package com.sometning.zeesh.crudwithroom.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class StudentEntity {

    @ColumnInfo(name = "student_id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "student_name")
    private String name;

    @ColumnInfo(name = "student_roll_no")
    private String roll_no;

    @ColumnInfo(name = "student_CNIC")
    private String cnic;

    @ColumnInfo(name = "student_dept")
    private String department;

    @ColumnInfo(name = "student_gender")
    private String gender;

    @ColumnInfo(name = "student_age")
    private String age;

    @Ignore
    public StudentEntity() {
    }

    public StudentEntity(long id, String name, String roll_no, String cnic, String department, String gender, String age) {
        this.id = id;
        this.name = name;
        this.roll_no = roll_no;
        this.cnic = cnic;
        this.department = department;
        this.gender = gender;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
