package com.example.jpashop2.config;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//(Mustache 템플릿 엔진을 사용해서) 템플릿을 생성한다.
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        MustacheViewResolver mustacheViewResolver = new MustacheViewResolver();//import org.springframework.boot.web.servlet.view.MustacheViewResolver;
        mustacheViewResolver.setCharset("UTF-8");
        mustacheViewResolver.setContentType("text/html; charset=UTF-8");
        mustacheViewResolver.setPrefix("classpath:/templates/"); // Prefix 설정
        mustacheViewResolver.setSuffix(".mustache"); // Suffix 설정
        registry.viewResolver(mustacheViewResolver); // 위에서 생성한 Mustache 리졸버를 적용
    }

}