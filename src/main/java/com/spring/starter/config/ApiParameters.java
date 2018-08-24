package com.spring.starter.config;

public class ApiParameters {
	 ///jwt//////////
    public static final String URL = "/login";
    public static final String AUTH_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer";
    public static final int JWT_EXPIRATION = 365*24*60*60;
    public static final String JWT_SECRET = "dsflkakw0893209rpoiefjslkdljrsf0980epqdsa";

    public static final int REFRESH_TOKEN_EXPIRATION = 5*365*24*60*60;

    /////////UserRoles
    public static final String ROLE_USER="ROLE_User";
    public static final String ROLE_ADMIN="ROLE_Admin";

}
