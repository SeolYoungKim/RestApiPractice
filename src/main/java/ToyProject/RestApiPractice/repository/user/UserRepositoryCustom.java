package ToyProject.RestApiPractice.repository.user;

import ToyProject.RestApiPractice.domain.user.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByEmail(String email);
}
