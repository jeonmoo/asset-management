package com.example.assetManagement.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(AssetNotFoundException.class)
    public String handleAssetNotFound(AssetNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/asset-not-found";
    }
}
