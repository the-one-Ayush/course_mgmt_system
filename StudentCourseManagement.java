import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class SimpleStudent {
    int id;
    String name;
    ArrayList<String> courses = new ArrayList<>();

    SimpleStudent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return id + " - " + name + " | Courses: " + courses;
    }
}

public class SimpleStudentCourse extends JFrame implements ActionListener {
    private HashMap<Integer, SimpleStudent> students = new HashMap<>();
    private ArrayList<String> courses = new ArrayList<>();

    private JTextField txtStudentId = new JTextField(5);
    private JTextField txtStudentName = new JTextField(10);
    private JTextField txtCourseName = new JTextField(10);

    private JList<String> studentList = new JList<>();
    private JList<String> courseList = new JList<>();
    private JTextArea displayArea = new JTextArea(10, 40);

    public SimpleStudentCourse() {
        setTitle("Simple Student-Course Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        JPanel addStudentPanel = new JPanel();
        addStudentPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));
        JButton btnAddStudent = new JButton("Add Student");
        btnAddStudent.addActionListener(this);
        addStudentPanel.add(new JLabel("ID:"));
        addStudentPanel.add(txtStudentId);
        addStudentPanel.add(new JLabel("Name:"));
        addStudentPanel.add(txtStudentName);
        addStudentPanel.add(btnAddStudent);

        JPanel addCoursePanel = new JPanel();
        addCoursePanel.setBorder(BorderFactory.createTitledBorder("Add Course"));
        JButton btnAddCourse = new JButton("Add Course");
        btnAddCourse.addActionListener(this);
        addCoursePanel.add(new JLabel("Name:"));
        addCoursePanel.add(txtCourseName);
        addCoursePanel.add(btnAddCourse);

        topPanel.add(addStudentPanel);
        topPanel.add(addCoursePanel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));

        studentList.setBorder(BorderFactory.createTitledBorder("Students"));
        courseList.setBorder(BorderFactory.createTitledBorder("Courses"));

        centerPanel.add(new JScrollPane(studentList));
        centerPanel.add(new JScrollPane(courseList));

        JButton btnEnroll = new JButton("Enroll Selected");
        btnEnroll.addActionListener(this);
        centerPanel.add(btnEnroll);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        displayArea.setEditable(false);
        bottomPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JButton btnShowAll = new JButton("Show All Students");
        btnShowAll.addActionListener(this);
        bottomPanel.add(btnShowAll, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "Add Student": addStudent(); break;
            case "Add Course": addCourse(); break;
            case "Enroll Selected": enrollStudent(); break;
            case "Show All Students": showAll(); break;
        }
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(txtStudentId.getText().trim());
            String name = txtStudentName.getText().trim();
            if (name.isEmpty()) return;
            students.put(id, new SimpleStudent(id, name));
            refreshLists();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid student ID!");
        }
    }

    private void addCourse() {
        String name = txtCourseName.getText().trim();
        if (!name.isEmpty()) {
            courses.add(name);
            refreshLists();
        }
    }

    private void enrollStudent() {
        int studentIndex = studentList.getSelectedIndex();
        int courseIndex = courseList.getSelectedIndex();
        if (studentIndex >= 0 && courseIndex >= 0) {
            int studentId = new ArrayList<>(students.keySet()).get(studentIndex);
            SimpleStudent s = students.get(studentId);
            String courseName = courses.get(courseIndex);
            if (!s.courses.contains(courseName)) s.courses.add(courseName);
        }
    }

    private void showAll() {
        StringBuilder sb = new StringBuilder();
        for (SimpleStudent s : students.values()) sb.append(s).append("\n");
        displayArea.setText(sb.toString());
    }

    private void refreshLists() {
        studentList.setListData(students.values().stream().map(s -> s.id + " - " + s.name).toArray(String[]::new));
        courseList.setListData(courses.toArray(new String[0]));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleStudentCourse::new);
    }
}
