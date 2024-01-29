package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Objects;

public class TaskTest {

    @Test
    public void shouldEqualsObjectsIfEqualsId() {
        final Task task1 = new Task(4,"a", "b", TaskStatus.NEW);
        final Task task2 = new Task(4, "c", "d", TaskStatus.DONE);
        assertEquals(task1, task2, "Метод equals() для Task должен проверять только id");
    }

    @Test
    public void TaskhashCodeShouldBeEqualsIdHashCode() {
        final long id = 7;
        final Task task = new Task(id, "a", "b", TaskStatus.NEW);
        assertEquals(Objects.hash(id), task.hashCode(), "Хеш-код обьекта не равен его полю id");
    }
}
