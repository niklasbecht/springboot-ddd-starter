package com.becht.staffMemberService.mapper.staffmember;


import com.becht.staffMemberService.adapter.web.staffmember.dto.IdentityInDto;
import com.becht.staffMemberService.adapter.web.staffmember.dto.StaffMemberInDto;
import com.becht.staffMemberService.adapter.web.staffmember.dto.StaffMemberOutDto;
import com.becht.staffMemberService.domain.model.staffmember.StaffMember;
import com.becht.staffMemberService.domain.model.staffmember.Identity;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StaffMemberMapperTest {

    StaffMember staffMember =  StaffMember.builder()
            .displayName("Testman")
            .birthday(LocalDate.parse("2000-01-01"))
            .identity(Identity.builder().firstName("Tim").lastName("Tester").build())
            .skills(List.of(StaffMember.Skill.DDD, StaffMember.Skill.ENGINEERING)).build();
    StaffMemberOutDto staffMemberOutDto = StaffMemberOutDto.builder()
            .name("Testman")
            .birthday(LocalDate.parse("2000-01-01"))
            .identity("Tim Tester")
            .skills(List.of("DDD","ENGINEERING")).build();
    StaffMemberInDto staffMemberInDto = StaffMemberInDto.builder()
            .name("Testman")
            .birthday(LocalDate.parse("2000-01-01"))
            .identity(IdentityInDto.builder().firstName("Tim").lastName("Tester").build())
            .skills(List.of("DDD","ENGINEERING")).build();
    @Test
    void toOutDto() {
        val generatedDto= StaffMemberMapper.toOutDto(staffMember);
        assertEquals(staffMemberOutDto,generatedDto);
    }

    @Test
    void toEntity() {
        val generatedHero= StaffMemberMapper.toEntity(staffMemberInDto);
        assertEquals(staffMember,generatedHero);
    }
}