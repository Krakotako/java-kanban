public class Subtask extends Task {

    int epicId;

    public Subtask(String name, String description, Epic epic ) {
        super(name, description);
        this.epicId = epic.id;
    }
}
