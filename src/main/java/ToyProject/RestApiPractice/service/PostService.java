package ToyProject.RestApiPractice.service;

import ToyProject.RestApiPractice.domain.Post;
import ToyProject.RestApiPractice.repository.PostRepository;
import ToyProject.RestApiPractice.web.request.AddPost;
import ToyProject.RestApiPractice.web.response.ResponsePost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public ResponsePost save(AddPost addPost) {
        Post post = Post.builder()
                .title(addPost.getTitle())
                .text(addPost.getText())
                .build();

        postRepository.save(post);

        return new ResponsePost(post);
    }
}
