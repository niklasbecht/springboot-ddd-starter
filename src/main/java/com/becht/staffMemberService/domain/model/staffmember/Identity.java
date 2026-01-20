package com.becht.staffMemberService.domain.model.staffmember;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Identity {
    @NonNull private String firstName;
    @NonNull private String lastName;
}