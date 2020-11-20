package com.nimashi.pawpaw;

public class PModel {
    String name,speciality,age,place,type;

    String image,id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PModel()
    {

    }
    public PModel(String name, String speciality, String age, String place, String type, String id, String image) {
        this.name = name;
        this.speciality = speciality;
        this.age = age;
        this.place = place;
        this.type = type;
        this.id = id;
        this.image = image;
    }
}
