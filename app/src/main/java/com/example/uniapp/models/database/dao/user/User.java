package com.example.uniapp.models.database.dao.user;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.io.Serializable;

@Entity (tableName = "user", primaryKeys = {"first_name", "last_name"})
public class User implements Serializable {
    @NonNull
    @ColumnInfo(name = "first_name")
    private String firstname;

    @NonNull
    @ColumnInfo(name = "last_name")
    private String lastname;

    @ColumnInfo(name = "birthday")
    private String birthday;

    @ColumnInfo(name = "height")
    private int height;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "club")
    private String club;

    @ColumnInfo(name = "spe")
    private String spe;

    @ColumnInfo(name = "city_training")
    private String cityTraining;

    @ColumnInfo(name = "gender")
    private String gender;

    public User(String gender, String firstname, String lastname, String birthday, int height, int weight, String club, String spe, String cityTraining) {
        this.gender = gender;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.club = club;
        this.spe = spe;
        this.cityTraining = cityTraining;
    }

    @NonNull
    @Override
    public String toString() {
        return "gender : " + gender + " firstname : " + firstname + "\nlastname : " + lastname + "\n"
                + "birthday : " + birthday + "\nheight : " + height + "\nweight : " + weight + "\n"
                + "club : " + club + "\nspe : " + spe + "\ncity : " + cityTraining;
    }

    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getBirthday() { return birthday; }
    public int getHeight() { return height; }
    public int getWeight() { return weight; }
    public String getClub() { return club; }
    public String getSpe() { return spe; }
    public String getCityTraining() { return cityTraining; }
    public String getGender() { return (gender.equals("Homme") ? "girl" : "boy"); }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setHeight(int height) { this.height = height; }
    public void setWeight(int weight) { this.weight = weight; }
    public void setClub(String club) { this.club = club; }
    public void setSpe(String spe) { this.spe = spe; }
    public void setCityTraining(String cityTraining) { this.cityTraining = cityTraining; }
    public void setGender(String gender) { this.gender = gender; }
}
