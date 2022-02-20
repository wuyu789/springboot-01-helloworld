package com.wuyu.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Author:      wy
 * Create Date: 2022/2/20
 * Create Time: 0:27
 * Description:
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket docket(Environment environment) {

        // 设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev", "test");

        boolean swaggerFlag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerFlag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wuyu.controller")) // 指定需要扫描的包路径，只有此路径下的 Controller 类才会自动生成
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger2API")
                .description("供公司内部前后端开发人员交流使用")
                .version("1.0.0")
                .license("公司内部使用")
                .contact(new Contact("yhy", "http://www.jet-china.com.cn", "youheyu@jet-china.com.cn"))
                .build();
    }

}