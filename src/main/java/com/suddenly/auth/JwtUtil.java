package com.suddenly.auth;

import com.alibaba.fastjson.JSONObject;
import com.suddenly.exception.CustomizeException;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.codec.Base64;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import static com.suddenly.responseResult.ResultEnum.TOKEN_PARSE_FAILURE;


public class JwtUtil {

    private static Logger logger = LogManager.getLogger(JwtUtil.class);

    /**
     * 生成JWT Token(加密)
     */
    public static String generateToken(String payloadStr) {
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        byte[] encodeKey = Base64.decode("suddenly.com");
        Key key = new SecretKeySpec(encodeKey, 0,encodeKey.length, "AES");
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setPayload(payloadStr)
                .signWith(algorithm, key)
                .compact();
        return token;
    }

    /**
     * 解析 Token
     */
    public static Claims parseJwtToken(String token) throws CustomizeException {
        String[] splitToken = token.split("\\.");
        if (splitToken.length == 3) {
//			String header = splitToken[0];
//			String poyload = splitToken[1];
            String sign = splitToken[2];
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("suddenly.com")).parseClaimsJws(token).getBody();
            String newSign = generateToken(JSONObject.toJSONString(claims)).split("\\.")[2];
            if (!newSign.equals(sign)) {
                logger.error("token解析失败");
                throw new CustomizeException(TOKEN_PARSE_FAILURE);
            }
            return claims;
        } else {
            return null;
        }
    }


}
