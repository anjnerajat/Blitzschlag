package com.example.dell.blitzschlag.Classes;

public class Wish {
    String title,venue,type;
    String date,start;

    public Wish(){

    }

    public Wish(String title,String venue,String  type,String date,String start){
        this.date=date;
        this.start=start;
        this.title=title;
        this.venue=venue;
        this.type=type;
    }

    public String getTitle() {
        return title;
    }

    public String getVenue() {
        return venue;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
