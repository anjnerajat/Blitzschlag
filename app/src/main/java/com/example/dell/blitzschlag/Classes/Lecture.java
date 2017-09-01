package com.example.dell.blitzschlag.Classes;

import java.util.Comparator;

public class Lecture {
    private String title,venue,image,contact,organisers,speaker,duration;
    private boolean remember;
    private int date,start;

    public Lecture(){

    }

    public Lecture(String title,String venue,int start,int date,String image,String contact,String organisers,String speaker,String duration){
        this.title=title;
        this.venue=venue;
        this.start=start;
        this.date=date;
        this.remember=false;
        this.image=image;
        this.contact=contact;
        this.organisers=organisers;
        this.duration=duration;
        this.speaker=speaker;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getDuration() {
        return duration;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setOrganisers(String organisers) {
        this.organisers = organisers;
    }

    public String getOrganisers() {
        return organisers;
    }

    public String getContact() {
        return contact;
    }

    public String getImage() {
        return image;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getVenue(){
        return venue;
    }

    public void setVenue(String venue){
        this.venue=venue;
    }

    public  int getStart(){
        return start;
    }

    public void setStart(int start){
        this.start=start;
    }

    public int getDate(){
        return date;
    }

    public void setDate(int date){
        this.date=date;
    }

    public boolean getRemember(){
        return remember;
    }

    public void setRemember(){
        remember=true;
    }

    public static Comparator<Lecture> dateComparator = new Comparator<Lecture>() {
        @Override
        public int compare(Lecture jc1, Lecture jc2) {
            return (jc2.getDate() > jc1.getDate() ? -1 :
                    (jc2.getDate() == jc1.getDate() ? 0 : 1));
        }
    };

    public static Comparator<Lecture> startComparator = new Comparator<Lecture>() {
        @Override
        public int compare(Lecture jc1, Lecture jc2) {
            return (jc2.getStart() > jc1.getStart() ? -1 :
                    (jc2.getStart() == jc1.getStart() ? 0 : 1));
        }
    };

    public static Comparator<Lecture> titleComparator = new Comparator<Lecture>() {
        @Override
        public int compare(Lecture jc1, Lecture jc2) {
            return (int) (jc1.getTitle().compareTo(jc2.getTitle()));
        }
    };
}
