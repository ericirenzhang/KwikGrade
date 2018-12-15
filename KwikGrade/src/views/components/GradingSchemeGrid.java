package views.components;

import models.CourseCategory;
import models.SubCategory;
import models.OverallGrade;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GradingSchemeGrid {
    public enum GradingSchemeType {
        ADD_STUDENT, MANAGE_STUDENT, MANAGE_CATEGORIES;
    }

    private static int GRADING_SCHEME_WIDTH = 900;
    private int GRADING_SCHEME_HEIGHT;
    private static Color LIGHT_GRAY_COLOR = new Color(0xF0F0F0);
    private static Color DARK_GRAY_COLOR = new Color(0xE0E0E0);
    private static Color BORDER_COLOR = new Color(0xD1D0D1);
    private static Color LIGHT_GREEN_COLOR = new Color(0x97FFBF);
    private static Color LIGHT_BLUE_COLOR = new Color(0x0C97F5);


    private static int GRADING_SCHEME_COL_OFFSET = 2;

    // First two columns of the grading scheme grid are just header/titles.
    private static int gradingSchemeRowCount;
    private static String[] firstColumnText;
    private static String[] secondColumnText;
    private boolean hasFinishedRendering;

    private OverallGrade initialGradeScheme, modifiedGradeScheme;
    private GradingSchemeType gradingSchemeType;
    private List<List<JPanel>> schemeGrid = new ArrayList<List<JPanel>>();
    private HashMap<JPanel, CourseCategory> courseCategoryColMapping = new HashMap<>();
    private HashMap<JPanel, SubCategory> subCategoryColMapping = new HashMap<>();
    JPanel parentPanel = new JPanel();

    public GradingSchemeGrid(OverallGrade modifiedGradeScheme) {
        this.initialGradeScheme = modifiedGradeScheme;
        this.modifiedGradeScheme = modifiedGradeScheme;
    }

    public void configureGradingSchemeGrid(GradingSchemeType gradingSchemeType) {
        this.gradingSchemeType = gradingSchemeType;
        String finalGrade = String.format("%.2f%%", this.initialGradeScheme.getOverallGrade());

        switch(gradingSchemeType) {
            case ADD_STUDENT:
                gradingSchemeRowCount = 5;
                firstColumnText = new String[]{"", "", "Final Raw Score", "Points Gained on Item", "Total Points on Item"};
                secondColumnText = new String[]{"Final Grade", "", finalGrade, "", ""};
                GRADING_SCHEME_HEIGHT = 300;
                break;
            case MANAGE_STUDENT:
                gradingSchemeRowCount = 5;
                firstColumnText = new String[]{"", "", "Final Raw Score", "Points Gained on Item", "Total Points on Item"};
                secondColumnText = new String[]{"Final Grade", "", finalGrade, "", ""};
                GRADING_SCHEME_HEIGHT = 300;
                break;
            case MANAGE_CATEGORIES:
                gradingSchemeRowCount = 2;
                firstColumnText = new String[]{"", ""};
                secondColumnText = new String[]{"", ""};
                // cut the grading scheme grid in half to even out the heights of JPanels
                GRADING_SCHEME_HEIGHT = 150;
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
        this.hasFinishedRendering = false;
        parentPanel.setBackground(Color.WHITE);

        // Append initial header/title columns.
        appendTextColumn(firstColumnText, 12);
        appendTextColumn(secondColumnText, 16);

        // Define GridLayout.
        parentPanel.setLayout(new GridLayout(schemeGrid.size(), schemeGrid.get(0).size()));
        parentPanel.setBounds(75, 200, GRADING_SCHEME_WIDTH, GRADING_SCHEME_HEIGHT);

        // The first two columns are static with text. When we build the JPanels in the columns, they need to be offset by these initial columns of static text.
        for(int categoryIndex = 0; categoryIndex < modifiedGradeScheme.getCourseCategoryList().size(); categoryIndex++) {
            CourseCategory currCategory = modifiedGradeScheme.getCourseCategoryList().get(categoryIndex);

            appendCategoryColumn(currCategory);

            for (int subCategoryIndex = 0; subCategoryIndex < currCategory.getSubCategoryList().size(); subCategoryIndex++) {
                SubCategory currSubCategory = currCategory.getSubCategoryList().get(subCategoryIndex);

                appendSubCategoryColumn(currSubCategory);
            }
        }

        this.hasFinishedRendering = true;
        rerenderGradeValues(true);

        // Add everything we just built into a JScrollPane.
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(parentPanel);
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

        for(int rowIndex = 0; rowIndex < gradingSchemeRowCount; rowIndex++) {
            JPanel currPanel = schemeGrid.get(rowIndex).get(lastColumnIndex);

            JTextField currTextField = new JTextField();
            currTextField.getDocument().addDocumentListener(new DocumentListener() {
                 public void changedUpdate(DocumentEvent e) {
                 }
                 public void removeUpdate(DocumentEvent e) {
                     if(!currTextField.getText().equals("")) {
                         rerenderGradeValues(false);
                     }
                 }
                 public void insertUpdate(DocumentEvent e) {
                     if(!currTextField.getText().equals("")) {
                         rerenderGradeValues(false);
                     }
                 }
            });


            switch(rowIndex) {
                case 0:
                    break;
                case 1:
                    currPanel.setLayout(new BorderLayout(0,0));
                    // Set weight label
                    JLabel weightLabel = new JLabel(currSubCategory.getName() + " (Weight)");
                    Font font = weightLabel.getFont();
                    Font boldFont = new Font(font.getFontName(), Font.PLAIN, 12);
                    weightLabel.setFont(boldFont);
                    currPanel.add(weightLabel, BorderLayout.NORTH);

                    // Set weight text field
                    currPanel.add(weightLabel, BorderLayout.NORTH);
                    String subCategoryWeightPercentage = String.format("%.2f", 100 * currSubCategory.getWeight());
                    currTextField.setText(subCategoryWeightPercentage);
                    currTextField.setPreferredSize(new Dimension(100, 30));
                    currPanel.add(currTextField, BorderLayout.WEST);
                    currPanel.add(new JLabel("%"), BorderLayout.CENTER);
                    break;
                case 2:
                    String subCategoryScorePercentage = String.format("%.2f%%", currSubCategory.getRawFinalScore());
                    JLabel subCategoryWeightLabel = new JLabel(subCategoryScorePercentage);
                    font = subCategoryWeightLabel.getFont();
                    boldFont = new Font(font.getFontName(), Font.BOLD, 12);
                    subCategoryWeightLabel.setFont(boldFont);
                    currPanel.add(subCategoryWeightLabel);
                    break;
                case 3:
                    String pointsGained = String.format("%.2f", currSubCategory.getPointsGained());
                    currPanel.setLayout(new BorderLayout(0,0));
                    currTextField.setText(pointsGained);
                    currTextField.setPreferredSize(new Dimension(100, 30));
                    currPanel.add(currTextField, BorderLayout.WEST);
                    currPanel.add(new JLabel("pts"), BorderLayout.CENTER);
                    break;
                case 4:
                    String totalPoints = String.format("%.2f", currSubCategory.getTotalPoints());
                    currPanel.setLayout(new BorderLayout(0,0));
                    currTextField.setText(totalPoints);
                    currTextField.setPreferredSize(new Dimension(100, 30));
                    currPanel.add(currTextField, BorderLayout.WEST);
                    currPanel.add(new JLabel("pts"), BorderLayout.CENTER);
                    break;
                default:
                    break;
            }
        }

        JPanel firstRowPanel = schemeGrid.get(0).get(lastColumnIndex);
        subCategoryColMapping.put(firstRowPanel, currSubCategory);
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

        for(int rowIndex = 0; rowIndex < gradingSchemeRowCount; rowIndex++) {
            JPanel currPanel = schemeGrid.get(rowIndex).get(lastColumnIndex);

            JTextField currTextField = new JTextField();
            currTextField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                }
                public void removeUpdate(DocumentEvent e) {
                    if(!currTextField.getText().equals("")) {
                        rerenderGradeValues(false);
                    }
                }
                public void insertUpdate(DocumentEvent e) {
                    if(!currTextField.getText().equals("")) {
                        rerenderGradeValues(false);
                    }                }
            });

            switch(rowIndex) {
                case 0:
                    String categoryWeightPercentage = String.format("%.2f", 100 * currCategory.getWeight());
                    currPanel.setLayout(new BorderLayout(0,0));

                    // Set weight label
                    JLabel weightLabel = new JLabel(currCategory.getName() + " (Weight)");
                    Font font = weightLabel.getFont();
                    Font boldFont = new Font(font.getFontName(), Font.BOLD, 14);
                    weightLabel.setFont(boldFont);
                    currPanel.add(weightLabel, BorderLayout.NORTH);

                    // set weight text field
                    currTextField.setText(categoryWeightPercentage);
                    currTextField.setPreferredSize(new Dimension(100, 30));
                    currPanel.add(currTextField, BorderLayout.WEST);
                    currPanel.add(new JLabel("%"), BorderLayout.CENTER);
                case 1:
                    break;
                case 2:
                    String finalScore = String.format("%.2f%%", currCategory.getCategoryFinalWeightedScore());
                    JLabel categoryWeightLabel = new JLabel(finalScore);
                    font = categoryWeightLabel.getFont();
                    boldFont = new Font(font.getFontName(), Font.BOLD, 14);
                    categoryWeightLabel.setFont(boldFont);
                    currPanel.add(categoryWeightLabel);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }

        JPanel firstRowPanel = schemeGrid.get(0).get(lastColumnIndex);
        courseCategoryColMapping.put(firstRowPanel, currCategory);
    }

    /**
     * Given rowTexts of text we want to put at each row, generates a new column filled with text at each row.
     * @param rowTexts
     */
    public void appendTextColumn(String[] rowTexts, int fontSize) {
        int lastColumnIndex = buildBlankColumn();

        // Style and add the text for each JPanel.
        for(int i = 0; i < gradingSchemeRowCount; i++) {
            JPanel currPanel = schemeGrid.get(i).get(lastColumnIndex);

            String currText = rowTexts[i];
            JLabel textLabel = new JLabel(currText);
            Font font = textLabel.getFont();
            Font boldFont = new Font(font.getFontName(), Font.BOLD, fontSize);
            textLabel.setFont(boldFont);
            currPanel.add(textLabel);
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
                } else if (i == 2) {
                    currPanel.setBackground(Color.WHITE);
                } else if (i == 3) {
                    currPanel.setBackground(LIGHT_GREEN_COLOR);
                } else {
                    currPanel.setBackground(Color.WHITE);
                }

                currPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
                parentPanel.add(currPanel);
            }
        }

        return schemeGrid.get(0).size()-1;
    }

    /**
     * Iterates through all cells in this custom grid component and creates an OverallGrade object of all the fields.
     * @return
     */
    public OverallGrade getOverallGradeFromFields() {
        OverallGrade overallGradeFromFields = new OverallGrade();

        // for each Category column (will need some index offset jumping)
        for (int columnIndex = 0; columnIndex < schemeGrid.get(0).size(); columnIndex++) {
            JPanel currPanel = schemeGrid.get(0).get(columnIndex);

            // If it's a Course Category column
            if (courseCategoryColMapping.containsKey(currPanel)) {
                // Create CourseCategory object of weight from textfield and getName
                CourseCategory currCategory = courseCategoryColMapping.get(currPanel);

                double categoryWeight = 0;

                // Get TextField with weight.
                for (Component c : currPanel.getComponents()) {
                    if (c instanceof JTextField) {
                        categoryWeight = Double.parseDouble(((JTextField) c).getText());
                    }
                }
                CourseCategory categoryFromFields = new CourseCategory(currCategory.getName(), categoryWeight);

                categoryFromFields.setWeight(categoryWeight/100);

                // for each subcategory within CourseCategory (we can make this assumption since SubCategory cannot be edited from any of these pages)
                for (int subCategoryIndex = 0; subCategoryIndex < currCategory.getSubCategoryList().size(); subCategoryIndex++) {
                    SubCategory currSubCategory = currCategory.getSubCategoryList().get(subCategoryIndex);
                    SubCategory subCategoryFromFields = new SubCategory(currSubCategory.getName());

                    // for each row
                    for (int rowIndex = 0; rowIndex < gradingSchemeRowCount; rowIndex++) {
                        JPanel rowPanel = schemeGrid.get(rowIndex).get(columnIndex+subCategoryIndex+1);
                        // Get TextField with value.
                        for (Component c : rowPanel.getComponents()) {
                            if (c instanceof JTextField) {
                                double subCategoryField = Double.parseDouble(((JTextField) c).getText());

                                switch (rowIndex) {
                                    case 0:
                                        break;
                                    case 1:
                                        // Row 1 is the weight.
                                        subCategoryFromFields.setWeight(subCategoryField/100);
                                        break;
                                    case 2:
                                        // Row 2 is the raw final score but this is not a JTextField.
                                        break;
                                    case 3:
                                        subCategoryFromFields.setPointsGained(subCategoryField);
                                        break;
                                    case 4:
                                        subCategoryFromFields.setTotalPoints(subCategoryField);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }

                    categoryFromFields.addSubCategory(subCategoryFromFields);
                }
                overallGradeFromFields.addCourseCategory(categoryFromFields);
            }
        }
        return overallGradeFromFields;
    }

    /**
     * Helper function to help a matching CourseCategory from this.modifiedGradeScheme.
     *
     * Used in the context of rerendering:
     *  When we rerender, we go through each column and the CourseCategory and rerender it with this.modifiedGradeScheme's CourseCategory
     *
     * @param courseCategory
     * @return
     */
    public CourseCategory findMatchingCategory(CourseCategory courseCategory) {
        for(int i = 0 ; i < this.modifiedGradeScheme.getCourseCategoryList().size(); i++) {
            if(courseCategory.getName() == this.modifiedGradeScheme.getCourseCategoryList().get(i).getName()) {
                return this.modifiedGradeScheme.getCourseCategoryList().get(i);
            }
        }
        return null;
    }

    /**
     * When the user updates any of the JTextFields, we need to update all the appropriate JLabels.
     */
    public void rerenderGradeValues(boolean isInitialRender) {
        // TODO: refactor this boolean, hacky fix for now in order to use a DocumentListener
        if(!this.hasFinishedRendering) {
            return;
        }
        // TODO: refactor this boolean, hacky fix for now in order to enable proper display of curving
        if(!isInitialRender) {
            this.modifiedGradeScheme = getOverallGradeFromFields();
        }

        JPanel currPanel;

        // TODO: maybe add a more elegant check to this.
        // AddStudentFrame/ManageStudentFrame have a Final Grade Score JLabel. If it's ManageCategoriesFrame, don't try to modify the JLabel because it'll go out of bounds.
        if(this.gradingSchemeType != GradingSchemeType.MANAGE_CATEGORIES) {
            currPanel = schemeGrid.get(2).get(1);
            for (Component c : currPanel.getComponents()) {
                if (c instanceof JLabel) {
                    // Row 2 is the category final score, update it
                    String courseFinalScore = String.format("%.2f%%", this.modifiedGradeScheme.getOverallGrade());
                    ((JLabel) c).setText(courseFinalScore);
                }
            }
        }

        // Update all of the JLabels
        for (int columnIndex = 0; columnIndex < schemeGrid.get(0).size(); columnIndex++) {
            currPanel = schemeGrid.get(0).get(columnIndex);

            // If it's a Course Category column
            if (courseCategoryColMapping.containsKey(currPanel)) {
                // Create CourseCategory object of weight from textfield and getName
                CourseCategory currCategory = courseCategoryColMapping.get(currPanel);

                CourseCategory categoryToUpdateWith = findMatchingCategory(currCategory);

                // for each row
                for (int rowIndex = 0; rowIndex < gradingSchemeRowCount; rowIndex++) {
                    JPanel rowPanel = schemeGrid.get(rowIndex).get(columnIndex);
                    // Get TextField with value.
                    for (Component c : rowPanel.getComponents()) {
                        if (c instanceof JLabel) {
                            if(rowIndex == 2) {
                                // Row 2 is the category final score, update it
                                String categoryFinalWeightedScore = String.format("%.2f%%", categoryToUpdateWith.getCategoryFinalWeightedScore());
                                ((JLabel) c).setText(categoryFinalWeightedScore);
                            }
                        }
                    }
                }
                courseCategoryColMapping.put(currPanel, categoryToUpdateWith);

                // for each subcategory within CourseCategory (we can make this assumption since SubCategory cannot be edited from any of these pages)
                for (int subCategoryIndex = 0; subCategoryIndex < categoryToUpdateWith.getSubCategoryList().size(); subCategoryIndex++) {
                    SubCategory currSubCategory = categoryToUpdateWith.getSubCategoryList().get(subCategoryIndex);

                    // for each row
                    for (int rowIndex = 0; rowIndex < gradingSchemeRowCount; rowIndex++) {
                        JPanel rowPanel = schemeGrid.get(rowIndex).get(columnIndex+subCategoryIndex+1);
                        // Get JLabel with value.
                        for (Component c : rowPanel.getComponents()) {
                            if (c instanceof JLabel) {
                                if(rowIndex == 2) {
                                    // Row 2 is the category final score, update it
                                    String subCategoryFinalWeightedScore = String.format("%.2f%%", currSubCategory.getRawFinalScore());
                                    ((JLabel) c).setText(subCategoryFinalWeightedScore);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
