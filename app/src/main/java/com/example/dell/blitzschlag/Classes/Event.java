package com.example.dell.blitzschlag.Classes;

import java.util.Comparator;

public class Event {
    private String title,venue,registeration,image,contact,organisers,problemstatement,prizes;
    private boolean remember;
    private int date,start;

    public Event(){

    }

    public Event(String title,String venue,int start,int date,String registeration,String image,String contact,String organisers,String problemstatement,String prizes){
        this.title=title;
        this.venue=venue;
        this.start=start;
        this.date=date;
        this.remember=false;
        this.registeration=registeration;
        this.image=image;
        this.contact=contact;
        this.organisers=organisers;
        this.prizes=prizes;
        this.problemstatement=problemstatement;
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

    public void setProblemstatement(String problemstatement) {
        this.problemstatement = problemstatement;
    }

    public void setPrizes(String prizes) {
        this.prizes = prizes;
    }

    public String getPrizes() {

        return prizes;
    }

    public String getProblemstatement() {
        return problemstatement;
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

    public static Comparator<Event> dateComparator = new Comparator<Event>() {
        @Override
        public int compare(Event jc1, Event jc2) {
            return (jc2.getDate() > jc1.getDate() ? -1 :
                    (jc2.getDate() == jc1.getDate() ? 0 : 1));
        }
    };

    public static Comparator<Event> startComparator = new Comparator<Event>() {
        @Override
        public int compare(Event jc1, Event jc2) {
            return (jc2.getStart() > jc1.getStart() ? -1 :
                    (jc2.getStart() == jc1.getStart() ? 0 : 1));
        }
    };

    public static Comparator<Event> titleComparator = new Comparator<Event>() {
        @Override
        public int compare(Event jc1, Event jc2) {
            return (int) (jc1.getTitle().compareTo(jc2.getTitle()));
        }
    };

}

