package com.sistemaponto.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptionMethod(Exception ex, WebRequest requset, HttpServletResponse res) {

		ModelAndView mav = new ModelAndView();
		
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("Exception", ex.getLocalizedMessage());     
        mav.addObject(ex.getClass().getCanonicalName());
        return mav;          
    }
}
