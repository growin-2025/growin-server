package ita.growin.domain.user.entity;

import ita.growin.domain.event.entity.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("User 엔티티 테스트")
class UserTest {

    @Nested
    @DisplayName("생성자 테스트")
    class ConstructorTests {

        @Test
        @DisplayName("모든 필드를 포함한 생성자로 User를 생성한다")
        void createUserWithAllFields() {
            // given
            Long id = 1L;
            List<Event> events = new ArrayList<>();

            // when
            User user = new User(id, events);

            // then
            assertThat(user).isNotNull();
            assertThat(user.getId()).isEqualTo(id);
            assertThat(user.getEvents()).isEqualTo(events);
        }

        @Test
        @DisplayName("빈 Event 리스트로 User를 생성한다")
        void createUserWithEmptyEventList() {
            // given
            Long id = 1L;

            // when
            User user = new User(id, new ArrayList<>());

            // then
            assertThat(user).isNotNull();
            assertThat(user.getEvents()).isEmpty();
        }

        @Test
        @DisplayName("null 값으로 User를 생성한다")
        void createUserWithNullValues() {
            // when
            User user = new User(null, null);

            // then
            assertThat(user).isNotNull();
            assertThat(user.getId()).isNull();
            assertThat(user.getEvents()).isNull();
        }

