package ToyProject.RestApiPractice.repository.post;

import ToyProject.RestApiPractice.domain.post.Post;
import ToyProject.RestApiPractice.web.request.PostPage;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getPageList(PostPage postPage);

    List<Post> findAllDesc();
}
