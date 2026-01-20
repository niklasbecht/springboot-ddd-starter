package com.becht.staffMemberService.repository.staffmember;


import com.becht.staffMemberService.domain.model.staffmember.StaffMember;
import io.vavr.control.Option;
import lombok.NonNull;

import java.util.List;


public interface StaffmemberInMemoryRepository {

    List<StaffMember> findAll();

    Option<StaffMember> findByName(@NonNull String name);

    StaffMember saveStaffmemberInMemory(@NonNull StaffMember staffMember);
}
