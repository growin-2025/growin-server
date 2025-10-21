package ita.growin.domain.task.entity;

import ita.growin.domain.event.entity.Event;
import ita.growin.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Task 엔티티 테스트")
class TaskTest {

    @Nested
    @DisplayName("생성자 테스트")
    class ConstructorTests {

        @Test
        @DisplayName("모든 필드를 포함한 생성자로 Task를 생성한다")
        void createTaskWithAllFields() {
            // given
            Long id = 1L;
            Event event = new Event(1L, new ArrayList<>(), null);

            // when
            Task task = new Task(id, event);

            // then
            assertThat(task).isNotNull();
            assertThat(task.getId()).isEqualTo(id);
            assertThat(task.getEvent()).isEqualTo(event);
        }

        @Test
        @DisplayName("null 값으로 Task를 생성한다")
        void createTaskWithNullValues() {
            // when
            Task task = new Task(null, null);

            // then
            assertThat(task).isNotNull();
            assertThat(task.getId()).isNull();
            assertThat(task.getEvent()).isNull();
        }

        @Test
        @DisplayName("Event 없이 Task를 생성한다")
        void createTaskWithoutEvent() {
            // given
            Long id = 1L;

            // when
            Task task = new Task(id, null);

            // then
            assertThat(task).isNotNull();
            assertThat(task.getId()).isEqualTo(id);
            assertThat(task.getEvent()).isNull();
        }

