package model;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void ObjectsShouldEqualsIfEqualsId() {
        final Task task1 = new Task(4,"a", "b", TaskStatus.NEW);
        final Task task2 = new Task(4, "c", "d", TaskStatus.DONE);
        assertEquals(task1, task2, "Метод equals() для Task должен проверять только id");
    }

    @Test
    public void TaskHashCodeShouldBeEqualsIdHashCode() {
        final long id = 7;
        final Task task = new Task(id, "a", "b", TaskStatus.NEW);
        assertEquals(Objects.hash(id), task.hashCode(), "Хеш-код объекта должен вычисляться только по id");
    }
}
