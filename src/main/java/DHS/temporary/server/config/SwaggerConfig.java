package DHS.temporary.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title("도회선 임시서버 API")
            .description("swagger ui")
            .version("1.0.0");
    }

}
