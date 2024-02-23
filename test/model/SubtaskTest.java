package model;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtaskTest {

    @Test
    public void ObjectsShouldEqualsIfEqualsId() {
        final Subtask subtask1 = new Subtask(7, "a", "b", TaskStatus.NEW, 1);
        final Subtask subtask2 = new Subtask(7, "c", "d", TaskStatus.DONE, 5 );
        assertEquals(subtask1, subtask2, "Метод equals() для Subtask должен проверять только id");
    }

    @Test
    public void hashCodeShouldBeEqualsId() {
        final long id = 9;
        final Subtask subtask = new Subtask(id, "a", "b", TaskStatus.NEW, 1);
        assertEquals(Objects.hash(id), subtask.hashCode(), "Хеш-код объекта должен вычисляться только по id");
    }
}
