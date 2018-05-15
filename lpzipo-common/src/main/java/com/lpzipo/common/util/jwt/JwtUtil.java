package com.lpzipo.common.util.jwt;

import com.lpzipo.common.util.RSASecret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import java.util.Map;

/**
 * JWT 工具类
 */
public class JwtUtil {


    /**
     * 通过指定文件内的 私钥生成 token
     * @param claims
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String generateToken(Map<String, Object> claims,String fileName) throws Exception {
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(DateTime.now().plus(System.currentTimeMillis()).toDate())
                .signWith(SignatureAlgorithm.RS256, RSASecret.getPrivateKey(fileName)).compact();
        return token;

    }

    /**
     * 通过私钥生成 token
     * @param claims
     * @param priKey
     * @return
     * @throws Exception
     */
    public static String generateToken(Map<String, Object> claims,byte[] priKey) throws Exception {
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(DateTime.now().plus(System.currentTimeMillis()).toDate())
                .signWith(SignatureAlgorithm.RS256, RSASecret.getPrivateKey(priKey)).compact();
        return token;
    }

    /**
     * 公钥解析 Token
     * @param token
     * @param pubKeyPath
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(RSASecret.getPublicKey(pubKeyPath)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 公钥解析 Token
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(RSASecret.getPublicKey(pubKey)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取Token 具体信息
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public  static Object parserTokenInfo(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token,pubKey);
        return claimsJws.getBody();
    }




}
