package ita.growin.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("YAML 설정 파일 구조 검증 테스트")
class YamlConfigurationValidationTest {

    @Nested
    @DisplayName("application.yaml 검증")
    class ApplicationYamlTests {

        @Test
        @DisplayName("application.yaml 파일이 존재하고 파싱 가능하다")
        void applicationYamlExistsAndParseable() {
            assertThatCode(() -> {
                ClassPathResource resource = new ClassPathResource("application.yaml");
                assertThat(resource.exists()).isTrue();
                
                YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
                List<PropertySource<?>> propertySources = loader.load("application", resource);
                
                assertThat(propertySources).isNotEmpty();
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("application.yaml에 spring.application.name이 정의되어 있다")
        void springApplicationNameIsDefined() throws IOException {
            ClassPathResource resource = new ClassPathResource("application.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            Object applicationName = propertySource.getProperty("spring.application.name");
            
            assertThat(applicationName).isNotNull();
            assertThat(applicationName).isEqualTo("growin");
        }

        @Test
        @DisplayName("application.yaml에 프로파일 그룹이 정의되어 있다")
        void profileGroupsAreDefined() throws IOException {
            ClassPathResource resource = new ClassPathResource("application.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            
            // Check for profile group definitions
            Object localGroup = propertySource.getProperty("spring.profiles.group.local");
            Object devGroup = propertySource.getProperty("spring.profiles.group.dev");
            Object testGroup = propertySource.getProperty("spring.profiles.group.test");
            
            assertThat(localGroup).isNotNull();
            assertThat(devGroup).isNotNull();
            assertThat(testGroup).isNotNull();
        }
    }

    @Nested
    @DisplayName("application-local.yaml 검증")
    class ApplicationLocalYamlTests {

        @Test
        @DisplayName("application-local.yaml 파일이 존재하고 파싱 가능하다")
        void applicationLocalYamlExistsAndParseable() {
            assertThatCode(() -> {
                ClassPathResource resource = new ClassPathResource("application-local.yaml");
                assertThat(resource.exists()).isTrue();
                
                YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
                List<PropertySource<?>> propertySources = loader.load("application-local", resource);
                
                assertThat(propertySources).isNotEmpty();
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("application-local.yaml에 필수 datasource 설정이 정의되어 있다")
        void requiredDatasourcePropertiesAreDefined() throws IOException {
            ClassPathResource resource = new ClassPathResource("application-local.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application-local", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            
            assertThat(propertySource.getProperty("spring.datasource.driver-class-name")).isNotNull();
            assertThat(propertySource.getProperty("spring.datasource.url")).isNotNull();
            assertThat(propertySource.getProperty("spring.datasource.username")).isNotNull();
            assertThat(propertySource.getProperty("spring.datasource.password")).isNotNull();
        }

        @Test
        @DisplayName("application-local.yaml에 JPA 설정이 정의되어 있다")
        void jpaPropertiesAreDefined() throws IOException {
            ClassPathResource resource = new ClassPathResource("application-local.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application-local", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            
            assertThat(propertySource.getProperty("spring.jpa.show-sql")).isEqualTo(true);
            assertThat(propertySource.getProperty("spring.jpa.open-in-view")).isEqualTo(false);
            assertThat(propertySource.getProperty("spring.jpa.hibernate.ddl-auto")).isEqualTo("create-drop");
        }

        @Test
        @DisplayName("application-local.yaml의 driver-class-name이 MySQL이다")
        void driverClassNameIsMySQL() throws IOException {
            ClassPathResource resource = new ClassPathResource("application-local.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application-local", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            String driverClassName = (String) propertySource.getProperty("spring.datasource.driver-class-name");
            
            assertThat(driverClassName).isEqualTo("com.mysql.cj.jdbc.Driver");
        }

        @Test
        @DisplayName("application-local.yaml의 프로파일이 local이다")
        void profileIsLocal() throws IOException {
            ClassPathResource resource = new ClassPathResource("application-local.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application-local", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            String profile = (String) propertySource.getProperty("spring.config.activate.on-profile");
            
            assertThat(profile).isEqualTo("local");
        }
    }

    @Nested
    @DisplayName("application-dev.yaml 검증")
    class ApplicationDevYamlTests {

        @Test
        @DisplayName("application-dev.yaml 파일이 존재하고 파싱 가능하다")
        void applicationDevYamlExistsAndParseable() {
            assertThatCode(() -> {
                ClassPathResource resource = new ClassPathResource("application-dev.yaml");
                assertThat(resource.exists()).isTrue();
                
                YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
                List<PropertySource<?>> propertySources = loader.load("application-dev", resource);
                
                assertThat(propertySources).isNotEmpty();
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("application-dev.yaml에 AWS Secrets Manager import가 설정되어 있다")
        void awsSecretsManagerImportIsConfigured() throws IOException {
            ClassPathResource resource = new ClassPathResource("application-dev.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application-dev", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            String importConfig = (String) propertySource.getProperty("spring.config.import");
            
            assertThat(importConfig).isNotNull();
            assertThat(importConfig).startsWith("aws-secretsmanager:");
            assertThat(importConfig).contains("arn:aws:secretsmanager:");
        }

        @Test
        @DisplayName("application-dev.yaml에 datasource 설정이 정의되어 있다")
        void datasourcePropertiesAreDefined() throws IOException {
            ClassPathResource resource = new ClassPathResource("application-dev.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application-dev", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            
            assertThat(propertySource.getProperty("spring.datasource.driver-class-name")).isNotNull();
            assertThat(propertySource.getProperty("spring.datasource.url")).isNotNull();
            assertThat(propertySource.getProperty("spring.datasource.username")).isNotNull();
            assertThat(propertySource.getProperty("spring.datasource.password")).isNotNull();
        }

        @Test
        @DisplayName("application-dev.yaml의 프로파일이 dev이다")
        void profileIsDev() throws IOException {
            ClassPathResource resource = new ClassPathResource("application-dev.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application-dev", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            String profile = (String) propertySource.getProperty("spring.config.activate.on-profile");
            
            assertThat(profile).isEqualTo("dev");
        }

        @Test
        @DisplayName("application-dev.yaml의 datasource URL이 환경변수 참조를 사용한다")
        void datasourceUrlUsesEnvironmentVariable() throws IOException {
            ClassPathResource resource = new ClassPathResource("application-dev.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application-dev", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            String url = (String) propertySource.getProperty("spring.datasource.url");
            
            assertThat(url).contains("${");
            assertThat(url).contains("}");
        }
    }

    @Nested
    @DisplayName("application-test.yaml 검증")
    class ApplicationTestYamlTests {

        @Test
        @DisplayName("application-test.yaml 파일이 존재하고 파싱 가능하다")
        void applicationTestYamlExistsAndParseable() {
            assertThatCode(() -> {
                ClassPathResource resource = new ClassPathResource("application-test.yaml");
                assertThat(resource.exists()).isTrue();
                
                YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
                List<PropertySource<?>> propertySources = loader.load("application-test", resource);
                
                assertThat(propertySources).isNotEmpty();
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("application-test.yaml의 프로파일이 test이다")
        void profileIsTest() throws IOException {
            ClassPathResource resource = new ClassPathResource("application-test.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = loader.load("application-test", resource);
            
            PropertySource<?> propertySource = propertySources.get(0);
            String profile = (String) propertySource.getProperty("spring.config.activate.on-profile");
            
            assertThat(profile).isEqualTo("test");
        }
    }

    @Nested
    @DisplayName("YAML 파일 일관성 검증")
    class YamlConsistencyTests {

        @Test
        @DisplayName("모든 프로파일별 설정 파일의 driver-class-name이 일관되다")
        void driverClassNameIsConsistentAcrossProfiles() throws IOException {
            // Load local profile
            ClassPathResource localResource = new ClassPathResource("application-local.yaml");
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> localProperties = loader.load("application-local", localResource);
            String localDriver = (String) localProperties.get(0).getProperty("spring.datasource.driver-class-name");

            // Load dev profile
            ClassPathResource devResource = new ClassPathResource("application-dev.yaml");
            List<PropertySource<?>> devProperties = loader.load("application-dev", devResource);
            String devDriver = (String) devProperties.get(0).getProperty("spring.datasource.driver-class-name");

            // Both should use MySQL driver
            assertThat(localDriver).isEqualTo("com.mysql.cj.jdbc.Driver");
            assertThat(devDriver).isEqualTo("com.mysql.cj.jdbc.Driver");
            assertThat(localDriver).isEqualTo(devDriver);
        }

        @Test
        @DisplayName("각 프로파일별 설정 파일이 고유한 프로파일을 가진다")
        void eachProfileFileHasUniqueProfile() throws IOException {
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

            ClassPathResource localResource = new ClassPathResource("application-local.yaml");
            List<PropertySource<?>> localProperties = loader.load("application-local", localResource);
            String localProfile = (String) localProperties.get(0).getProperty("spring.config.activate.on-profile");

            ClassPathResource devResource = new ClassPathResource("application-dev.yaml");
            List<PropertySource<?>> devProperties = loader.load("application-dev", devResource);
            String devProfile = (String) devProperties.get(0).getProperty("spring.config.activate.on-profile");

            ClassPathResource testResource = new ClassPathResource("application-test.yaml");
            List<PropertySource<?>> testProperties = loader.load("application-test", testResource);
            String testProfile = (String) testProperties.get(0).getProperty("spring.config.activate.on-profile");

            assertThat(localProfile).isEqualTo("local");
            assertThat(devProfile).isEqualTo("dev");
            assertThat(testProfile).isEqualTo("test");
            
            // Ensure all are different
            assertThat(localProfile).isNotEqualTo(devProfile);
            assertThat(localProfile).isNotEqualTo(testProfile);
            assertThat(devProfile).isNotEqualTo(testProfile);
        }
    }
}