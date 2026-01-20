package com.becht.staffMemberService.adapter.web.staffmember.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StaffMemberInDto {
    @NonNull private String name;
    @NonNull private IdentityInDto identity;
    @NonNull private LocalDate birthday;
    @NonNull private List<String> skills;
}


