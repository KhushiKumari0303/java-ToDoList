import javax.swing.*; //Used for creating Graphical User Interface
import java.awt.*; // UI components like layouts and colors.
import java.awt.event.*; //Enables handling user interactions
import java.io.*;//Allows reading and writing to files
import java.util.ArrayList;//Used to store a list of tasks dynamically.
//Task class stores individual task details.
class Task {
    String description;
    String deadline;
    String priority;
    String category;
    boolean isCompleted;
//Constructor for Task Class
    public Task(String description, String deadline, String priority, String category, boolean isCompleted) {
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.isCompleted = isCompleted;
    }
    //Marking Task as Completed
    public void markCompleted() {
        this.isCompleted = true;
    }
//Formatting Task Display
    @Override
    public String toString() {
        return (isCompleted ? "[✔] " : "[ ] ") + description + " | " + priority + " | " + category + " | Due: " + deadline;
    }
//Formatting Task Data for File Storage
    public String toFileFormat() {
        return description + "," + deadline + "," + priority + "," + category + "," + isCompleted;
    }
//Reading Task Data from File
    public static Task fromFileFormat(String line) {
        String[] parts = line.split(",");
        return new Task(parts[0], parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]));
    }
}
//This main GUI class handles the graphical interface and task management logic.
public class ToDoListGUI {
    private JFrame frame;
    private DefaultListModel<Task> taskModel;
    private JList<Task> taskList;
    private JTextField taskInput, deadlineInput;
    private JComboBox<String> priorityDropdown, categoryDropdown;
    private JButton addButton, deleteButton, markButton;
    private ArrayList<Task> tasks;
    private static final String FILE_NAME = "tasks.txt";

    public ToDoListGUI() {
        frame = new JFrame("To-Do List");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        tasks = new ArrayList<>();
        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);

        taskInput = new JTextField(15);
        deadlineInput = new JTextField(10);
        priorityDropdown = new JComboBox<>(new String[]{"High", "Medium", "Low"});
        categoryDropdown = new JComboBox<>(new String[]{"Work", "Personal", "Other"});

        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Task");
        markButton = new JButton("Mark as Completed");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskInput);
        inputPanel.add(new JLabel("Deadline:"));
        inputPanel.add(deadlineInput);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityDropdown);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryDropdown);
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(markButton);
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        markButton.addActionListener(e -> markTaskCompleted());

        loadTasksFromFile();
        frame.setVisible(true);
    }

    private void addTask() {
        String desc = taskInput.getText();
        String deadline = deadlineInput.getText();
        String priority = (String) priorityDropdown.getSelectedItem();
        String category = (String) categoryDropdown.getSelectedItem();

        if (!desc.isEmpty() && !deadline.isEmpty()) {
            Task task = new Task(desc, deadline, priority, category, false);
            tasks.add(task);
            taskModel.addElement(task);
            saveTasksToFile();
            taskInput.setText("");
            deadlineInput.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter task and deadline.");
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            taskModel.remove(selectedIndex);
            saveTasksToFile();
        }
    }

    private void markTaskCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task task = tasks.get(selectedIndex);
            task.markCompleted();
            taskModel.set(selectedIndex, task);
            saveTasksToFile();
        }
    }

    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromFileFormat(line);
                tasks.add(task);
                taskModel.addElement(task);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error loading tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListGUI::new);
    }
}