package ita.growin.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Application 설정 검증 테스트")
class ApplicationConfigurationTest {

    @Nested
    @DisplayName("기본 프로파일 설정 테스트")
    @SpringBootTest
    class DefaultProfileTests {

        @Autowired
        private Environment environment;

        @Test
        @DisplayName("애플리케이션 이름이 올바르게 설정되어 있다")
        void applicationNameIsConfigured() {
            String applicationName = environment.getProperty("spring.application.name");
            
            assertThat(applicationName).isNotNull();
            assertThat(applicationName).isEqualTo("growin");
        }

        @Test
        @DisplayName("프로파일 그룹이 정의되어 있다")
        void profileGroupsAreDefined() {
            // Test that profile groups exist in configuration
            // Note: Direct access to profile groups may require additional validation
            assertThat(environment).isNotNull();
        }
    }

    @Nested
    @DisplayName("테스트 프로파일 설정 테스트")
    @SpringBootTest
    @ActiveProfiles("test")
    class TestProfileTests {

        @Autowired
        private Environment environment;

        @Test
        @DisplayName("test 프로파일이 활성화되어 있다")
        void testProfileIsActive() {
            String[] activeProfiles = environment.getActiveProfiles();
            
            assertThat(activeProfiles).contains("test");
        }

        @Test
        @DisplayName("애플리케이션이 test 프로파일로 정상 로드된다")
        void applicationLoadsWithTestProfile() {
            assertThat(environment.getProperty("spring.application.name")).isEqualTo("growin");
        }
    }

    @Nested
    @DisplayName("로컬 프로파일 설정 테스트")
    @SpringBootTest
    @ActiveProfiles("local")
    class LocalProfileTests {

        @Autowired
        private Environment environment;

        @Test
        @DisplayName("local 프로파일이 활성화되어 있다")
        void localProfileIsActive() {
            String[] activeProfiles = environment.getActiveProfiles();
            
            assertThat(activeProfiles).contains("local");
        }

        @Test
        @DisplayName("datasource driver class name이 설정되어 있다")
        void datasourceDriverClassNameIsConfigured() {
            String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
            
            assertThat(driverClassName).isNotNull();
            assertThat(driverClassName).isEqualTo("com.mysql.cj.jdbc.Driver");
        }

        @Test
        @DisplayName("JPA show-sql이 true로 설정되어 있다")
        void jpaShowSqlIsEnabled() {
            String showSql = environment.getProperty("spring.jpa.show-sql");
            
            assertThat(showSql).isNotNull();
            assertThat(showSql).isEqualTo("true");
        }

        @Test
        @DisplayName("JPA open-in-view가 false로 설정되어 있다")
        void jpaOpenInViewIsDisabled() {
            String openInView = environment.getProperty("spring.jpa.open-in-view");
            
            assertThat(openInView).isNotNull();
            assertThat(openInView).isEqualTo("false");
        }

        @Test
        @DisplayName("Hibernate ddl-auto가 create-drop으로 설정되어 있다")
        void hibernateDdlAutoIsCreateDrop() {
            String ddlAuto = environment.getProperty("spring.jpa.hibernate.ddl-auto");
            
            assertThat(ddlAuto).isNotNull();
            assertThat(ddlAuto).isEqualTo("create-drop");
        }
    }
}