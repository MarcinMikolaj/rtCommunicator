package project.rtc.infrastructure.utils.token;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private final String secure = "co3js8cjwh33Su3nx927dns92mvheoskwhdwhwndh3946dhb2w8ck30wh2cbxHh2oShwhsGjrHwoB83w6dhIdbwh3gdw83H63h";

    @Test
    void createdJWTSignatureShouldBeEqual() {
        String token = JwtTokenProvider.create(secure
                , "ewelina23@gmail.com"
                , new Date(1661100000)
                , new Date(1661100000 + 3600000));
        assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJld2VsaW5hMjNAZ21haWwuY29tIiwiaWF0IjoxNjYxMTAwLCJleHAiOjE2NjQ3MDB9.kSxdp1EJrZLx4yvAyNRIjgRGDD4_NmYCwDOvFgTN_ejYskAM9SvAwdyzsBe2YjH-gNi2VyyvxvaNX35tM1_zww",
                token);
    }

    @Test
    void getTokenIssuedAt() {
        Date issuedAt = new Date(1661100000);
        String token = JwtTokenProvider.create(secure
                , "ewelina23@gmail.com"
                , new Date(1661100000)
                , new Date(System.currentTimeMillis() + 3600000));
        assertEquals(JwtTokenProvider.getTokenIssuedAt(secure, token), issuedAt);
    }

    @Test
    void createdJWTSubjectShouldBeEqual() {
        String token = JwtTokenProvider.create(secure, "ewelina23@gmail.com", new Date(1661100000), new Date(System.currentTimeMillis() + 3600000));
        assertEquals(JwtTokenProvider.getTokenSubject(secure, token), "ewelina23@gmail.com");
    }

}