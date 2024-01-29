import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Demonstration demonstration = new Demonstration();

        while (true) {
            demonstration.printMenu();
            String command = scanner.next();

            switch (command) {
                case "1":
                    demonstration.addNew();
                    break;
                case "2":
                    demonstration.getList();
                    break;
                case "3":
                    demonstration.getById();
                    break;
                case "4":
                    demonstration.update();
                    break;
                case "5":
                    demonstration.deleteById();
                    break;
                case "6":
                    demonstration.deleteAll();
                    break;
                case "7":
                    demonstration.getHistory();
                    break;
                case "0":
                    System.out.println("Завершение работы");
                    scanner.close();
                    return;
                default:
                    System.out.println("Такой команды нет");
            }
        }
    }
}
