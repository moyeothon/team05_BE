package projects.moyethon.global;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Server testServer = new Server()
                .url("https://junyeongan.store")
                .description("배포 서버");

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("로컬 테스트");

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(List.of(localServer, testServer));
    }

    private Info apiInfo() {
        return new Info()
                .title("Mute API")
                .description("Mute API 명세서입니다.")
                .version("1.0.0");
    }
}
