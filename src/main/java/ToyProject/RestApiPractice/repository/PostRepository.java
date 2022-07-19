package ToyProject.RestApiPractice.repository;

import ToyProject.RestApiPractice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
