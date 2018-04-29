package com.example.omnia.smartdoorbell.models;

/**
 * Created by omnia on 14/03/2018.
 */

public class history {

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    int img;
    String id,image,state,acthion,time,name,relation;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActhion() {
        return acthion;
    }

    public void setActhion(String acthion) {
        this.acthion = acthion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