        @Test
        @DisplayName("protected 기본 생성자를 통해 Task를 생성한다")
        void createTaskWithDefaultConstructor() {
            // when & then
            assertThatCode(() -> {
                Task.class.getDeclaredConstructor().newInstance();
            }).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("Getter 테스트")
    class GetterTests {

        @Test
        @DisplayName("getId()로 Task ID를 조회한다")
        void getTaskId() {
            // given
            Long expectedId = 42L;
            Task task = new Task(expectedId, null);

            // when
            Long actualId = task.getId();

            // then
            assertThat(actualId).isEqualTo(expectedId);
        }

        @Test
        @DisplayName("getEvent()로 연관된 Event를 조회한다")
        void getEvent() {
            // given
            Event event = new Event(1L, new ArrayList<>(), null);
            Task task = new Task(1L, event);

            // when
            Event retrievedEvent = task.getEvent();

            // then
            assertThat(retrievedEvent).isEqualTo(event);
            assertThat(retrievedEvent.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Event가 null인 경우 getEvent()는 null을 반환한다")
        void getEventReturnsNull() {
            // given
            Task task = new Task(1L, null);

            // when
            Event retrievedEvent = task.getEvent();

            // then
            assertThat(retrievedEvent).isNull();
        }
    }

    @Nested
    @DisplayName("연관관계 테스트")
    class RelationshipTests {

        @Test
        @DisplayName("Task와 Event의 다대일 관계를 확인한다")
        void taskToEventManyToOneRelationship() {
            // given
            Event event = new Event(1L, new ArrayList<>(), null);
            Task task1 = new Task(1L, event);
            Task task2 = new Task(2L, event);
            Task task3 = new Task(3L, event);

            // then
            assertThat(task1.getEvent()).isSameAs(event);
            assertThat(task2.getEvent()).isSameAs(event);
            assertThat(task3.getEvent()).isSameAs(event);
        }

        @Test
        @DisplayName("여러 Task가 같은 Event를 참조한다")
        void multipleTasksSameEvent() {
            // given
            User user = new User(1L, new ArrayList<>());
            Event event = new Event(1L, new ArrayList<>(), user);

            // when
            Task task1 = new Task(1L, event);
            Task task2 = new Task(2L, event);

            // then
            assertThat(task1.getEvent()).isEqualTo(task2.getEvent());
            assertThat(task1.getEvent()).isSameAs(event);
        }

        @Test
        @DisplayName("다른 Event를 참조하는 Task들을 생성한다")
        void tasksWithDifferentEvents() {
            // given
            Event event1 = new Event(1L, new ArrayList<>(), null);
            Event event2 = new Event(2L, new ArrayList<>(), null);

            // when
            Task task1 = new Task(1L, event1);
            Task task2 = new Task(2L, event2);

            // then
            assertThat(task1.getEvent()).isNotEqualTo(task2.getEvent());
            assertThat(task1.getEvent().getId()).isEqualTo(1L);
            assertThat(task2.getEvent().getId()).isEqualTo(2L);
        }

        @Test
        @DisplayName("Event를 통해 Task에 접근하고 다시 Event로 돌아온다")
        void bidirectionalRelationshipNavigation() {
            // given
            Event event = new Event(1L, new ArrayList<>(), null);
            Task task = new Task(1L, event);

            // when
            Event retrievedEvent = task.getEvent();

            // then
            assertThat(retrievedEvent).isSameAs(event);
            assertThat(retrievedEvent.getId()).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("불변성 테스트")
    class ImmutabilityTests {

        @Test
        @DisplayName("Task 엔티티의 필드는 Getter만 제공한다")
        void taskFieldsAreReadOnly() {
            // given
            Task task = new Task(1L, null);

            // when & then
            assertThat(Task.class.getDeclaredMethods())
                    .noneMatch(method -> method.getName().startsWith("set"));
        }
    }

    @Nested
    @DisplayName("경계값 테스트")
    class BoundaryTests {

        @Test
        @DisplayName("ID가 0인 Task를 생성한다")
        void createTaskWithZeroId() {
            // when
            Task task = new Task(0L, null);

            // then
            assertThat(task.getId()).isEqualTo(0L);
        }

        @Test
        @DisplayName("매우 큰 ID 값으로 Task를 생성한다")
        void createTaskWithLargeId() {
            // given
            Long largeId = Long.MAX_VALUE;

            // when
            Task task = new Task(largeId, null);

            // then
            assertThat(task.getId()).isEqualTo(Long.MAX_VALUE);
        }

        @Test
        @DisplayName("음수 ID 값으로 Task를 생성한다")
        void createTaskWithNegativeId() {
            // given
            Long negativeId = -1L;

            // when
            Task task = new Task(negativeId, null);

            // then
            assertThat(task.getId()).isEqualTo(-1L);
        }

        @Test
        @DisplayName("최소값 ID로 Task를 생성한다")
        void createTaskWithMinId() {
            // given
            Long minId = Long.MIN_VALUE;

            // when
            Task task = new Task(minId, null);

            // then
            assertThat(task.getId()).isEqualTo(Long.MIN_VALUE);
        }
    }

    @Nested
    @DisplayName("동등성 테스트")
    class EqualityTests {

        @Test
        @DisplayName("같은 ID를 가진 두 Task 객체의 동등성을 확인한다")
        void tasksWithSameIdEquality() {
            // given
            Task task1 = new Task(1L, null);
            Task task2 = new Task(1L, null);

            // Note: Lombok doesn't generate equals/hashCode by default
            // This test documents the current behavior
            assertThat(task1).isNotEqualTo(task2);
            assertThat(task1).isNotSameAs(task2);
        }

        @Test
        @DisplayName("자기 자신과의 동등성을 확인한다")
        void taskEqualityWithSelf() {
            // given
            Task task = new Task(1L, null);

            // when & then
            assertThat(task).isEqualTo(task);
            assertThat(task).isSameAs(task);
        }

        @Test
        @DisplayName("다른 ID를 가진 Task 객체는 다르다")
        void tasksWithDifferentIdsAreNotEqual() {
            // given
            Task task1 = new Task(1L, null);
            Task task2 = new Task(2L, null);

            // when & then
            assertThat(task1).isNotEqualTo(task2);
        }
    }

    @Nested
    @DisplayName("nullable 컬럼 테스트")
    class NullableColumnTests {

        @Test
        @DisplayName("Task ID는 nullable이 false이므로 비즈니스 로직에서 검증이 필요하다")
        void taskIdShouldNotBeNull() {
            // This test documents that the entity allows null in Java,
            // but the database constraint (nullable = false) will enforce non-null
            Task task = new Task(null, null);
            
            assertThat(task.getId()).isNull();
            // In a real scenario, this would fail at persistence time
        }
    }
}