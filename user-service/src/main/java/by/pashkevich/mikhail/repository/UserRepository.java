package by.pashkevich.mikhail.repository;

import by.pashkevich.mikhail.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Query(value = """
            select u
            from User u join fetch u.roles
            where u.login = :login
            """)
    Optional<User> findByLoginWithRoles(String login);

    Boolean existsByLogin(String login);
}
