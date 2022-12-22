package by.pashkevich.mikhail.repository;

import by.pashkevich.mikhail.model.Role;
import by.pashkevich.mikhail.model.enums.Rolename;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Rolename name);
}
