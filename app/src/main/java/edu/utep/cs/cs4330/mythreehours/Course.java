package edu.utep.cs.cs4330.mythreehours;

public class Course {
    private String name;
    private int desiredWeekHours;
    private double currWeekHours; //can be updated by 15 minute increments (.25 hrs)
    private double totalHours;
    //Start date (Pending Upgraded Version)
    //End date

    public Course(){
        name = null;
        desiredWeekHours = 0;
        currWeekHours = 0;

    }
    public Course(String name, int desiredHours, double currHours, double totalHours ){
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
    public void setCurrWeekHours(double currHours){
        this.currWeekHours = currHours;
    }
    public void setTotalHours(double totalHours){
        this.totalHours = totalHours;
    }

    /*************GETTERS******************/
    public String getName(){
        return this.name;
    }
    public int getDesiredWeekHours(){
        return this.desiredWeekHours;
    }
    public double getCurrWeekHours() {
        return this.currWeekHours;
    }
    public double getTotalHours(){
        return this.totalHours;
    }

    /*************MODIFIERS****************/
    public double numHoursRemaining() {
        if (this.desiredWeekHours >= this.currWeekHours) {
            return (this.desiredWeekHours - this.currWeekHours);

        }
        else{ //Some people might add more hours than needed
            return 0;
        }
    }
    public void addOneHour(){
        this.currWeekHours++;
        this.totalHours++;
    }
    public void addFifteenMins(){
        this.currWeekHours += .25;
        this.totalHours += .25;
    }
    public void addWholeHours(int numHours) {
        this.currWeekHours += numHours;
        this.totalHours += numHours;
    }

}
