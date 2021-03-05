package com.example.graduation_design.interceptor;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class LoginConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new AdminInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(
               //添加不拦截路径
                                         "/login",
                                        "/backLogin",
                                        "/backlogined",
                                        "/logined",//登录
                "/user/research",
                "/user/index",
                "/user/allBooks",
                "/user/allBusiness",
                "/user/showBusinessDetail",
                "/user/bookDetail",
                "/user/booksByType",
                "/test",
                                         "/registration",
                                         "/registered",
                                         "/**/*.html",            //html静态资源
                                         "/**/*.js",              //js静态资源
                                         "/**/*.css",             //css静态资源
                                         "/**/*.woff",
                                         "/**/*.ttf",
                "/**/*.jpg",
                "/**/*.map",
                "/**/*.eot",
                "/**/*.svg",
                "/**/*.woff2",
                "/**/*.png",
                "/**/**/*.css",
                "/**/**/*,js",
                "/static/**"
                                         );    
    }
}