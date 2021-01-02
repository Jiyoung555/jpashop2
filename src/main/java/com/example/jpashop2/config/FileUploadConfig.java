package com.example.jpashop2.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
가상 url 주소 사용할 것임
→ url은 인터넷에서 접근하는 주소일 뿐
→ <img src="/uploads/마크1.jpg"> 이거는 가상 경로임

=> 이를, 실제 c 드라이브 속 로컬 경로와 연결시키자
*/
@Configuration
public class FileUploadConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //2.ITEM 가상경로 설정
        String uploadPath2 = "C:\\JPASHOP2_ITEM_IMG"; //절대경로
        registry.addResourceHandler("/uploads2/**") // 이 URL 패턴 요청 시
                .addResourceLocations("file:///" + uploadPath2 + "/");

    }

}