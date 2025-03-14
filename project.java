import java.util.ArrayList;
import java.util.Scanner;

class Task {
    String description;
    boolean isCompleted;

    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[Completed] " : "[Pending] ") + description;
    }
}
public class project {
public class ToDoListApp {
    static ArrayList<Task> tasks = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Task\n2. View Tasks\n3. Mark Task as Completed\n4. Delete Task\n5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    markTaskCompleted();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void addTask() {
        System.out.print("Enter task description: ");
        String desc = scanner.nextLine().trim();
        if (desc.isEmpty()) {
            System.out.println("Task description cannot be empty!");
            return;
        }
        tasks.add(new Task(desc));
        System.out.println("Task added successfully!");
    }

    public static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public static void markTaskCompleted() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        viewTasks();
        System.out.print("Enter task number to mark as completed: ");
        try {
            int taskIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                tasks.get(taskIndex).markCompleted();
                System.out.println("Task marked as completed!");
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine();
        }
    }

    public static void deleteTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        viewTasks();
        System.out.print("Enter task number to delete: ");
        try {
            int taskIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                tasks.remove(taskIndex);
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine();
        }
    }
}
}