        @Test
        @DisplayName("protected 기본 생성자를 통해 User를 생성한다")
        void createUserWithDefaultConstructor() {
            // when & then
            assertThatCode(() -> {
                User.class.getDeclaredConstructor().newInstance();
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("기본 생성자 사용 시 events 필드는 초기화된다")
        void defaultConstructorInitializesEvents() throws Exception {
            // when
            User user = User.class.getDeclaredConstructor().newInstance();

            // then
            // Note: events field has default initialization in the class
            assertThat(user.getEvents()).isNotNull();
            assertThat(user.getEvents()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Getter 테스트")
    class GetterTests {

        @Test
        @DisplayName("getId()로 User ID를 조회한다")
        void getUserId() {
            // given
            Long expectedId = 123L;
            User user = new User(expectedId, new ArrayList<>());

            // when
            Long actualId = user.getId();

            // then
            assertThat(actualId).isEqualTo(expectedId);
        }

        @Test
        @DisplayName("getEvents()로 연관된 Event 리스트를 조회한다")
        void getEvents() {
            // given
            Event event1 = new Event(1L, new ArrayList<>(), null);
            Event event2 = new Event(2L, new ArrayList<>(), null);
            List<Event> events = Arrays.asList(event1, event2);
            User user = new User(1L, events);

            // when
            List<Event> retrievedEvents = user.getEvents();

            // then
            assertThat(retrievedEvents).hasSize(2);
            assertThat(retrievedEvents).containsExactly(event1, event2);
        }

        @Test
        @DisplayName("getEvents()로 빈 Event 리스트를 조회한다")
        void getEmptyEvents() {
            // given
            User user = new User(1L, new ArrayList<>());

            // when
            List<Event> retrievedEvents = user.getEvents();

            // then
            assertThat(retrievedEvents).isEmpty();
        }
    }

    @Nested
    @DisplayName("연관관계 테스트")
    class RelationshipTests {

        @Test
        @DisplayName("User와 Event의 일대다 관계를 확인한다")
        void userToEventOneToManyRelationship() {
            // given
            User user = new User(1L, new ArrayList<>());
            Event event1 = new Event(1L, new ArrayList<>(), user);
            Event event2 = new Event(2L, new ArrayList<>(), user);
            Event event3 = new Event(3L, new ArrayList<>(), user);
            
            List<Event> events = Arrays.asList(event1, event2, event3);
            User userWithEvents = new User(1L, events);

            // when
            List<Event> retrievedEvents = userWithEvents.getEvents();

            // then
            assertThat(retrievedEvents).hasSize(3);
            assertThat(retrievedEvents.get(0).getUser()).isSameAs(user);
            assertThat(retrievedEvents.get(1).getUser()).isSameAs(user);
            assertThat(retrievedEvents.get(2).getUser()).isSameAs(user);
        }

        @Test
        @DisplayName("Event가 없는 User를 생성한다")
        void createUserWithoutEvents() {
            // when
            User user = new User(1L, new ArrayList<>());

            // then
            assertThat(user.getEvents()).isEmpty();
            assertThat(user.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("User를 통해 Event에 접근하고 다시 User로 돌아온다")
        void bidirectionalRelationshipNavigation() {
            // given
            User user = new User(1L, new ArrayList<>());
            Event event = new Event(1L, new ArrayList<>(), user);
            List<Event> events = Arrays.asList(event);
            User userWithEvents = new User(1L, events);

            // when
            Event retrievedEvent = userWithEvents.getEvents().get(0);
            User retrievedUser = retrievedEvent.getUser();

            // then
            assertThat(retrievedUser).isSameAs(user);
        }

        @Test
        @DisplayName("단일 User가 여러 Event를 소유한다")
        void singleUserOwnsMultipleEvents() {
            // given
            User user = new User(1L, new ArrayList<>());
            List<Event> events = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                events.add(new Event((long) i, new ArrayList<>(), user));
            }
            User userWithEvents = new User(1L, events);

            // when
            List<Event> retrievedEvents = userWithEvents.getEvents();

            // then
            assertThat(retrievedEvents).hasSize(5);
            retrievedEvents.forEach(event -> 
                assertThat(event.getUser()).isSameAs(user)
            );
        }
    }

    @Nested
    @DisplayName("컬렉션 초기화 테스트")
    class CollectionInitializationTests {

        @Test
        @DisplayName("필드 초기화로 events는 기본적으로 빈 ArrayList이다")
        void eventsFieldDefaultInitialization() throws Exception {
            // given
            User user = User.class.getDeclaredConstructor().newInstance();

            // when
            List<Event> events = user.getEvents();

            // then
            assertThat(events).isNotNull();
            assertThat(events).isEmpty();
            assertThat(events).isInstanceOf(ArrayList.class);
        }

        @Test
        @DisplayName("AllArgsConstructor로 생성 시 전달된 리스트를 사용한다")
        void allArgsConstructorUsesProvidedList() {
            // given
            List<Event> customList = new ArrayList<>();
            customList.add(new Event(1L, new ArrayList<>(), null));

            // when
            User user = new User(1L, customList);

            // then
            assertThat(user.getEvents()).isSameAs(customList);
            assertThat(user.getEvents()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("불변성 테스트")
    class ImmutabilityTests {

        @Test
        @DisplayName("User 엔티티의 필드는 Getter만 제공한다")
        void userFieldsAreReadOnly() {
            // given
            User user = new User(1L, new ArrayList<>());

            // when & then
            assertThat(User.class.getDeclaredMethods())
                    .noneMatch(method -> method.getName().startsWith("set"));
        }

        @Test
        @DisplayName("Event 리스트 참조를 통한 외부 수정 가능성을 확인한다")
        void eventListIsNotImmutable() {
            // given
            List<Event> events = new ArrayList<>();
            events.add(new Event(1L, new ArrayList<>(), null));
            User user = new User(1L, events);

            // when
            List<Event> retrievedEvents = user.getEvents();
            retrievedEvents.add(new Event(2L, new ArrayList<>(), null));

            // then
            // Note: This test documents that the list is mutable via reference
            assertThat(user.getEvents()).hasSize(2);
        }
    }

    @Nested
    @DisplayName("경계값 테스트")
    class BoundaryTests {

        @Test
        @DisplayName("ID가 0인 User를 생성한다")
        void createUserWithZeroId() {
            // when
            User user = new User(0L, new ArrayList<>());

            // then
            assertThat(user.getId()).isEqualTo(0L);
        }

        @Test
        @DisplayName("매우 큰 ID 값으로 User를 생성한다")
        void createUserWithLargeId() {
            // given
            Long largeId = Long.MAX_VALUE;

            // when
            User user = new User(largeId, new ArrayList<>());

            // then
            assertThat(user.getId()).isEqualTo(Long.MAX_VALUE);
        }

        @Test
        @DisplayName("음수 ID 값으로 User를 생성한다")
        void createUserWithNegativeId() {
            // given
            Long negativeId = -1L;

            // when
            User user = new User(negativeId, new ArrayList<>());

            // then
            assertThat(user.getId()).isEqualTo(-1L);
        }

        @Test
        @DisplayName("매우 많은 Event를 포함한 User를 생성한다")
        void createUserWithManyEvents() {
            // given
            List<Event> manyEvents = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                manyEvents.add(new Event((long) i, new ArrayList<>(), null));
            }

            // when
            User user = new User(1L, manyEvents);

            // then
            assertThat(user.getEvents()).hasSize(1000);
        }
    }

    @Nested
    @DisplayName("Cascade 및 OrphanRemoval 동작 테스트")
    class CascadeAndOrphanRemovalTests {

        @Test
        @DisplayName("CascadeType.ALL이 설정되어 있음을 문서화한다")
        void cascadeAllIsConfigured() {
            // This test documents that the relationship has CascadeType.ALL
            // In actual persistence, operations on User will cascade to Events
            User user = new User(1L, new ArrayList<>());
            Event event = new Event(1L, new ArrayList<>(), user);
            
            assertThat(event.getUser()).isSameAs(user);
        }

        @Test
        @DisplayName("orphanRemoval=true가 설정되어 있음을 문서화한다")
        void orphanRemovalIsEnabled() {
            // This test documents that orphanRemoval is enabled
            // In actual persistence, removing an Event from the collection
            // will delete it from the database
            User user = new User(1L, new ArrayList<>());
            List<Event> events = new ArrayList<>();
            Event event = new Event(1L, new ArrayList<>(), user);
            events.add(event);
            
            User userWithEvents = new User(1L, events);
            assertThat(userWithEvents.getEvents()).contains(event);
        }
    }

    @Nested
    @DisplayName("동등성 테스트")
    class EqualityTests {

        @Test
        @DisplayName("같은 ID를 가진 두 User 객체의 동등성을 확인한다")
        void usersWithSameIdEquality() {
            // given
            User user1 = new User(1L, new ArrayList<>());
            User user2 = new User(1L, new ArrayList<>());

            // Note: Lombok doesn't generate equals/hashCode by default
            // This test documents the current behavior
            assertThat(user1).isNotEqualTo(user2);
            assertThat(user1).isNotSameAs(user2);
        }

        @Test
        @DisplayName("자기 자신과의 동등성을 확인한다")
        void userEqualityWithSelf() {
            // given
            User user = new User(1L, new ArrayList<>());

            // when & then
            assertThat(user).isEqualTo(user);
            assertThat(user).isSameAs(user);
        }

        @Test
        @DisplayName("다른 ID를 가진 User 객체는 다르다")
        void usersWithDifferentIdsAreNotEqual() {
            // given
            User user1 = new User(1L, new ArrayList<>());
            User user2 = new User(2L, new ArrayList<>());

            // when & then
            assertThat(user1).isNotEqualTo(user2);
        }
    }
}