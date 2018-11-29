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

    private static int GRADING_SCHEME_WIDTH = 900;
    private static int GRADING_SCHEME_HEIGHT = 300;
    private static Color LIGHT_GRAY_COLOR = new Color(0xF0F0F0);
    private static Color DARK_GRAY_COLOR = new Color(0xE0E0E0);
    private static Color BORDER_COLOR = new Color(0xD1D0D1);
    private static Color LIGHT_GREEN_COLOR = new Color(0x97FFBF);

    private static int GRADING_SCHEME_COL_OFFSET = 2;

    // First two columns of the grading scheme grid are just header/titles.
    private static int gradingSchemeRowCount;
    private static String[] firstColumnText;
    private static String[] secondColumnText;

    private OverallGrade managedGradeScheme;
    private List<List<JPanel>> schemeGrid = new ArrayList<List<JPanel>>();
    JPanel parentPanel = new JPanel();

    public GradingSchemeGrid(OverallGrade managedGradeScheme) {
        this.managedGradeScheme = managedGradeScheme;
    }

    public void configureGradingSchemeGrid(GradingSchemeType gradingSchemeType) {
        switch(gradingSchemeType) {
            case ADD_STUDENT:
                gradingSchemeRowCount = 3;
                firstColumnText = new String[]{"", "", "Total Percentage"};
                secondColumnText = new String[]{"Final Grade", "", ""};
                break;
            case MANAGE_STUDENT:
                gradingSchemeRowCount = 5;
                firstColumnText = new String[]{"", "", "Total Percentage", "Points Gained", "Total Points"};
                secondColumnText = new String[]{"Final Grade", "", "", "", ""};
                break;
            case MANAGE_CATEGORIES:
                gradingSchemeRowCount = 2;
                firstColumnText = new String[]{"", ""};
                secondColumnText = new String[]{"Final Grade", ""};
                break;
            default:
                break;
        }

        for(int i = 0; i < gradingSchemeRowCount; i++) {
            schemeGrid.add(new ArrayList<JPanel>());
        }
    }

    /**
     * Generates the grading scheme table, which is built using GridLayout.
     *
     * We build this using a nested structure as follows:
     * - JScrollPane that we return
     * 	- Parent JPanel that uses a GridLayout and holds...
     * 		- 2D ArrayList of JPanels, where each JPanel represents a "cell" in the table
     * 			- Each array cell can then contain JLabels, JLabels + JTextField, etc
     *
     * @return JScrollPane containing the entire grading scheme table
     */
    public JScrollPane buildGradingSchemeGrid() {
        parentPanel.setBackground(Color.WHITE);

        // Append initial header/title columns.
        appendTextColumn(firstColumnText);
        appendTextColumn(secondColumnText);

        // Define GridLayout.
        parentPanel.setLayout(new GridLayout(schemeGrid.size(), schemeGrid.get(0).size()));
        parentPanel.setBounds(75, 200, GRADING_SCHEME_WIDTH, GRADING_SCHEME_HEIGHT);

        // The first two columns are static with text. When we build the JPanels in the columns, they need to be offset by these initial columns of static text.
        for(int categoryIndex = 0; categoryIndex < managedGradeScheme.getCourseCategoryList().size(); categoryIndex++) {
            CourseCategory currCategory = managedGradeScheme.getCourseCategoryList().get(categoryIndex);

            appendCategoryColumn(currCategory);

            for (int subCategoryIndex = 0; subCategoryIndex < currCategory.getSubCategoryList().size(); subCategoryIndex++) {
                SubCategory currSubCategory = currCategory.getSubCategoryList().get(subCategoryIndex);

                appendSubCategoryColumn(currSubCategory);
            }
        }

        // Add everything we just built into a JScrollPane.
        JScrollPane scrollPane = new JScrollPane(parentPanel);
        scrollPane.setBounds(75, 200, GRADING_SCHEME_WIDTH, GRADING_SCHEME_HEIGHT);

        return scrollPane;
    }

    /**
     * Given a SubCategory, generates a new column with SubCategory information.
     * Based on the grading scheme type we are serving this page from, we will add panels accordingly.
     * i.e. Add Student Page won't show the Points Gained/Total Points row but Manage Student Page will.
     *
     * @param currSubCategory
     */
    public void appendSubCategoryColumn(SubCategory currSubCategory) {
        int lastColumnIndex = buildBlankColumn();

        String subCategoryWeightPercentage = String.format("%.2f%%%n", 100 * currSubCategory.getWeight());
        String subCategoryNonWeightedPercentage = String.format("%.2f%%%n", 100 * currSubCategory.getNonWeightedValue());

        for(int rowIndex = 0; rowIndex < gradingSchemeRowCount; rowIndex++) {
            JPanel currPanel = schemeGrid.get(rowIndex).get(lastColumnIndex);

            switch(rowIndex) {
                case 0:
                    break;
                case 1:
                    // Show name and weight percentage
                    currPanel.setLayout(new BoxLayout(currPanel, BoxLayout.Y_AXIS));
                    currPanel.add(new JLabel(currSubCategory.getName() + " (Weight)"));
                    currPanel.add(new JTextField(subCategoryWeightPercentage));
                    break;
                case 2:
                    currPanel.add(new JLabel(subCategoryNonWeightedPercentage));
                    break;
                case 3:
                    currPanel.add(new JTextField(Double.toString(currSubCategory.getPointsGained())));
                    break;
                case 4:
                    currPanel.add(new JTextField(Double.toString(currSubCategory.getTotalPoints())));
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * Given a CourseCategory, generates a new column with CourseCategory information.
     * Based on our mockups, this is primarily just the first row, but can be extended to fill in other rows as well.
     *
     * @param currCategory
     */
    public void appendCategoryColumn(CourseCategory currCategory) {
        // Since we're appending a column, we can get the column index we want to add to by simply getting the last column.
        int lastColumnIndex = buildBlankColumn();

        // Course information always gets displayed in just the first row (row 0).
        int courseRow = 0;

        String categoryWeightPercentage = String.format("%.2f%%%n", 100 * currCategory.getWeight());

        JPanel currPanel = schemeGrid.get(courseRow).get(lastColumnIndex);
        currPanel.setLayout(new BoxLayout(schemeGrid.get(courseRow).get(lastColumnIndex), BoxLayout.Y_AXIS));
        currPanel.add(new JLabel(currCategory.getName() + " (Weight)"));
        currPanel.add(new JTextField(categoryWeightPercentage));
    }

    /**
     * Given rowTexts of text we want to put at each row, generates a new column filled with text at each row.
     * @param rowTexts
     */
    public void appendTextColumn(String[] rowTexts) {
        int lastColumnIndex = buildBlankColumn();

        // Style and add the text for each JPanel.
        for(int i = 0; i < schemeGrid.size(); i++) {
            JPanel currPanel = schemeGrid.get(i).get(lastColumnIndex);

            String currText = rowTexts[i];
            currPanel.add(new JLabel(currText));
        }
    }

    /**
     * Because we're using ArrayList of JPanels to add columns dynamically, we need to add a column of JPanels.
     * This creates a new blank column of schemeGrid, then styled accordingly for background color.
     *
     * @return int that is the last column index (0-indexed).
     */
    public int buildBlankColumn() {
        // First build the number of rows with blank JPanels.
        for(int i = 0; i < gradingSchemeRowCount; i++) {
            JPanel currPanel = new JPanel();
            currPanel.setPreferredSize(new Dimension(150, 50));

            schemeGrid.get(i).add(currPanel);
        }

        // Style the panels (row coloring).
        for(int i = 0; i < schemeGrid.size(); i++) {
            for(int j = 0; j < schemeGrid.get(i).size(); j++) {
                JPanel currPanel = schemeGrid.get(i).get(j);

                // Styling to make the table look like the mockup.
                if(i == 0) {
                    currPanel.setBackground(LIGHT_GRAY_COLOR);
                } else if (i == 1) {
                    currPanel.setBackground(DARK_GRAY_COLOR);
                } else {
                    currPanel.setBackground(Color.WHITE);
                }

                currPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
                parentPanel.add(currPanel);
            }
        }

        return schemeGrid.get(0).size()-1;
    }

    public OverallGrade getOverallGradeFromFields() {
        return new OverallGrade();
    }


}
