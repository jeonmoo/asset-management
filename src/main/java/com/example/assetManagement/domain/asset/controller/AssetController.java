package com.example.assetManagement.domain.asset.controller;

import com.example.assetManagement.domain.asset.dto.*;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignFormResponse;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignRequest;
import com.example.assetManagement.domain.asset.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String assetNewForm(Model model) {
        model.addAttribute("asset", new AssetModifyRequest());
        return "asset/newForm";
    }

    @PostMapping
    public String createAsset(@Valid @ModelAttribute("asset") AssetCreateRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "asset/newForm";
        }
        assetService.registerAsset(request);
        redirectAttributes.addFlashAttribute("message", "성공적으로 등록되었습니다.");
        return "redirect:/assets";
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

    @PostMapping("/{assetId}/edit")
    public String modifyAsset(@PathVariable("assetId") Long assetId, @Valid @ModelAttribute("asset") AssetModifyRequest request,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("assetId", assetId);
            return "asset/editForm";
        }

        assetService.modifyAsset(assetId, request);
        return "redirect:/assets/{assetId}";
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

    @GetMapping("/{assetId}/assign")
    public String goToAssignForm(@PathVariable("assetId") Long assetId, Model model) {
        AssetAssignFormResponse asset = assetService.getAssetWithAssignForm(assetId);
        model.addAttribute("asset", asset);
        return "asset/assign/assetAssignForm";
    }

    @PostMapping("/{assetId}/assign")
    public String assignAsset(@PathVariable("assetId") Long assetId, AssetAssignRequest request, RedirectAttributes redirectAttributes) {
        assetService.assignAsset(assetId, request);
        redirectAttributes.addFlashAttribute("message", "성공적으로 할당 되었습니다.");
        return "redirect:/assets";
    }

    @PostMapping("/{assetId}/return")
    public String returnAsset(@PathVariable("assetId") Long assetId, RedirectAttributes redirectAttributes) {
        assetService.returnAsset(assetId);
        redirectAttributes.addFlashAttribute("message", "성공적으로 반납 되었습니다.");
        return "redirect:/assets/{assetId}";
    }
}
