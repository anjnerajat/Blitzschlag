package com.example.dell.blitzschlag.Classes;

import java.util.Comparator;

public class Workshop {
    private String title,venue,registeration,image,contact,organisers,fees,duration;
    private boolean remember;
    private int date,start,end;

    public Workshop(){

    }

    public Workshop(String title,String venue,int start,int date,String registeration,String image,String contact,String organisers,String problemstatement,String prizes){
        this.title=title;
        this.venue=venue;
        this.start=start;
        this.date=date;
        this.remember=false;
        this.registeration=registeration;
        this.image=image;
        this.contact=contact;
        this.organisers=organisers;
    }

    public String getFees() {
        return fees;
    }

    public String getDuration() {
        return duration;
    }

    public int getEnd() {
        return end;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setRegisteration(String registeration) {
        this.registeration = registeration;
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

    public String getRegisteration() {
        return registeration;
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

    public static Comparator<Workshop> dateComparator = new Comparator<Workshop>() {
        @Override
        public int compare(Workshop jc1, Workshop jc2) {
            return (jc2.getDate() > jc1.getDate() ? -1 :
                    (jc2.getDate() == jc1.getDate() ? 0 : 1));
        }
    };

    public static Comparator<Workshop> startComparator = new Comparator<Workshop>() {
        @Override
        public int compare(Workshop jc1, Workshop jc2) {
            return (jc2.getStart() > jc1.getStart() ? -1 :
                    (jc2.getStart() == jc1.getStart() ? 0 : 1));
        }
    };

    public static Comparator<Workshop> titleComparator = new Comparator<Workshop>() {
        @Override
        public int compare(Workshop jc1, Workshop jc2) {
            return (int) (jc1.getTitle().compareTo(jc2.getTitle()));
        }
    };
}
