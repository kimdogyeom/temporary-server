package DHS.temporary.server.config;

import DHS.temporary.server.config.interceptor.LogCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")  // 모든 경로에 대해 CORS 허용
			.allowedOrigins("http://10.101.88.53:3000")  // 허용할 출처 (Origin)
			.allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
			.allowedHeaders("*")  // 모든 헤더 허용
			.allowCredentials(true);  // 자격 증명(쿠키, 인증 정보 등) 허용
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogCheckInterceptor());
	}
}
