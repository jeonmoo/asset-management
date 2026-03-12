package com.example.assetManagement.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public String handleGlobalException(GlobalException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e) {
        return "error/500";
    }
}
