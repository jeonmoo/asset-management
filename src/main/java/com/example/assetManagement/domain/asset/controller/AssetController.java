package com.example.assetManagement.domain.asset.controller;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.dto.AssetSearchCondition;
import com.example.assetManagement.domain.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public String goToAssets(@ModelAttribute AssetSearchCondition condition,
                             @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {
        assetService.getAssets(condition, pageable);
        return "asset/assetList";
    }

    @GetMapping("/new")
    public String assetNewForm() {
        return "asset/newForm";
    }

    @PostMapping
    public String createAsset(@ModelAttribute AssetCreateRequest request) {
        assetService.registerAsset(request);

        return "asset/assetList";
    }
}
