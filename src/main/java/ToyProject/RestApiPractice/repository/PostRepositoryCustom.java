package ToyProject.RestApiPractice.repository;

import ToyProject.RestApiPractice.domain.Post;
import ToyProject.RestApiPractice.web.request.PostPage;

import java.util.List;

public interface PostRepositoryCustom {

    public List<Post> getPageList(PostPage postPage);
}
