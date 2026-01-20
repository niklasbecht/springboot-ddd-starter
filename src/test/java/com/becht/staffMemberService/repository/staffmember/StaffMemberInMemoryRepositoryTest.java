package com.becht.staffMemberService.repository.staffmember;

import com.becht.staffMemberService.domain.model.staffmember.StaffMember;
import com.becht.staffMemberService.domain.model.staffmember.Identity;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StaffMemberInMemoryRepositoryTest {

    StaffmemberInMemoryRepository staffmemberInMemoryRepository =new StaffmemberInMemoryRepositoryImpl();
    @Test
    void findAll() {
        val staffMembers= staffmemberInMemoryRepository.findAll();
        assertFalse(staffMembers.isEmpty());
    }

    @Test
    void findByName() {
        val staffMember= staffmemberInMemoryRepository.findByName("superman").get();
        assertEquals("superman", staffMember.getDisplayName());
        assertEquals("clark", staffMember.getIdentity().getFirstName());
        assertEquals("kent", staffMember.getIdentity().getLastName());
        assertEquals(LocalDate.parse("1977-04-18"),staffMember.getBirthday());
        assertEquals(List.of(
                StaffMember.Skill.JAVA,
                StaffMember.Skill.KOTLIN,
                StaffMember.Skill.ENGINEERING),staffMember.getSkills());
    }

    @Test
    void testFindAll() {
    }

    @Test
    void testFindByName() {
    }

    @Test
    void saveInMemoryStaffmember() {
        var newStaffMember= StaffMember.builder()
                .displayName("tdanger")
                .birthday(LocalDate.parse("2025-09-17"))
                .identity(Identity.builder()
                        .firstName("Tim")
                        .lastName("Tester")
                        .build())
                .skills(List.of(StaffMember.Skill.DDD, StaffMember.Skill.ENGINEERING))
                .build();
        staffmemberInMemoryRepository.saveStaffmemberInMemory(newStaffMember);
        assertEquals(newStaffMember, staffmemberInMemoryRepository.findByName("tdanger").get());
    }
}