package com.gamecodeschool.en_leo.Model;

public class Event {
    public String title;
    public String desc;
    public String image;
    public String timestamp;
    public String date;
    public String time;
    public String Mtime;

    public Event(){
    }

    public Event(String title, String desc, String image, String timestamp, String date, String time, String Mtime) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.timestamp = timestamp;
        this.date = date;
        this.time = time;
        this.Mtime = Mtime;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
