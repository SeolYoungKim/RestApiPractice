package ToyProject.RestApiPractice.repository.post;

import ToyProject.RestApiPractice.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
