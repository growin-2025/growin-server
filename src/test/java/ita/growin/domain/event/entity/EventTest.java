package ita.growin.domain.event.entity;

import ita.growin.domain.task.entity.Task;
import ita.growin.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Event 엔티티 테스트")
class EventTest {

    @Nested
    @DisplayName("생성자 테스트")
    class ConstructorTests {

        @Test
        @DisplayName("모든 필드를 포함한 생성자로 Event를 생성한다")
        void createEventWithAllFields() {
            // given
            Long id = 1L;
            User user = new User(1L, new ArrayList<>());
            List<Task> tasks = new ArrayList<>();

            // when
            Event event = new Event(id, tasks, user);

            // then
            assertThat(event).isNotNull();
            assertThat(event.getId()).isEqualTo(id);
            assertThat(event.getUser()).isEqualTo(user);
            assertThat(event.getTasks()).isEqualTo(tasks);
        }

        @Test
        @DisplayName("null 값으로 Event를 생성한다")
        void createEventWithNullValues() {
            // when
            Event event = new Event(null, null, null);

            // then
            assertThat(event).isNotNull();
            assertThat(event.getId()).isNull();
            assertThat(event.getUser()).isNull();
            assertThat(event.getTasks()).isNull();
        }

        @Test
        @DisplayName("protected 기본 생성자를 통해 Event를 생성한다")
        void createEventWithDefaultConstructor() {
            // when & then
            assertThatCode(() -> {
                Event.class.getDeclaredConstructor().newInstance();
            }).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("Getter 테스트")
    class GetterTests {

        @Test
        @DisplayName("getId()로 Event ID를 조회한다")
        void getEventId() {
            // given
            Long expectedId = 100L;
            Event event = new Event(expectedId, new ArrayList<>(), null);

            // when
            Long actualId = event.getId();

            // then
            assertThat(actualId).isEqualTo(expectedId);
        }

        @Test
        @DisplayName("getUser()로 연관된 User를 조회한다")
        void getUser() {
            // given
            User user = new User(1L, new ArrayList<>());
            Event event = new Event(1L, new ArrayList<>(), user);

            // when
            User retrievedUser = event.getUser();

            // then
            assertThat(retrievedUser).isEqualTo(user);
            assertThat(retrievedUser.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("getTasks()로 연관된 Task 리스트를 조회한다")
        void getTasks() {
            // given
            Task task1 = new Task(1L, null);
            Task task2 = new Task(2L, null);
            List<Task> tasks = Arrays.asList(task1, task2);
            Event event = new Event(1L, tasks, null);

            // when
            List<Task> retrievedTasks = event.getTasks();

            // then
            assertThat(retrievedTasks).hasSize(2);
            assertThat(retrievedTasks).containsExactly(task1, task2);
        }

        @Test
        @DisplayName("getTasks()로 빈 Task 리스트를 조회한다")
        void getEmptyTasks() {
            // given
            List<Task> emptyTasks = new ArrayList<>();
            Event event = new Event(1L, emptyTasks, null);

            // when
            List<Task> retrievedTasks = event.getTasks();

            // then
            assertThat(retrievedTasks).isEmpty();
        }
    }

    @Nested
    @DisplayName("연관관계 테스트")
    class RelationshipTests {

        @Test
        @DisplayName("Event와 User의 다대일 관계를 확인한다")
        void eventToUserManyToOneRelationship() {
            // given
            User user = new User(1L, new ArrayList<>());
            Event event1 = new Event(1L, new ArrayList<>(), user);
            Event event2 = new Event(2L, new ArrayList<>(), user);

            // then
            assertThat(event1.getUser()).isSameAs(user);
            assertThat(event2.getUser()).isSameAs(user);
        }

        @Test
        @DisplayName("Event와 Task의 일대다 관계를 확인한다")
        void eventToTaskOneToManyRelationship() {
            // given
            Event event = new Event(1L, new ArrayList<>(), null);
            Task task1 = new Task(1L, event);
            Task task2 = new Task(2L, event);
            Task task3 = new Task(3L, event);
            
            List<Task> tasks = Arrays.asList(task1, task2, task3);
            Event eventWithTasks = new Event(1L, tasks, null);

            // when
            List<Task> retrievedTasks = eventWithTasks.getTasks();

            // then
            assertThat(retrievedTasks).hasSize(3);
            assertThat(retrievedTasks.get(0).getEvent()).isSameAs(event);
            assertThat(retrievedTasks.get(1).getEvent()).isSameAs(event);
            assertThat(retrievedTasks.get(2).getEvent()).isSameAs(event);
        }

        @Test
        @DisplayName("User가 없는 Event를 생성한다")
        void createEventWithoutUser() {
            // given
            List<Task> tasks = new ArrayList<>();

            // when
            Event event = new Event(1L, tasks, null);

            // then
            assertThat(event.getUser()).isNull();
            assertThat(event.getTasks()).isNotNull();
        }

        @Test
        @DisplayName("Task가 없는 Event를 생성한다")
        void createEventWithoutTasks() {
            // given
            User user = new User(1L, new ArrayList<>());

            // when
            Event event = new Event(1L, new ArrayList<>(), user);

            // then
            assertThat(event.getTasks()).isEmpty();
            assertThat(event.getUser()).isNotNull();
        }
    }

    @Nested
    @DisplayName("불변성 테스트")
    class ImmutabilityTests {

        @Test
        @DisplayName("Event 엔티티의 필드는 Getter만 제공한다")
        void eventFieldsAreReadOnly() {
            // given
            Event event = new Event(1L, new ArrayList<>(), null);

            // when & then
            assertThat(Event.class.getDeclaredMethods())
                    .noneMatch(method -> method.getName().startsWith("set"));
        }

        @Test
        @DisplayName("Task 리스트 참조를 통한 외부 수정 가능성을 확인한다")
        void taskListIsNotImmutable() {
            // given
            List<Task> tasks = new ArrayList<>();
            tasks.add(new Task(1L, null));
            Event event = new Event(1L, tasks, null);

            // when
            List<Task> retrievedTasks = event.getTasks();
            retrievedTasks.add(new Task(2L, null));

            // then
            // Note: This test documents that the list is mutable via reference
            assertThat(event.getTasks()).hasSize(2);
        }
    }

    @Nested
    @DisplayName("경계값 테스트")
    class BoundaryTests {

        @Test
        @DisplayName("ID가 0인 Event를 생성한다")
        void createEventWithZeroId() {
            // when
            Event event = new Event(0L, new ArrayList<>(), null);

            // then
            assertThat(event.getId()).isEqualTo(0L);
        }

        @Test
        @DisplayName("매우 큰 ID 값으로 Event를 생성한다")
        void createEventWithLargeId() {
            // given
            Long largeId = Long.MAX_VALUE;

            // when
            Event event = new Event(largeId, new ArrayList<>(), null);

            // then
            assertThat(event.getId()).isEqualTo(Long.MAX_VALUE);
        }

        @Test
        @DisplayName("음수 ID 값으로 Event를 생성한다")
        void createEventWithNegativeId() {
            // given
            Long negativeId = -1L;

            // when
            Event event = new Event(negativeId, new ArrayList<>(), null);

            // then
            assertThat(event.getId()).isEqualTo(-1L);
        }

        @Test
        @DisplayName("매우 많은 Task를 포함한 Event를 생성한다")
        void createEventWithManyTasks() {
            // given
            List<Task> manyTasks = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                manyTasks.add(new Task((long) i, null));
            }

            // when
            Event event = new Event(1L, manyTasks, null);

            // then
            assertThat(event.getTasks()).hasSize(1000);
        }
    }

    @Nested
    @DisplayName("동등성 테스트")
    class EqualityTests {

        @Test
        @DisplayName("같은 ID를 가진 두 Event 객체의 동등성을 확인한다")
        void eventsWithSameIdEquality() {
            // given
            Event event1 = new Event(1L, new ArrayList<>(), null);
            Event event2 = new Event(1L, new ArrayList<>(), null);

            // Note: Lombok doesn't generate equals/hashCode by default
            // This test documents the current behavior
            assertThat(event1).isNotEqualTo(event2);
            assertThat(event1).isNotSameAs(event2);
        }

        @Test
        @DisplayName("자기 자신과의 동등성을 확인한다")
        void eventEqualityWithSelf() {
            // given
            Event event = new Event(1L, new ArrayList<>(), null);

            // when & then
            assertThat(event).isEqualTo(event);
            assertThat(event).isSameAs(event);
        }
    }
}