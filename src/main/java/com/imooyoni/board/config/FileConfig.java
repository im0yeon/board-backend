package com.imooyoni.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.imooyoni.board.service.file.FileStorage;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FileConfig implements WebMvcConfigurer {

	private final FileStorage fileStorage;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String location = fileStorage.getBaseDir().toUri().toString();
		if (!location.endsWith("/")) {
			location += "/";
		}

		registry.addResourceHandler(FileStorage.URL_PREFIX + "/**")
				.addResourceLocations(location);
	}
}
