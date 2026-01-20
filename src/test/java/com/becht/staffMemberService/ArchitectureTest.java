package com.becht.staffMemberService;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

public class ArchitectureTest {
    JavaClasses classes=new ClassFileImporter().importPackages("com.becht.staffMemberService");

    @Test
    public void verifyControllerPackageAccess(){
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAnyPackage(
                        "..domain..",
                        "..repository..",
                        "..service..",
                        "..util.."
                ).and()
                .resideOutsideOfPackage("..test..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..adapter..");

        rule.check(classes);

    }

    @Test
    public void verifyDomainPackageAccess() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAnyPackage(
                        "..adapter.."
                ).and()
                .resideOutsideOfPackage("..test..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..domain..");

        rule.check(classes);
    }

    @Test
    public void verifyMapperPackageAccess() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAnyPackage(
                        "..domain..",
                        "..service..",
                        "..util.."
                        ).and()
                .resideOutsideOfPackage("..test..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..mapper..");

        rule.check(classes);
    }

    @Test
    public void verifyRepositoryPackageAccess() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAnyPackage(
                        "..adapter..",
                        "..domain..",
                        "..mapper..",
                        "..util.."
                ).and()
                .resideOutsideOfPackage("..test..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..repository..");

        rule.check(classes);
    }
    @Test
    public void verifyServicePackageAccess() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that()
                .resideInAnyPackage(
                        "..domain..",
                        "..mapper..",
                        "..repository..",
                        "..util.."
                ).and()
                .resideOutsideOfPackage("..test..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..service..");

        rule.check(classes);
    }
}
