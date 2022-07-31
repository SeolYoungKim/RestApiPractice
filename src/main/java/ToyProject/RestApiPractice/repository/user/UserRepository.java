package ToyProject.RestApiPractice.repository.user;

import ToyProject.RestApiPractice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
