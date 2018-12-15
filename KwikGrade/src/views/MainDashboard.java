package views;

import helpers.FileManager;
import helpers.ModelGenerators;
import models.Course;
import models.KwikGrade;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;

public class MainDashboard extends JFrame {
	private static final String SERIALIZED_FILE_NAME_ACTIVE = "serializedActiveCourseSaveData.ser";
	private static final String SERIALIZED_FILE_NAME_CLOSED = "serializedClosedCourseSaveData.ser";

	private DefaultListModel DLM;

	private JPanel contentPane;
	private static KwikGrade kwikGrade;
	private JList activeCourseDisplayList;
	private JList closedCourseDisplayList;

	/**
	 * Create the MainDashboard Frame.
	 */
	public MainDashboard() {
		kwikGrade = new KwikGrade();

		// Load from saved file.
		kwikGrade.setActiveCourses(FileManager.loadFile(SERIALIZED_FILE_NAME_ACTIVE));
		kwikGrade.setClosedCourses(FileManager.loadFile(SERIALIZED_FILE_NAME_CLOSED));

		// Set up JFrame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// =====================
		// Active and closed course tables
		// =====================
		// Adding courses
		JLabel activeCoursesLabel = new JLabel("Active Courses");
		activeCoursesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		activeCoursesLabel.setBounds(12, 0, 128, 42);
		contentPane.add(activeCoursesLabel);

		JScrollPane activeCourseScrollPane = new JScrollPane();
		activeCourseScrollPane.setBounds(12, 41, 456, 229);
		contentPane.add(activeCourseScrollPane);

		// Jlist for dynamic display of courses
		activeCourseDisplayList = new JList();
		activeCourseDisplayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activeCourseDisplayList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				closedCourseDisplayList.clearSelection();
			}
		});
		activeCourseScrollPane.setViewportView(activeCourseDisplayList);
		activeCourseDisplayList.setFont(new Font("Tahoma", Font.PLAIN, 18));

		// Loads courses upon verifyAndOpenDashboard for display in dynamic list
		activeCourseDisplayList.setModel(ModelGenerators.loadCourseList(kwikGrade.getActiveCourses()));
		
		// Closing courses
		JLabel closedCourseLabel = new JLabel("Closed Courses");
		closedCourseLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		closedCourseLabel.setBounds(12, 281, 128, 42);
		contentPane.add(closedCourseLabel);

		JScrollPane closedCourseScrollPane = new JScrollPane();
		closedCourseScrollPane.setBounds(12, 334, 456, 133);
		contentPane.add(closedCourseScrollPane);

		closedCourseDisplayList = new JList();
		closedCourseDisplayList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				activeCourseDisplayList.clearSelection();
			}
		});
		closedCourseScrollPane.setViewportView(closedCourseDisplayList);
		closedCourseDisplayList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		closedCourseDisplayList.setModel(ModelGenerators.loadCourseList(kwikGrade.getClosedCourses()));

		// =====================
		// Buttons
		// =====================
		JButton openCourseButton = new JButton("Open Course");
		openCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int manageIndex;
				Course selectedCourse;
				//determines if a closed course is selected or an active
				if (closedCourseDisplayList.getSelectedIndex() < 0 && activeCourseDisplayList.getSelectedIndex() < 0 ) {
					JOptionPane.showMessageDialog(null, "No course is selected!");
					return;
				}
				else if (closedCourseDisplayList.getSelectedIndex() <= activeCourseDisplayList.getSelectedIndex()) {
					manageIndex = activeCourseDisplayList.getSelectedIndex();
					selectedCourse = kwikGrade.getActiveCourses().get(manageIndex);
					
					try {
						CourseOverviewFrame courseOverview = new CourseOverviewFrame(kwikGrade, selectedCourse);
						courseOverview.setModal(true);
						courseOverview.setVisible(true);
					}
					catch (Exception eManage) {
						JOptionPane.showMessageDialog(null, "No course is selected!");
					}
					
				}
				else {
					manageIndex = closedCourseDisplayList.getSelectedIndex();
					selectedCourse = kwikGrade.getClosedCourses().get(manageIndex);
					
					try {
						ClosedCourseOverviewFrame courseOverview = new ClosedCourseOverviewFrame(kwikGrade, selectedCourse);
						courseOverview.setModal(true);
						courseOverview.setVisible(true);
					}
					catch (Exception eManage) {
						JOptionPane.showMessageDialog(null, "No course is selected!");
					}
					
				}	
			}
		});
		openCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		openCourseButton.setBounds(478, 20, 166, 71);
		contentPane.add(openCourseButton);

		JButton addCourseButton = new JButton("Add Course");
		addCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(kwikGrade.getActiveCourses());

				CreateCourseFrame createCourse = new CreateCourseFrame(kwikGrade);
				createCourse.setModal(true);
				createCourse.setVisible(true);

				// If user clicked "OK", then add new course. Otherwise, the user must have clicked "Cancel".
				if(createCourse.getHasCreatedNewCourse()){
					kwikGrade.addCourse(createCourse.getCourseNum(), createCourse.getCourseTerm(), createCourse.getCourseTitle(), createCourse.getImportedStudentsList(), createCourse.getUGOverallGrade(), createCourse.getGradOverallGrade());

					//saves file upon course creation
					FileManager.saveFile(kwikGrade.getActiveCourses(), SERIALIZED_FILE_NAME_ACTIVE);
					System.out.println(kwikGrade.getActiveCourses());
					updateCourseDisplayModel();
				}
			}
		});
		addCourseButton.setBounds(478, 102, 166, 71);
		contentPane.add(addCourseButton);

		JButton closeCourseButton = new JButton("Close Course");
		closeCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		closeCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int closeIndex = activeCourseDisplayList.getSelectedIndex();
				try {
					kwikGrade.closeCourse(closeIndex);
					FileManager.saveFile(kwikGrade.getActiveCourses(), SERIALIZED_FILE_NAME_ACTIVE);
					FileManager.saveFile(kwikGrade.getClosedCourses(), SERIALIZED_FILE_NAME_CLOSED);
				}
				catch (Exception eClose) {
					//exception for if an invalid course is selected
					JOptionPane.showMessageDialog(null, "No course selected!");
				}
				updateCourseDisplayModel();
			}
		});
		closeCourseButton.setBounds(478, 186, 166, 71);
		contentPane.add(closeCourseButton);

		// Course deletion, can only be done to active courses
		JButton deleteCourseButton = new JButton("Delete Course");
		deleteCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int deleteIndex = activeCourseDisplayList.getSelectedIndex();
				try {
					kwikGrade.deleteCourse(deleteIndex);
				}
				catch (Exception eDelete) {
					JOptionPane.showMessageDialog(null, "No course selected!");
				}
				FileManager.saveFile(kwikGrade.getActiveCourses(), SERIALIZED_FILE_NAME_ACTIVE);
				updateCourseDisplayModel();
			}
		});
		deleteCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		deleteCourseButton.setBounds(478, 268, 166, 71);
		contentPane.add(deleteCourseButton);

		// Course saving
		JButton saveCourseButton = new JButton("Save");
		saveCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileManager.saveFile(kwikGrade.getActiveCourses(), SERIALIZED_FILE_NAME_ACTIVE);
				FileManager.saveFile(kwikGrade.getClosedCourses(), SERIALIZED_FILE_NAME_CLOSED);
				JOptionPane.showMessageDialog(null, "Successfully Saved!");
			}
		});

		saveCourseButton.setBounds(480, 380, 166, 32);
		contentPane.add(saveCourseButton);

		// Loads courses upon verifyAndOpenDashboard with button, for display in dynamic list
		JButton refreshCourseButton = new JButton("Refresh");
		refreshCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateCourseDisplayModel();
			}
		});
		refreshCourseButton.setBounds(480, 425, 166, 32);
		contentPane.add(refreshCourseButton);

		// Double click on a course to open
		activeCourseDisplayList.addMouseListener(new MouseAdapter(){
 		    @Override
 		    public void mouseClicked(MouseEvent e){
 		        if(e.getClickCount()==2){
 		        	openCourseButton.doClick();
 		        }
 		    }
 		});
		
		closedCourseDisplayList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount()==2) {
					openCourseButton.doClick();
				}
			}
		});
	}

	public void updateCourseDisplayModel() {
		activeCourseDisplayList.setModel(ModelGenerators.loadCourseList(kwikGrade.getActiveCourses()));
		closedCourseDisplayList.setModel(ModelGenerators.loadCourseList(kwikGrade.getClosedCourses()));
	}
	
	public static String getActiveSaveFileName() {
		return SERIALIZED_FILE_NAME_ACTIVE;
	}
	
	public static String getClosedSaveFileName() {
		return SERIALIZED_FILE_NAME_CLOSED;
	}
	
	public static KwikGrade getKwikGrade() {
		return kwikGrade;
	}
	
}
