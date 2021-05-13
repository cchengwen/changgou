package com.changgou.filter;

/**
 * 不需要认证就能访问的路径校验
 * @Author WEN
 * @Date 2021/1/19 23:07
 */
public class URLFilter {

    /**
     * 此处配置的是无需拦截认证的请求路径，与网关yml文件路径有关
     */
    private static final String allurl = "/auth/user/login,/api/user/add";

    /**
     * 校验当前访问路径是否需要验证权限
     * 如果不需要验证：false
     *  需要验证：true
     * @return
     */
    public static boolean hasAuthorize(String url){
        // 不需要拦截的URL
        String[] urls = allurl.split(",");
        for (String uri : urls) {
            if (url.equals(uri)){
                return false;
            }
        }
        return true;
    }
}
