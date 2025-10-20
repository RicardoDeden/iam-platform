package com.example.user_management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@SpringBootTest
@ActiveProfiles("test")
class UserManagementApplicationTests {

    @MockBean
    JwtDecoder jwtDecoder; // mocked to avoid real JWKS calls during tests

	@Test
	void contextLoads() {
	}

}
