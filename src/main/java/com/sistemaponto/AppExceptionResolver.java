package com.sistemaponto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

@Component
public class AppExceptionResolver extends AbstractHandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(AppExceptionResolver.class.getSimpleName());
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		logger.error("Application error in: [" + ex.getClass().getName() + "]", ex);
        return null;
	}

}
