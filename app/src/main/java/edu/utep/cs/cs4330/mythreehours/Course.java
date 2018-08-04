package edu.utep.cs.cs4330.mythreehours;

public class Course {
    private String name;
    private int desiredWeekHours;
    private int currWeekHours;
    private int totalHours;
    //Start date (data type pending)
    //End date

    public Course(){
        name = null;
        desiredWeekHours = 0;
        currWeekHours = 0;

    }
    public Course(String name, int desiredHours, int currHours, int totalHours ){
        this.name = name;
        this.currWeekHours = currHours;
        this.desiredWeekHours = desiredHours;
    }

    /*************SETTERS*******************/
    public void setName(String name){
        this.name = name;
    }
    public void setDesiredWeekHours(int desiredHours){
        this.desiredWeekHours = desiredHours;
    }
    public void setCurrWeekHours(int currHours){
        this.currWeekHours = currHours;
    }
    public void setTotalHours(int totalHours){
        this.totalHours = totalHours;
    }
    /*************GETTERS******************/
    public String getName(){
        return this.name;
    }
    public int getDesiredWeekHours(){
        return this.desiredWeekHours;
    }
    public int getCurrWeekHours() {
        return this.currWeekHours;
    }
    public int getTotalHours(){
        return this.totalHours;
    }

    /*************MODIFIERS****************/
    public void addOneHour(){
        this.currWeekHours++;
        this.totalHours++;
    }
    public void addHours(int numHours){
        this.currWeekHours += numHours;
        this.totalHours += numHours;
    }

}
