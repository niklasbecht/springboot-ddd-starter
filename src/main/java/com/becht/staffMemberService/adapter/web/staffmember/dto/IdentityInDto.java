package com.becht.staffMemberService.adapter.web.staffmember.dto;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class IdentityInDto {
    @NonNull private String firstName;
    @NonNull private String lastName;
}
