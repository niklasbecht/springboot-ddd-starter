package com.becht.staffMemberService.domain.model.staffmember;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StaffMember {
    @NonNull private String displayName;
    @NonNull private Identity identity;
    @NonNull private LocalDate birthday;
    @NonNull private List<Skill> skills;

    @Getter
    public enum Skill {
        JAVA("java"),
        KOTLIN("kotlin"),
        ENGINEERING("engineering"),
        DESIGNPATTERNS("designpatterns"),
        DDD("ddd");

        private final String value;

        Skill(String value) {
            this.value = value;
        }

    }
}
