package policy.demo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("policy.demo");

        noClasses()
            .that()
            .resideInAnyPackage("policy.demo.service..")
            .or()
            .resideInAnyPackage("policy.demo.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..policy.demo.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
