package com.suddenly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
//         添加请求参数，我们这里把token作为请求头部参数传入后端
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder
                .name("token")
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        parameters.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot Swagger2")
                .description("详细信息")
                .version("1.0")
                .build();
    }

}




//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//        .apiInfo(apiInfo())
//        .select()
//        .apis(RequestHandlerSelectors.basePackage("com.suddenly.controller")) // controller包所在的位置
//        .apis(RequestHandlerSelectors.any()) // 扫描所有的位置
//        .paths(PathSelectors.any())
//        .build();
//    }
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//            .title("SpringBoot Swagger2")
//            .description("详细信息")
//            .version("1.0")
//            .build();
//    }












//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .pathMapping("/")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.nvn.controller"))
//                .paths(PathSelectors.any())
//                .build().apiInfo(new ApiInfoBuilder()
//                        .title("Swagger")
//                        .description("详细信息")
//                        .version("9.0")
//                        .contact(new Contact("suddenly","blog.csdn.net","2676178516@qq.com"))
//                        .license("The Apache License")
//                        .licenseUrl("http://www.baidu.com")
//                        .build());
//    }








//这里提供一个配置类，首先通过@EnableSwagger2注解启用Swagger2，然后配置一个Docket Bean，这个Bean中，配置映射路径和要扫描的接口的位置，在apiInfo中，主要配置一下Swagger2文档网站的信息，例如网站的title，网站的描述，联系人的信息，使用的协议等等。
//
//        如此，Swagger2就算配置成功了，非常方便。
//
//        此时启动项目，输入http://localhost:8080/swagger-ui.html  能够看到如下页面，说明已经配置成功了：