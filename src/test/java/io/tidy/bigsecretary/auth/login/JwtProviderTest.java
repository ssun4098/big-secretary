package io.tidy.bigsecretary.auth.login;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TimeZone;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JwtProviderTest {

  private JwtProvider jwtProvider = new JwtProvider("test_keyasdasdasdqweadasdasdasdqweadasdasseqweasdads", 500000L);

    @BeforeAll
    static void setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    void getUuidByToken() {
        String uuid = UUID.randomUUID().toString();
        String token = jwtProvider.createToken(uuid);
        String id = jwtProvider.getUuidByToken(token);
        assertNotNull(id);
        assertEquals(uuid, id);
    }
}