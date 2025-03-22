package io.tidy.bigsecretary.briefcase.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record BriefcaseUpdateReq(@NotBlank @Length(max = 512) String name) {
    public BriefcaseUpdate toService() {
        return new BriefcaseUpdate(this.name);
    }
}
