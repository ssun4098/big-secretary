package io.tidy.bigsecretary.briefcase.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record BriefcaseCreateReq(@NotBlank @Length(max = 512) String name) {
    public BriefcaseCreate toService() {
        return new BriefcaseCreate(this.name);
    }
}
