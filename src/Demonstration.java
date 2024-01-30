import model.*;
import manager.Managers;
import manager.TaskManager;
import java.util.Scanner;
import java.util.List;

public class Demonstration {

    Scanner scanner = new Scanner(System.in);
    TaskManager manager = Managers.getDefaultManager();

    public void printMenu() {
        System.out.println("1 - создать");
        System.out.println("2 - вывести список");
        System.out.println("3 - получить по id");
        System.out.println("4 - обновить");
        System.out.println("5 - удалить по id");
        System.out.println("6 - удалить все");
        System.out.println("7 - вывести историю просмотров");
        System.out.println("0 - Выход");
    }

    public void addNew() {
        System.out.println("1 - задача, 2 - эпик, 3 - подзадача");
        String command2 = scanner.next();
        switch (command2) {
            case "1":
                System.out.println("Вводите без ошибок через enter: Имя, описание и статус задачи строкой");
                manager.addNewTask(new Task(scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next())));
                System.out.println("Задача добавлена");
                break;
            case "2":
                System.out.println("Вводите без ошибок через enter: Имя, описание и статус эпика строкой");
                manager.addNewEpic(new Epic(scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next())));
                System.out.println("Эпик добавлен");
                break;
            case "3":
                remindEpics();
                System.out.println("Вводите без ошибок через enter: Имя, описание и статус epicId подзадачи");
                manager.addNewSubtask(new Subtask(scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next()), scanner.nextLong()));
                System.out.println("Подзадача добавлена");
                break;
        }
    }

    public void getList() {
        System.out.println("1 - задач, 2 - эпиков, 3 - подзадач, 4 - подзадач эпика");
        String command2 = scanner.next();
        switch (command2) {
            case "1":
                for (Task task : manager.getTasksList()) {
                    System.out.println(task);
                }
                break;
            case "2":
                for (Epic epic : manager.getEpicsList()) {
                    System.out.println(epic);
                }
                break;
            case "3":
                for (Subtask subtask : manager.getSubtasksList()) {
                    System.out.println(subtask);
                }
                break;
            case "4":
                remindEpics();
                System.out.println("Введите id эпика (в метод передаётся объект)");
                List<Subtask> subtasks = manager.getSubtasksListByEpicId(scanner.nextLong());
                for (Subtask subtask : subtasks) {
                    System.out.println(subtask);
                }
                break;
        }
    }

    public void getById() {
        System.out.println("1 - задачу, 2 - эпик, 3 - подзадачу");
        String command2 = scanner.next();
        switch (command2) {
            case "1":
                remindTasks();
                System.out.println("Введите id:");
                System.out.println(manager.getTaskById(scanner.nextLong()));
                break;
            case "2":
                remindEpics();
                System.out.println("Введите id:");
                System.out.println(manager.getEpicById(scanner.nextLong()));
                break;
            case "3":
                remindSubtasks();
                System.out.println("Введите id:");
                System.out.println(manager.getSubtaskById(scanner.nextLong()));
                break;
        }
    }

    public void update() {
        System.out.println("1 - задачу, 2 - эпик, 3 - подзадачу");
        String command2 = scanner.next();
        switch (command2) {
            case "1": {
                remindTasks();
                System.out.println("сначала выберите id задачи");
                long id = scanner.nextLong();
                System.out.println("Теперь снова введите через enter без ошибок: Имя, описание и статус задачи строкой");
                Task updatedTask = new Task(id, scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next()));
                manager.updateTask(updatedTask);
                System.out.println("Задача обновлена");
                break;
            }
            case "2": {
                remindEpics();
                System.out.println("сначала выберите id эпика");
                long id = scanner.nextLong();
                System.out.println("Теперь снова введите через enter без ошибок: Имя и писание эпика");
                Epic updatedEpic = new Epic(id, scanner.next(), scanner.next(), TaskStatus.NEW);
                manager.updateEpic(updatedEpic);
                System.out.println("Эпик обновлен");
                break;
            }
            case "3": {
                remindSubtasks();
                System.out.println("сначала выберите id подзадачи");
                long id = scanner.nextLong();
                System.out.println("Теперь снова введите через enter без ошибок: Имя, описание и статус подзадачи строкой");
                Subtask updatedSubtask = new Subtask(id, scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next()), 1);
                manager.updateSubtask(updatedSubtask);
                System.out.println("Подзадача обновлена");
                break;
            }
        }
    }

    public void deleteById() {
        System.out.println("1 - задачу, 2 - эпик, 3 - подзадачу");
        String command2 = scanner.next();
        switch (command2) {
            case "1":
                remindTasks();
                System.out.println("Введите id задачи:");
                manager.deleteTaskById(scanner.nextLong());
                System.out.println("Задача удалена");
                break;
            case "2":
                remindEpics();
                System.out.println("Введите id эпика:");
                manager.deleteEpicById(scanner.nextLong());
                System.out.println("Эпик удален");
                break;
            case "3":
                remindSubtasks();
                System.out.println("Введите id подзадачи:");
                manager.deleteSubtaskById(scanner.nextLong());
                System.out.println("Подзадача удалена");
                break;
        }
    }

    public void deleteAll() {
        System.out.println("1 - задачи, 2 - эпики, 3 - подзадачи");
        String command2 = scanner.next();
        switch (command2) {
            case "1":
                manager.deleteAllTasks();
                System.out.println("Все обычные задачи удалены");
                break;
            case "2":
                manager.deleteAllEpics();
                System.out.println("Все эпики удалены");
                break;
            case "3":
                manager.deleteAllSubtasks();
                System.out.println("Все подзадачи удалены");
                break;
        }
    }

    public void getHistory() {
        System.out.println("Длина списка: " + manager.getHistory().size());
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }

    public void remindTasks() {
        System.out.println("Напомню список задач:");
        System.out.println(manager.getTasksList());
    }

    public void remindEpics() {
        System.out.println("Напомню список эпиков:");
        System.out.println(manager.getEpicsList());
    }

    public void remindSubtasks() {
        System.out.println("Напомню список подзадач:");
        System.out.println(manager.getSubtasksList());
    }
}
