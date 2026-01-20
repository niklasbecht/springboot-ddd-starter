package com.becht.staffMemberService.mapper.staffmember;


import com.becht.staffMemberService.adapter.web.staffmember.dto.StaffMemberInDto;
import com.becht.staffMemberService.adapter.web.staffmember.dto.StaffMemberOutDto;
import com.becht.staffMemberService.domain.model.staffmember.StaffMember;
import com.becht.staffMemberService.domain.model.staffmember.Identity;
import lombok.NonNull;

public class StaffMemberMapper {

    public static StaffMemberOutDto toOutDto(StaffMember staffMember) {
        return StaffMemberOutDto.builder()
                .name(staffMember.getDisplayName())
                .identity(staffMember.getIdentity().getFirstName() + " " + staffMember.getIdentity().getLastName())
                .birthday(staffMember.getBirthday())
                .skills(staffMember.getSkills().stream().map(Enum::name).toList())
                .build();
    }

    public static StaffMember toEntity(@NonNull StaffMemberInDto dto) {

        return StaffMember.builder()
                .displayName(dto.getName())
                .birthday(dto.getBirthday())
                .skills(dto.getSkills().stream().map(StaffMember.Skill::valueOf).toList())
                .identity(Identity.builder()
                        .firstName(dto.getIdentity().getFirstName())
                        .lastName(dto.getIdentity().getLastName())
                        .build())
                .build();
    }
}
