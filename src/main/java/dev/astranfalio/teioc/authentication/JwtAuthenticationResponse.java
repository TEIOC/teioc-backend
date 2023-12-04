package dev.astranfalio.teioc.authentication;

public class JwtAuthenticationResponse {

    private String accessToken;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
