package com.houseagent.config.swaggercfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //访问：http://localhost:9000/swagger-ui.html
    @Bean //配置docket以配置Swagger具体参数
    public Docket docket() {
     return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
            .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors用来配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.houseagent.controller"))
            .paths(PathSelectors.any())//any是都要扫描到
            .build();
}

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
        return new ApiInfo(
                "房产中介管理系统API文档", // 标题
                "便于查看接口信息", // 描述
                "v1.0", // 版本
                "https://gitee.com/jimouchen/house-agent", // 组织链接
                contact, // 联系人信息
                "Apache 2.0 许可", // 许可
                "http://www.apache.org/licenses/LICENSE-2.0", // 许可连接
                new ArrayList<>()// 扩展
        );
    }


}

