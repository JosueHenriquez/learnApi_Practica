package IntegracionBackFront.backfront.Repositories.Users;

import IntegracionBackFront.backfront.Entities.Users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByCorreo(String email);

    boolean existsByCorreo(String email);
}
