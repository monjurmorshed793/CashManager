package org.cash.manager.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String EXPANSE = "ROLE_EXPANSE";

    public static final String DEPOSIT = "ROLE_DEPOSIT";

    private AuthoritiesConstants() {
    }
}
