package helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.GraduateStudent;
import models.OverallGrade;
import models.Student;
import models.UndergraduateStudent;

import javax.swing.*;

public class StudentTextImport {
	/**
	 * Imports students by parsing a text file into this.importedStudentList.
	 * @param filePath
	 */
	public static ArrayList<Student> addImportedStudents(String filePath, OverallGrade ugOverallGrade, OverallGrade gradOverallGrade) {
		ArrayList<Student> importedStudentList = new ArrayList<Student>();
		Scanner rawStudentData;

		try {
			rawStudentData = new Scanner(new File(filePath));

			// Read line by line from the file
			while(rawStudentData.hasNext()) {
				String line = rawStudentData.nextLine();
				List<String> splitLine = Arrays.asList(line.split(","));

				// Checks for middle initial, if there's middle initial, there will be 6 items in string
				if(splitLine.size() == 6) {
					String fName = splitLine.get(0);
					String middleInitial = splitLine.get(1);
					String lName = splitLine.get(2);
					String buId = splitLine.get(3);
					String email = splitLine.get(4);
					String standing = splitLine.get(5);
					if(standing.equals("Undergraduate")) {
						importedStudentList.add(new UndergraduateStudent(fName, middleInitial, lName, buId, email, "Undergraduate", ugOverallGrade));
					} else if (standing.equals("Graduate")) {
						importedStudentList.add(new GraduateStudent(fName, middleInitial, lName, buId, email, "Graduate", gradOverallGrade));
 					} else {
						importedStudentList.add(new Student(fName, middleInitial, lName, buId, email));
					}
				}
				//if no middle initial, then 5 items in string
				else {
					String fName = splitLine.get(0);
					String middleInitial = "";
					String lName = splitLine.get(1);
					String buId = splitLine.get(2);
					String email = splitLine.get(3);
					String standing = splitLine.get(4);
					if(standing.equals("Undergraduate")) {
						importedStudentList.add(new UndergraduateStudent(fName, middleInitial, lName, buId, email, "Undergraduate", ugOverallGrade));
					} else if (standing.equals("Graduate")) {
						importedStudentList.add(new GraduateStudent(fName, middleInitial, lName, buId, email, "Graduate", gradOverallGrade));
					} else {
						importedStudentList.add(new Student(fName, middleInitial, lName, buId, email));
					}
				}
			}

			rawStudentData.close();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Could not find Student file. Please try again.");
		}
		
		return importedStudentList;
	}

}
