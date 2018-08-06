package edu.utep.cs.cs4330.mythreehours;

public class Course {
    private String name;
    private int desiredWeekHours;
    private double currWeekHours; //can be updated by 15 minute increments (.25 hrs)
    private double totalHours;
    private String courseID;
    private String courseWebsite;
    private String description;
    //Start date (Pending Upgraded Version)
    //End date

    protected Course(){
        name = null;
        desiredWeekHours = 0;
        currWeekHours = 0;
        totalHours = 0;
    }
    protected Course(String name, int desiredHours, double currHours, double totalHours,
                     String courseID, String courseWebsite, String description){
        this.name = name;
        this.desiredWeekHours = desiredHours;
        this.currWeekHours = currHours;
        this.totalHours = 0;
        this.courseID = courseID;
        this.courseWebsite = courseWebsite;
        this.description = description;
    }

    protected Course(String name, int desiredHours, double currHours, double totalHours ){
        this.name = name;
        this.desiredWeekHours = desiredHours;
        this.currWeekHours = currHours;
        this.totalHours = 0;
        this.courseID = null;
        this.courseWebsite = null;
        this.description = null;
    }

    protected Course(String name, int desiredHours){
        this.name = name;
        this.desiredWeekHours = desiredHours;
        this.currWeekHours = 0;
        this.totalHours = 0;
        this.courseID= null;
        this.courseWebsite = null;
        this.description = null;
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
    public void setCourseID(String courseID){
        this.courseID = courseID;
    }
    public void setCourseWebsite(String website){
        this.courseWebsite = website;
    }
    public void setDescription(String description){
        this.description = description;
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
    public String getCourseID(){
        return this.courseID;
    }
    public String getCourseWebsite(){
        return this.courseWebsite;
    }
    public String getDescription(){
        return this.description;
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

    public boolean isEmpty(){
        if(this.name == null && this.desiredWeekHours == 0){
            return true;
        }
        return false;
    }
}
