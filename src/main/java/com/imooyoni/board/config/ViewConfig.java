package com.imooyoni.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 화면 확인용 임시 매핑 - 실제 라우팅은 컨트롤러로 대체
@Configuration
public class ViewConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/guide/alerts").setViewName("guide/alerts");
		registry.addViewController("/guide/styleguide").setViewName("guide/styleguide");
	}
}
