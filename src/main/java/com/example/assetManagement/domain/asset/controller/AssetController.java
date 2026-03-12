package com.example.assetManagement.domain.asset.controller;

import com.example.assetManagement.domain.asset.dto.*;
import com.example.assetManagement.domain.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public String goToAssets(@ModelAttribute("condition") AssetSearchCondition condition,
                             @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {
        Page<AssetListResponse> assetPage = assetService.getAssets(condition, pageable);
        model.addAttribute("assets", assetPage.getContent());
        model.addAttribute("page", assetPage);
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

    @GetMapping("/{assetId}")
    public String goToAssetDetail(@PathVariable("assetId") Long assetId, Model model) {
        AssetDetailResponse asset = assetService.getAsset(assetId);
        model.addAttribute("asset", asset);
        return "asset/detailForm";
    }

    @GetMapping("/{assetId}/edit")
    public String goToModifyAsset(@PathVariable("assetId") Long assetId, Model model) {
        AssetDetailResponse asset = assetService.getAsset(assetId);
        model.addAttribute("asset", asset);
        return "asset/editForm";
    }

    @PutMapping("/{assetId}/edit")
    public String modifyAsset(@PathVariable("assetId") Long assetId, AssetModifyRequest request, RedirectAttributes redirectAttributes) {
        AssetDetailResponse asset = assetService.getAsset(assetId);
        redirectAttributes.addFlashAttribute("message", "수정되었습니다.");
        return "redirect:/assets/{id}"; // 스프링이 자동으로 {id}를 치환해줍니다.
    }

    @PostMapping("/{assetId}/delete")
    public String deleteAsset(@PathVariable("assetId") Long assetId,
                              @ModelAttribute("condition") AssetSearchCondition condition,
                              RedirectAttributes redirectAttributes) {

        assetService.deleteAsset(assetId);
        redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
        return UriComponentsBuilder.fromPath("redirect:/assets")
                .queryParam("q", condition.getQ())
                .queryParam("category", condition.getCategory())
                .queryParam("status", condition.getStatus())
                .build()
                .toUriString();
    }

}
