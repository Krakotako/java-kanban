package model;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {

    @Test
    public void ObjectsShouldEqualsIfEqualsId() {
        final Epic epic1 = new Epic(5, "a", "b", TaskStatus.NEW);
        final Epic epic2= new Epic(5, "c", "d", TaskStatus.IN_PROGRESS);
        assertEquals(epic1, epic2, "Метод equals() для Epic должен проверять только id");
    }

    @Test
    public void hashCodeShouldBeEqualsId() {
        final long id = 8;
        final Epic epic = new Epic(id, "a", "b", TaskStatus.NEW);
        assertEquals(Objects.hash(id), epic.hashCode(), "Хеш-код объекта должен вычисляться только по id");
    }

}