package com.example.dell.blitzschlag.Classes;

import java.util.Comparator;

public class Schedule {
    String title,venue,type,image;
    int date,start;

    public Schedule(){

    }

    public Schedule(String title,String venue,String  type,int date,int start,String image){
        this.date=date;
        this.start=start;
        this.title=title;
        this.venue=venue;
        this.type=type;
        this.image=image;
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

    public int getDate() {
        return date;
    }

    public int getStart() {
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

    public void setDate(int date) {
        this.date = date;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Comparator<Schedule> dateComparator = new Comparator<Schedule>() {
        @Override
        public int compare(Schedule jc1, Schedule jc2) {
            return (jc2.getDate() > jc1.getDate() ? -1 :
                    (jc2.getDate() == jc1.getDate() ? 0 : 1));
        }
    };

    public static Comparator<Schedule> startComparator = new Comparator<Schedule>() {
        @Override
        public int compare(Schedule jc1, Schedule jc2) {
            return (jc2.getStart() > jc1.getStart() ? -1 :
                    (jc2.getStart() == jc1.getStart() ? 0 : 1));
        }
    };

    public static Comparator<Schedule> titleComparator = new Comparator<Schedule>() {
        @Override
        public int compare(Schedule jc1, Schedule jc2) {
            return (int) (jc1.getTitle().compareTo(jc2.getTitle()));
        }
    };

}
