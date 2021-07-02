package cn.enilu.flash.schedule.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * swagger在线文档配置<br>
 * 项目启动后可通过地址：http://host:ip/swagger-ui.html 查看在线文档
 *
 * @author enilu
 * @version 2018-07-24
 */

@EnableOpenApi
@Configuration
public class SwaggerConfiguration {


    @Bean
    public Docket frontApi() {
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", In.HEADER.toValue());
        List<SecurityScheme> headers =  Collections.singletonList(apiKey);

        return new Docket(DocumentationType.OAS_30)
                //是否开启，根据环境配置
                .enable(true)
//                .groupName("api")
                .apiInfo(frontApiInfo())
                .select()

                //指定扫描的包
                .apis(RequestHandlerSelectors.basePackage("cn.enilu.flash.schedule.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(headers);
    }

    /**
     * 前台API信息
     */
    private ApiInfo frontApiInfo() {
        return new ApiInfoBuilder()
                .title("flash-schedule api")
                .description("定时任务")
                .version("v1.0")
                .license("MIT")
                .contact(    //添加开发者的一些信息
                        new Contact("enilu", "http://www.enilu.cn",
                                "eniluzt@qq.com"))
                .build();
    }
}
