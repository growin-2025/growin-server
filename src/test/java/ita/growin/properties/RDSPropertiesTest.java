package ita.growin.properties;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

// dev profile에서 db 연결 X, 그저 aws secrets manager에서 값이 import되는지만 확인
@ActiveProfiles("dev") //
@SpringBootTest(classes = RDSPropertiesTest.TestConfig.class)
public class RDSPropertiesTest {

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Test
    @DisplayName("aws secret manager에서 import한 arn에서 username, password를 가져옵니다.")
    void get_username_and_password() {
        assertThat(username).isNotNull();
        assertThat(password).isNotNull();
    }

    @SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
    static class TestConfig {

    }
}
