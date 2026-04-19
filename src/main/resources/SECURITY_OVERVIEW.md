# Security & Authentication Overview

This project uses **Spring Security** with **JWT (JSON Web Token)** for stateless authentication and authorization.

## Key Components

### 1. JwtAuthenticationFilter
- Intercepts incoming HTTP requests.
- Extracts JWT from the `Authorization` header.
- Validates the token using `JwtService`.
- Loads user details and sets authentication in the security context if the token is valid.

### 2. JwtService
- Generates JWT tokens after successful login.
- Extracts username/email from tokens.
- Validates tokens (checks signature, expiration, and subject).

### 3. CustomUserDetailsService & CustomUserDetails
- **CustomUserDetailsService** loads user data from the database for authentication (by email).
- **CustomUserDetails** wraps the user entity for Spring Security compatibility and provides user authorities (roles).
- The `getAuthorities()` method in `CustomUserDetails` returns the user's role as `ROLE_<PREMIUM_LEVEL>`, e.g., `ROLE_PRO` or `ROLE_BASIC`, based on the user's premium status.
- These roles are used by Spring Security to control access to endpoints (e.g., restricting `/pro/**` to `PRO` users).
- This mechanism is used automatically by Spring Security during authentication and authorization checks.

### 4. SecurityConfigure
- Configures security filter chain.
- Registers `JwtAuthenticationFilter` before the default authentication filter.
- Sets endpoints under `/auth/**` as public (no authentication required).
- Restricts `/pro/**` endpoints to users with `PRO` or `PREMIUM` roles.
- All other endpoints require authentication.
- Uses stateless session management (no server-side sessions).

### 5. AuthController
- `/auth/users/signup`: Registers a new user (no JWT issued).
- `/auth/users/login`: Authenticates user and returns a JWT token.

### 6. AuthenticationManager Bean
- **Definition:**
  ```java
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
  }
  ```
- **Purpose:** Provides the `AuthenticationManager` used by Spring Security to authenticate user credentials (e.g., during login).
- **Input:** The `AuthenticationConfiguration` parameter is auto-injected by Spring Boot. It contains the security configuration and is managed by the framework.
- **Usage:**
  - Used in your service layer (e.g., `UserServiceImpl`) to authenticate login requests:
    ```java
    Authentication auth = manager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
    );
    ```
  - If authentication is successful, a JWT is generated and returned to the client.
- **How it works:**
  - Spring Boot auto-configures `AuthenticationConfiguration` with your custom `UserDetailsService` and `PasswordEncoder`.
  - The bean exposes the `AuthenticationManager` for use in your application wherever authentication is needed.

### JwtAuthenticationFilter Placement
- The following line in `SecurityConfigure`:
  ```java
  .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  ```
- **Meaning:**
  - Registers your custom `JwtAuthenticationFilter` in the Spring Security filter chain.
  - Ensures that JWT validation and authentication happen **before** the standard username/password authentication filter.
  - This allows requests with valid JWTs to be authenticated without needing to submit credentials again.
  - If the JWT is valid, the user is authenticated for the request; if not, the request proceeds to other filters (like login).
- **Where:**
  - This line is inside the `securityFilterChain` bean method in your `SecurityConfigure` class.

## JWT Flow
1. **Login**: User sends credentials to `/auth/users/login`. If valid, receives a JWT.
2. **Subsequent Requests**: Client sends JWT in the `Authorization: Bearer <token>` header.
3. **Filter**: `JwtAuthenticationFilter` validates the token and sets authentication.
4. **Access Control**: Security rules in `SecurityConfigure` determine access based on roles and authentication.

## Token Expiry and Claims Extraction

#### isTokenExpired(String token)
- **Purpose:** Checks if the JWT token has expired.
- **How it works:**
  - Calls `extractClaim(token, Claims::getExpiration)` to get the token's expiration date.
  - Compares the expiration date with the current date (`new Date()`).
  - Returns `true` if the token is expired, otherwise `false`.
- **Production Note:**
  - The use of `new Date()` checks against the server's current time. For production, ensure your server's clock is synchronized (e.g., using NTP) to avoid token validation issues. If you need to support distributed systems or time zones, consider using a time service or `Instant.now()` from Java's time API for more robust handling.

#### <T> T extractClaim(String token, Function<Claims, T> resolver)
- **Purpose:** Extracts any specific claim from the JWT token using a resolver function.
- **How it works:**
  - Parses the JWT using the signing key to get the `Claims` object (the payload of the token).
  - Applies the provided resolver function to extract a specific value (e.g., subject, expiration, custom claims like role).
  - Returns the extracted value.
- **Claims Explained:**
  - Claims are the payload data in a JWT. Standard claims include:
    - `sub` (subject, usually the user's email or ID)
    - `exp` (expiration time)
    - `iat` (issued at)
  - Custom claims can be added, such as `role`, `premium`, etc.
  - Claims are used to carry user identity and authorization data securely between client and server.

## Notes
- No cookies are used; JWT is stored client-side (e.g., localStorage).
- Passwords are hashed using BCrypt.
- Stateless: Each request must include a valid JWT for protected endpoints.
- User roles (authorities) are dynamically provided by `CustomUserDetails` and used by Spring Security for endpoint protection.

---
For more details, see the code in the `security`, `controller`, and `service` packages.
