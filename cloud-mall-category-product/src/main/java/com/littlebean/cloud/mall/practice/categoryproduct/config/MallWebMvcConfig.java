package com.littlebean.cloud.mall.practice.categoryproduct.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.littlebean.cloud.mall.practice.categoryproduct.common.ProductConstant.FILE_UPLOAD_DIR;

/**
 * 描述：     配置地址映射
 */
@Configuration
public class MallWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + FILE_UPLOAD_DIR);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}
