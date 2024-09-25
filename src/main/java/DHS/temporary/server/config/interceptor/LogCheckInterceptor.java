package DHS.temporary.server.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LogCheckInterceptor implements HandlerInterceptor {

	private final UUID uuid = UUID.randomUUID();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("-------------------------------------------------------------------------------------");
		log.info("RequestId: {}, Method: {}, requestURI: {}", uuid, request.getMethod(), request.getRequestURI());
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		log.info("RequestId: {}, Method: {}, requestURI: {}, with status {}", uuid, request.getMethod(), request.getRequestURI(), response.getStatus());
		if (ex != null) {
			log.error("예외발생: {}", ex.getMessage());
		}
		log.info("-------------------------------------------------------------------------------------");
	}
}
