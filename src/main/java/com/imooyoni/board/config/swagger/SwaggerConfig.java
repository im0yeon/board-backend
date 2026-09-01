package com.imooyoni.board.config.swagger;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Value("${server.servlet.context-path:}")
	private String contextPath;

	@Bean
	public OpenAPI openAPI() {
		Server server = new Server()
				.url(contextPath.isBlank() ? "/" : contextPath)
				.description("현재 실행 중인 서버");

		Info info = new Info()
				.title("게시판 API")
				.description("게시글과 댓글을 관리하는 API 문서")
				.version("v1");

		return new OpenAPI()
				.info(info)
				.servers(List.of(server));
	}
}
