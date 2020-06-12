package xyz.fur.skeleton.entity.support;

/**
 * @author Fururur
 * @date 2020-03-20-9:44
 */
public class TokenConstant {
    // jwt 加密私钥
    public static final String SK = "C7F0F6DB2C92A8DA32C98AC5F5B2560F";
    public static final String TYP = "JWT";
    public static final String ISSUER = "Arc";
    public static final Integer EXPIRED_DAY = 3;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_KEY = "Authorization";
}
