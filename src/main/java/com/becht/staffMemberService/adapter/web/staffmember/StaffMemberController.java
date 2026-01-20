package com.becht.staffMemberService.adapter.web.staffmember;

import com.becht.staffMemberService.adapter.web.staffmember.dto.StaffMemberInDto;
import com.becht.staffMemberService.adapter.web.staffmember.dto.StaffMemberOutDto;
import com.becht.staffMemberService.mapper.staffmember.StaffMemberMapper;
import com.becht.staffMemberService.service.staffmember.StaffMemberService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/staffmembers")
public class StaffMemberController {

    private final StaffMemberService staffMemberService;

    /**
     * Retrieve all staff members
     */
    @GetMapping
    public ResponseEntity<List<StaffMemberOutDto>> getStaffMembers(
            @RequestParam(value = "skill", required = false) String skill,
            @RequestParam(value = "encrypted", required = false, defaultValue = "false") boolean encrypted
    ) {
        if (skill != null && !staffMemberService.isValidSkill(skill)) {
            return ResponseEntity.badRequest().build();
        }

        var staffmembers = (skill == null)
                ? (encrypted ? staffMemberService.getAllStaffMembersEncrypted() : staffMemberService.getAllStaffMembers())
                : (encrypted ? staffMemberService.getStaffMembersBySkillEncrypted(skill)
                : staffMemberService.getStaffMembersBySkill(skill));

        return ResponseEntity.ok(
                staffmembers.stream().map(StaffMemberMapper::toOutDto).toList()
        );
    }

    /**
     * Retrieve a single staff member
     */
    @GetMapping("/{staffName}")
    public ResponseEntity<StaffMemberOutDto> getStaffMember(
            @PathVariable("staffName") String staffMemberName,
            @RequestParam(value = "encrypted", required = false, defaultValue = "false") boolean encrypted
    ) {
        var staffmemberOpt = encrypted
                ? staffMemberService.getStaffMemberEncrypted(staffMemberName)
                : staffMemberService.getStaffMember(staffMemberName);

        return staffmemberOpt.map(StaffMemberMapper::toOutDto)
                .fold(ResponseEntity.notFound()::build, ResponseEntity::ok);
    }

    /**
     * Add a new staff member
     */
    @PostMapping
    public ResponseEntity<StaffMemberOutDto> addStaffMember(@RequestBody StaffMemberInDto staffMemberInDto) {
        if (!staffMemberService.hasOnlyValidSkills(staffMemberInDto.getSkills())) {
            return ResponseEntity.badRequest().build();
        }
        val staffMember = this.staffMemberService.saveStaffMembers(StaffMemberMapper.toEntity(staffMemberInDto));
        return ResponseEntity.ok(StaffMemberMapper.toOutDto(staffMember));
    }
}
