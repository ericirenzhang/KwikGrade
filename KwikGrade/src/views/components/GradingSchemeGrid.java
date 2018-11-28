package views.components;

import models.CourseCategory;
import models.OverallGrade;
import models.SubCategory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GradingSchemeGrid {
    public enum GradingSchemeType {
        ADD_STUDENT, MANAGE_STUDENT, MANAGE_CATEGORIES;
    }

    private static int GRADING_SCHEME_ROW_COUNT = 5;
    private static int GRADING_SCHEME_WIDTH = 900;
    private static int GRADING_SCHEME_HEIGHT = 300;
    private static Color LIGHT_GRAY_COLOR = new Color(0xF0F0F0);
    private static Color DARK_GRAY_COLOR = new Color(0xE0E0E0);
    private static Color LIGHT_GREEN_COLOR = new Color(0x97FFBF);

    private static int GRADING_SCHEME_COL_OFFSET = 2;

    private OverallGrade managedGradeScheme;

    public GradingSchemeGrid(OverallGrade managedGradeScheme) {
        this.managedGradeScheme = managedGradeScheme;
    }

    public void configureGradingSchemeGrid(GradingSchemeType gradingSchemeType) {
        switch(gradingSchemeType) {
            case ADD_STUDENT:
                return;
            case MANAGE_STUDENT:
                return;
            case MANAGE_CATEGORIES:
                return;
            default:
                return;
        }
    }

    public JPanel buildGradingSchemeGrid() {
        JPanel parentPanel = new JPanel();
        parentPanel.setBackground(Color.WHITE);

        // Build a 2D arraylist of our scheme grid
        List<List<JPanel>> schemeGrid = new ArrayList<List<JPanel>>();
        
        // Define GridLayout.
        parentPanel.setLayout(new GridLayout(GRADING_SCHEME_ROW_COUNT, numCols));
        parentPanel.setBounds(75, 200, GRADING_SCHEME_WIDTH, GRADING_SCHEME_HEIGHT);

        // Create grid of JPanels
        JPanel[][] schemeGrid = new JPanel[GRADING_SCHEME_ROW_COUNT][numCols];
        for(int i = 0; i < GRADING_SCHEME_ROW_COUNT; i++) {
            for(int j = 0; j < numCols; j++) {
                schemeGrid[i][j] = new javax.swing.JPanel();
                javax.swing.JPanel currPanel = schemeGrid[i][j];
                currPanel.setPreferredSize(new Dimension(150, 50));

                // Styling to make the table look like the mockup.
                if(i == 0) {
                    currPanel.setBackground(LIGHT_GRAY_COLOR);
                } else if (i == 1) {
                    currPanel.setBackground(DARK_GRAY_COLOR);
                } else if (i == 3){
                    currPanel.setBackground(LIGHT_GREEN_COLOR);
                } else {
                    currPanel.setBackground(Color.WHITE);
                }

                // Only add borders on vertical sides.
                currPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(0xD1D0D1)));

                parentPanel.add(currPanel);
            }
        }

        // Set up static cells since these will be hardcoded.
        schemeGrid[0][1].add(new JLabel("Final Grade"));
        String studentGradePercentage = String.format("%.2f%%%n", 100 * managedStudent.getOverallGrade().getOverallGrade());
        schemeGrid[2][1].add(new JLabel(studentGradePercentage));
        schemeGrid[2][0].add(new JLabel("Total Percentage"));

        // TODO: once we add in the toggle for points gained and points subtracted, this string will have to change dynamically
        schemeGrid[3][0].add(new JLabel("Points Gained"));
        schemeGrid[4][0].add(new JLabel("Total Points"));

        // The first two columns are static with text. When we build the JPanels in the columns, they need to be offset by these initial columns with static text.
        int colOffset = 2;
        for(int i = 0; i < managedGradeScheme.getCourseCategoryList().size(); i++) {
            CourseCategory currCategory = managedGradeScheme.getCourseCategoryList().get(i);
            String categoryWeightPercentage = String.format("%.2f%%%n", 100 * currCategory.getWeight());

            schemeGrid[0][i+colOffset].setLayout(new BoxLayout(schemeGrid[0][i+colOffset], BoxLayout.Y_AXIS));
            schemeGrid[0][i+colOffset].add(new JLabel(currCategory.getName() + " (Weight)"));
            schemeGrid[0][i+colOffset].add(new JTextField(categoryWeightPercentage));

            for (int j = 0; j < currCategory.getSubCategoryList().size(); j++) {
                SubCategory currSubCategory = currCategory.getSubCategoryList().get(j);
                String subCategoryWeightPercentage = String.format("%.2f%%%n", 100 * currSubCategory.getWeight());

                // Show name and weight percentage
                schemeGrid[1][j+i+colOffset+1].setLayout(new BoxLayout(schemeGrid[1][j+i+colOffset+1], BoxLayout.Y_AXIS));
                schemeGrid[1][j+i+colOffset+1].add(new JLabel(currSubCategory.getName() + " (Weight)"));
                schemeGrid[1][j+i+colOffset+1].add(new JTextField(subCategoryWeightPercentage));

                // Show total percentage, points gained and total points.
                String subCategoryNonWeightedPercentage = String.format("%.2f%%%n", 100 * currSubCategory.getNonWeightedValue());
                schemeGrid[2][j+i+colOffset+1].add(new JLabel(subCategoryNonWeightedPercentage));
                schemeGrid[3][j+i+colOffset+1].add(new JTextField(Double.toString(currSubCategory.getPointsGained())));
                schemeGrid[4][j+i+colOffset+1].add(new JTextField(Double.toString(currSubCategory.getTotalPoints())));

            }
            colOffset += currCategory.getSubCategoryList().size();
        }

        // Add everything we just built into a JScrollPane.
//        JScrollPane scrollPane = new JScrollPane(parentPanel);
//        scrollPane.setBounds(75, 200, GRADING_SCHEME_WIDTH, GRADING_SCHEME_HEIGHT);

        return parentPanel;
    }



}
