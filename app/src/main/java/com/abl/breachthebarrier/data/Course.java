package com.abl.breachthebarrier.data;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Course {

    @PropertyName("id")
    private int id;

    @PropertyName("name")
    private String name;

    @PropertyName("image")
    private String image;

   @DocumentId
    private String title;

    public Course(int id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public Course(){}

    public int getId() {
        return id;
    }

    public String getIdToString(){
        return Integer.toString(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
