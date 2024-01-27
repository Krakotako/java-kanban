import service.InMemoryTaskManager;
import model.Task;
import model.TaskStatus;
import model.Epic;
import model.Subtask;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            printMenu();
            String command = scanner.next();

            if (command.equals("1")) {
                System.out.println("1 - задача, 2 - эпик, 3 - подзадача");
                String command2 = scanner.next();
                if (command2.equals("1")) {
                    System.out.println("Вводишь без ошибок через enter: Имя, Описание, Статус задачи строкой");
                    manager.addNewTask(new Task(scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next())));
                    System.out.println("Задача добавлена");
                } else if (command2.equals("2")) {
                    System.out.println("Вводишь без ошибок через enter: Имя Описание, Статус эпика строкой");
                    manager.addNewEpic(new Epic(scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next())));
                    System.out.println("Эпик добавлен");
                } else if (command2.equals("3")) {
                    System.out.println("вот список эпиков:");
                    System.out.println(manager.getEpicsList());
                    System.out.println("Вводишь без ошибок через enter: имя описание статус epicId подзадачи");
                    manager.addNewSubtask(new Subtask(scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next()), scanner.nextInt()));
                    System.out.println("Подзадача добавлена");
                }
            } else if (command.equals("2")) {
                System.out.println("1 - задач, 2 - эпиков, 3 - подзадач, 4 - подзадач эпика");
                String command2 = scanner.next();
                if (command2.equals("1")) {
                    System.out.println(manager.getTasksList());
                } else if (command2.equals("2")) {
                    System.out.println(manager.getEpicsList());
                } else if (command2.equals("3")) {
                    System.out.println(manager.getAllSubtasksList());
                } else if (command2.equals("4")) {
                    System.out.println("вот список эпиков:");
                    System.out.println(manager.getEpicsList());
                    System.out.println("Введи id эпика (в метод передаётся объект)");
                    System.out.println(manager.getSubtasksListByEpic(manager.getEpicById(scanner.nextInt())));
                }
            } else if (command.equals("3")) {
                System.out.println("1 - задачу, 2 - эпик, 3 - подзадачу");
                String command2 = scanner.next();
                if (command2.equals("1")) {
                    System.out.println("вот список задач:");
                    System.out.println(manager.getTasksList());
                    System.out.println("Введи id:");
                    System.out.println(manager.getTaskById(scanner.nextInt()));
                } else if (command2.equals("2")) {
                    System.out.println("вот список эпиков:");
                    System.out.println(manager.getEpicsList());
                    System.out.println("Введи id:");
                    System.out.println(manager.getEpicById(scanner.nextInt()));
                } else if (command2.equals("3")) {
                    System.out.println("вот список всех подзадач:");
                    System.out.println(manager.getAllSubtasksList());
                    System.out.println("Введи id:");
                    System.out.println(manager.getSubtaskById(scanner.nextInt()));
                }
            } else if (command.equals("4")) {
                System.out.println("1 - задачу, 2 - эпик, 3 - подзадачу");
                String command2 = scanner.next();
                if (command2.equals("1")) {
                    System.out.println("вот список задач:");
                    System.out.println(manager.getTasksList());
                    System.out.println("сначала выбери id задачи");
                    Task oldTask = manager.getTaskById(scanner.nextInt());
                    System.out.println("Сейчас эта задача выглядит так: ");
                    System.out.println(oldTask);
                    System.out.println("Теперь снова вводишь через enter без ошибок: Имя, Описание, Статус задачи строкой");
                    Task updatedTask = new Task(scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next()));
                    updatedTask.setId(oldTask.getId());
                    manager.updateTask(updatedTask);
                    System.out.println("Задача обновлена");
                } else if (command2.equals("2")) {
                    System.out.println("вот список эпиков:");
                    System.out.println(manager.getEpicsList());
                    System.out.println("сначала выбери id эпика");
                    Epic oldEpic = manager.getEpicById(scanner.nextInt());
                    System.out.println("Сейчас этот эпик выглядит так: ");
                    System.out.println(oldEpic);
                    System.out.println("Теперь снова вводишь через enter без ошибок: Имя, Описание эпика");
                    Epic updatedEpic = new Epic(scanner.next(), scanner.next(), oldEpic.getStatus());
                    updatedEpic.setId(oldEpic.getId());
                    manager.updateEpic(updatedEpic);
                    System.out.println("Эпик обновлен");
                } else if (command2.equals("3")) {
                    System.out.println("вот список всех подзадач:");
                    System.out.println(manager.getAllSubtasksList());
                    System.out.println("сначала выбери id подзадачи");
                    Subtask oldSubtask = manager.getSubtaskById(scanner.nextInt());
                    System.out.println("Сейчас эта подзадача выглядит так: ");
                    System.out.println(oldSubtask);
                    System.out.println("Теперь снова вводишь через enter без ошибок: Имя, Описание, Статус подзадачи строкой");
                    Subtask updatedSubtask = new Subtask(scanner.next(), scanner.next(), TaskStatus.valueOf(scanner.next()), oldSubtask.getEpicId());
                    updatedSubtask.setId(oldSubtask.getId());
                    manager.updateSubtask(updatedSubtask);
                    System.out.println("Подзадача обновлена");
                }
            } else if (command.equals("5")) {
                System.out.println("1 - задачу, 2 - эпик, 3 - подзадачу");
                String command2 = scanner.next();
                if (command2.equals("1")) {
                    System.out.println("вот список задач:");
                    System.out.println(manager.getTasksList());
                    System.out.println("Введи id задачи:");
                    manager.deleteTaskById(scanner.nextInt());
                    System.out.println("Задача удалена");
                } else if (command2.equals("2")) {
                    System.out.println("вот список эпиков:");
                    System.out.println(manager.getEpicsList());
                    System.out.println("Введи id эпика:");
                    manager.deleteEpicById(scanner.nextInt());
                    System.out.println("Эпик удален");
                } else if (command2.equals("3")) {
                    System.out.println("вот список всех подзадач:");
                    System.out.println(manager.getAllSubtasksList());
                    System.out.println("Введи id подзадачи:");
                    manager.deleteSubtaskById(scanner.nextInt());
                    System.out.println("Подзадача удалена");
                }
            } else if (command.equals("6")) {
                System.out.println("1 - задачи, 2 - эпики, 3 - подзадачи");
                String command2 = scanner.next();
                if (command2.equals("1")) {
                    manager.deleteAllTasks();
                    System.out.println("Все обычные задачи удалены");
                } else if (command2.equals("2")) {
                    manager.deleteAllEpics();
                    System.out.println("Все эпики удалены");
                } else if (command2.equals("3")) {
                    manager.deleteAllSubtasks();
                    System.out.println("Все подзадачи удалены");
                }
            } else if (command.equals("0")) {
                System.out.println("Завершение работы");
                scanner.close();
                return;
            } else {
                System.out.println("Такой команды нет");
            }
        }
    }

    public static void printMenu() {
        System.out.println("1 - создать");
        System.out.println("2 - вывести список");
        System.out.println("3 - получить по id");
        System.out.println("4 - обновить");
        System.out.println("5 - удалить по id");
        System.out.println("6 - удалить все");
        System.out.println("0 - Выход");
    }
}
