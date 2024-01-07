import java.util.ArrayList;

public class Epic extends Task{

    ArrayList<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
    }


}
