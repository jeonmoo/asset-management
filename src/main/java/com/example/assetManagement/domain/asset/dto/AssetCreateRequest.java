package com.example.assetManagement.domain.asset.dto;

import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetCreateRequest {

    @NotBlank(message = "자산 번호는 필수입니다.")
    private String assetNo;

    @NotBlank(message = "자산명은 필수입니다.")
    private String name;

    @NotNull(message = "카테고리는 필수입니다.")
    private Category category;

    @NotNull(message = "상태는 필수입니다.")
    private AssetStatus status;

    private String serialNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchasedAt;

    private String memo;
}
