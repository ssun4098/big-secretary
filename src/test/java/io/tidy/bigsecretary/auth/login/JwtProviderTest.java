package io.tidy.bigsecretary.auth.login;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TimeZone;
import java.util.UUID;

import io.tidy.bigsecretary.auth.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JwtProviderTest {

  private JwtProvider jwtProvider = new JwtProvider("test_keyasdasdasdqweadasdasdasdqweadasdasseqweasdads");

    @BeforeAll
    static void setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    void getUuidByToken() {
        String uuid = UUID.randomUUID().toString();
        String token = jwtProvider.createToken(uuid, "ROLE_USER", 500000L);
        String id = jwtProvider.getUuidByToken(token);
        assertNotNull(id);
        assertEquals(uuid, id);
    }
}