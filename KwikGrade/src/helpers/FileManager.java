package helpers;

import models.Course;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    /**
     * Saves all courses and their state to a local file.
     * @param courseList
     */
    public static void saveFile(ArrayList<Course> courseList, String saveFileName) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        try {
            fileOutputStream = new FileOutputStream(saveFileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Write Course list to file.
            objectOutputStream.writeObject(courseList);
            objectOutputStream.close();

            System.out.println("Successfully saved data.");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Load courses from saved local file.
     * @param saveFileName
     * @return ArrayList of Courses from saved file
     */
    public static ArrayList<Course> loadFile(String saveFileName) {
        ArrayList<Course> savedCoursesList = new ArrayList<>();
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        try {
            fileInputStream = new FileInputStream(saveFileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            savedCoursesList = (ArrayList<Course>) objectInputStream.readObject();
            objectInputStream.close();

            System.out.println("Successfully loaded save data.");
            return savedCoursesList;
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("File not found: " + fnfe.getMessage());
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        return savedCoursesList;
    }

}
