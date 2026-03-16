package com.example.assetManagement.domain.asset.dto.assign;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PUBLIC)
public class AssetAssignRequest {

    private Long id;

    @NotBlank(message = "할당자 이름은 필수입니다.")
    @Size(max = 50, message = "이름은 50자 이내로 입력해주세요.")
    private String assigneeName;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String assigneeEmail;

    @Size(max = 100, message = "부서명은 100자 이내로 입력해주세요.")
    private String department;

    @Size(max = 500, message = "비고는 500자 이내로 입력해주세요.")
    private String note;
}
