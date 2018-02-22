//Christopher Holt
//CS232 Project
package cs232project;
import java.util.Scanner;
import java.time.LocalTime;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
abstract class Course{
    String courseName;
    String crn;
    String courseNumber;
    String courseSection;
    String courseCredits;
    LocalTime courseStart;
    LocalTime courseEnd;
    String courseDays;
    String courseLocation;
    String courseInstructor;
    String courseType;
    public Course(){}
    public void setCourseName(String courseName){
        this.courseName=courseName;
    }
    public void setCRN(String crn){
        this.crn=crn;
    }
    public void setCourseNumber(String courseNumber){
        this.courseNumber=courseNumber;
    }
    public void setCourseSection(String courseSection){
        this.courseSection=courseSection;
    }
    public void setCourseCredits(String courseCredits){
        this.courseCredits=courseCredits;
    }
    public void setCourseStart(LocalTime courseStart){
        this.courseStart=courseStart;
    }
    public void setCourseEnd(LocalTime courseEnd){
        this.courseEnd=courseEnd;
    }
    public void setCourseDays(String courseDays){
        this.courseDays=courseDays;
    }
    public void setCourseLocation(String courseLocation){
        this.courseLocation=courseLocation;
    }
    public void setCourseInstructor(String courseInstructor){
        this.courseInstructor=courseInstructor;
    }
    public void setCourseType(String courseType){
        this.courseType=courseType;
    }
    public String getCourseNumber(){
        return courseNumber;
    }
    @Override
    public abstract String toString();
    public abstract boolean conflictsWith(Course course);
}
class Traditional extends Course{
    @Override
    public boolean conflictsWith(Course course){
        boolean conflicts=false;
        if (course.courseNumber.equals(this.courseNumber)){
            conflicts=true;
        }
        if (course.courseDays.equals(this.courseDays)&&course.courseStart.equals(this.courseStart)){
             conflicts=true;
        }
        return conflicts;
    }
    @Override
    public String toString(){
        return ("#"+crn+": "+courseNumber+"-"+courseSection+" ("+courseName+"), "+courseInstructor+", "+courseType+", "+courseStart+" - "+courseEnd+", "+courseDays+", "+courseLocation);
    }
}
class Blended extends Course{
    @Override
    public boolean conflictsWith(Course course){
        boolean conflicts=false;
        if (course.courseNumber.equals(this.courseNumber)){
            conflicts=true;
        }
        return conflicts;
    }
     @Override
     public String toString(){
        return ("#"+crn+": "+courseNumber+"-"+courseSection+" ("+courseName+"), "+courseInstructor+", "+courseType);
     }
}
class Online extends Course{
     @Override
     public boolean conflictsWith(Course course){
        boolean conflicts=false;
        if (course.courseNumber.equals(this.courseNumber)){
            conflicts=true;
        }
        return conflicts;
    }     
     @Override
     public String toString(){
         return ("#"+crn+": "+courseNumber+"-"+courseSection+" ("+courseName+"), "+courseInstructor+", "+courseType);
     }
}

