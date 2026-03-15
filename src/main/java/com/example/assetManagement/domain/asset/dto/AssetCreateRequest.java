package com.example.assetManagement.domain.asset.dto;

import com.example.assetManagement.common.annotation.UniqueAssetNo;
import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AssetCreateRequest {

    @NotBlank(message = "자산 번호는 필수입니다.")
    @Size(max = 255, message = "자산 번호는 255자 이하로 입력해주세요.")
    private String assetNo;

    @NotBlank(message = "자산명은 필수입니다.")
    @Size(max = 255, message = "자산명은 255자 이하로 입력해주세요.")
    private String name;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

    @Size(max = 255, message = "시리얼 번호는 255자 이하로 입력해주세요.")
    private String serialNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchasedAt;

    @Size(max = 500, message = "메모는 500자 이하로 작성해주세요.")
    private String memo;
}
