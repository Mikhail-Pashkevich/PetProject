package by.pashkevich.mikhail.repository;

import by.pashkevich.mikhail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByLogin(String login);
}