public class Cs232Project {
    public static void main(String[] args) throws IOException{
        Scanner file=new Scanner(new File("project-input.csv"));
        ArrayList<Course> courseList=new ArrayList<>();
        while (file.hasNextLine()){
            String courseRead=file.nextLine();
            String[] courseInfo=courseRead.split("\t");
            if (courseInfo[5].equals("World Wide Web")){
                Course readCourse=new Online();
                readCourse.setCourseName(courseInfo[0]);
                readCourse.setCRN(courseInfo[1]);
                readCourse.setCourseNumber(courseInfo[2]);
                readCourse.setCourseSection(courseInfo[3]);
                readCourse.setCourseCredits(courseInfo[4]);
                readCourse.setCourseType(courseInfo[5]);
                readCourse.setCourseInstructor(courseInfo[6]);
                courseList.add(readCourse);                
            }
            else if(courseInfo[5].equals("Blended 51-99% online")){
                Course readCourse=new Blended();
                readCourse.setCourseName(courseInfo[0]);
                readCourse.setCRN(courseInfo[1]);
                readCourse.setCourseNumber(courseInfo[2]);
                readCourse.setCourseSection(courseInfo[3]);
                readCourse.setCourseCredits(courseInfo[4]);
                readCourse.setCourseType(courseInfo[5]);
                readCourse.setCourseInstructor(courseInfo[6]);
                courseList.add(readCourse);
            }
            else{
                Course readCourse=new Traditional();
                readCourse.setCourseName(courseInfo[0]);
                readCourse.setCRN(courseInfo[1]);
                readCourse.setCourseNumber(courseInfo[2]);
                readCourse.setCourseSection(courseInfo[3]);
                readCourse.setCourseCredits(courseInfo[4]);
                LocalTime start=LocalTime.parse(courseInfo[5]);
                LocalTime end= LocalTime.parse(courseInfo[6]);
                readCourse.setCourseStart(start);
                readCourse.setCourseEnd(end);
                readCourse.setCourseDays(courseInfo[7]);
                readCourse.setCourseLocation(courseInfo[8]);
                readCourse.setCourseType(courseInfo[9]);
                readCourse.setCourseInstructor(courseInfo[10]);
                courseList.add(readCourse);
            }
        }
        Scanner read=new Scanner(System.in);
        ArrayList<Course> schedule=new ArrayList<>();
        boolean exit=true;
        while (exit){
            System.out.println("\n1) Search Courses");
            System.out.println("2) Register For Course");
            System.out.println("3) Remove Course From Schedule");
            System.out.println("4) View Trial Schedule");
            System.out.println("5) Quit\n");
            System.out.println("Choice: ");
            String choice=read.nextLine();
            if (choice.equals("1")){
                System.out.println("Enter the course number in the following format SSNNN (Example: CS230): ");
                String courseChoice= read.nextLine();
                courseChoice=courseChoice.trim();
                courseChoice=courseChoice.toUpperCase();
                courseChoice=courseChoice.replace(" ","");
                boolean notFound= true;
                for(Course course:courseList){
                    Course readCourse=course;
                    String tempCN=readCourse.getCourseNumber();
                    tempCN=tempCN.replace(" ","");
                    if (courseChoice.equals(tempCN)){
                        System.out.println(readCourse.toString());
                        notFound=false;
                    }
                }
                if (notFound){
                    System.out.println("Course does not exist.\n");
                }
            }
            else if (choice.equals("2")){
                System.out.println("Enter a CRN: ");
                String crnChoice=read.nextLine();
                crnChoice=crnChoice.trim();
                boolean conflictsTest=false;
                boolean notAdded=true;
                int i=0;
                for (Course course:courseList){
                    if (course.crn.equals(crnChoice)){                        
                        schedule.add(course);    
                    }
                    else{}
                }
                try{
                    Course addedCourse=schedule.get(schedule.size()-1);    
                    schedule.remove(schedule.size()-1);
                    for (Course registered:schedule){
                        if (registered.conflictsWith(addedCourse)){
                            conflictsTest=true;
                        }
                        else{}
                    }                
                    if(conflictsTest){
                        System.out.println("This course conflicts with your current schedule\n");
                        notAdded=false;
                    }
                    else{
                        schedule.add(addedCourse);
                        System.out.println("Course added successfully! ");
                        notAdded=false;
                    }
                    if(notAdded){
                        System.out.println("Course not found ");
                    }                
                }
                catch(ArrayIndexOutOfBoundsException e ){System.out.println("Course not found\n");}                                
            }
            else if(choice.equals("3")){
                System.out.println("Enter a CRN: ");
                String crnChoice=read.nextLine();
                crnChoice=crnChoice.trim();
                try{
                    for (Course registered: schedule){
                        if (registered.crn.equals(crnChoice)){
                            schedule.remove(registered);
                            System.out.println("Course successfully removed");
                        }
                    }    
                }
                catch(java.util.ConcurrentModificationException c){}
            }
            else if(choice.equals("4")){
                for (Course registered:schedule){
                    System.out.println(registered.toString());
                }
            }
            else if (choice.equals("5")){
                exit=false;
            }
            else{
                System.out.println("Please Select a number from 1 to 5.\n");
            }
        }
    }    
}
