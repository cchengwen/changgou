package com.changgou.filter;

import com.changgou.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器，实现用户权限鉴别（校验）
 * @Author WEN
 * @Date 2021/1/13 23:23
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //令牌的名字
    private static final String AUTHORIZE_TOKEN = "Authorization";

    /**
     * 全局拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 用户如果是登录或者一些不需要做权限认证的请求，直接放行
        String uri = request.getURI().getPath();
        if (!URLFilter.hasAuthorize(uri)){
            return chain.filter(exchange);
        }


        // 获取用户令牌信息
        // 1) 从头文件中获取令牌
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        // boolean true:表示令牌在头文件中，false：表示令牌不在头文件中 -> 需要将令牌封装到头文件中，再传递给其他微服务
        boolean hasToken = true;

        if (StringUtils.isEmpty(token)){
            // 2）如果头文件中没有，则从参数中的第一个参数获取
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            hasToken = false;
        }
        // 3) 从cookie中获取
        if (StringUtils.isEmpty(token)){
            HttpCookie httpCookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (httpCookie != null){
                token = httpCookie.getValue();
            }
        }
        // 如果没有令牌，则拦截
        if (StringUtils.isEmpty(token)){
            // 设置没有权限的状态码   401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        }
        // 判断令牌是否为空，如果不为空，将令牌放到头文件中   放行

        // 如果有令牌，则校验令牌是否有效
//        try {
////            JwtUtil.parseJWT(token);
//        } catch (Exception e) {
//            logger.error("token令牌校验出错：" + e);
//            // 无效则拦截
//            // 设置没有权限的状态码   401
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            // 响应空数据
//            return response.setComplete();
//        }

        // 令牌为空，则不允许访问，直接拦截
        if (StringUtils.isEmpty(token)){
            // 设置没有权限的状态码   401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        }else {
            // 将令牌封装到头文件中
            if (!hasToken){
                // 判断当前令牌是否有bearer前缀，如果没有，则添加前缀 bearer
                if (!token.startsWith("bearer ") && !token.startsWith("Bearer ")){
                    token = "bearer "+token;
                }
                request.mutate().header(AUTHORIZE_TOKEN, token);
            }
        }
        // 有效则放行
        return chain.filter(exchange);
    }

    /**
     * 排序，越小越先执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
