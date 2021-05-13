package entity;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * token 令牌解析
 * @Author WEN
 * @Date 2021/1/17 22:10
 */
@Component
public class TokenDecode {
    // 公钥
    private static final String PUBLIC_KEY = "public.key";
    private static String publickey = "";

    /**
     * 获取非对称加密公钥
     * @return 公钥 key
     */
    public static String getpubkey(){
        if (!StringUtils.isEmpty(publickey)){
            return publickey;
        }
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            publickey = br.lines().collect(Collectors.joining("\n"));
            return publickey;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取令牌数据
     * @param token 令牌
     * @return
     */
    public static Map<String, String> dcodeToken(String token){
        // 校验 Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(getpubkey()));

        // 获取Jwt原始内容
        String claims = jwt.getClaims();
        return JSON.parseObject(claims, Map.class);
    }

    public static Map<String, String> getUserInfo(){
        // 获取授权信息
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        // 令牌解码
        return dcodeToken(details.getTokenValue());
    }

}
