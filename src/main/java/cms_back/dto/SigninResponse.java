package cms_back.dto;

import java.time.Instant;

import cms_back.domain.UserRole;

public class SigninResponse {
    private String tokenType;
    private String accessToken;
    private Instant expiresAt;

    private Long userId;
    private String name;
    private String email;
    private UserRole role;

    public SigninResponse(String tokenType, String accessToken, Instant expiresAt,
                          Long userId, String name, String email, UserRole role) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getTokenType() { return tokenType; }
    public String getAccessToken() { return accessToken; }
    public Instant getExpiresAt() { return expiresAt; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
}
