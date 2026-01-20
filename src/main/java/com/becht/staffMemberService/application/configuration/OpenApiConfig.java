package com.becht.staffMemberService.application.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    /**
    *   Automatically generates api
    * */
    @Bean
    public OpenAPI heroApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Staff Member Service API")
                        .version("1.0")
                        .description("REST API for managing staff members"));
    }
}
