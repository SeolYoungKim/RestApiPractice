package ToyProject.RestApiPractice.service;

import ToyProject.RestApiPractice.domain.post.Post;
import ToyProject.RestApiPractice.exception.NullPostException;
import ToyProject.RestApiPractice.repository.post.PostRepository;
import ToyProject.RestApiPractice.web.request.AddPost;
import ToyProject.RestApiPractice.web.request.EditPost;
import ToyProject.RestApiPractice.web.request.PostPage;
import ToyProject.RestApiPractice.web.response.ResponsePost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public ResponsePost save(AddPost addPost) {
        Post post = Post.builder()
                .title(addPost.getTitle())
                .text(addPost.getText())
                .author(addPost.getAuthor())
                .build();

        postRepository.save(post);

        return new ResponsePost(post);
    }

    public ResponsePost findById(Long id) throws NullPostException {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPostException("글이 없습니다."));
        return new ResponsePost(post);
    }

    @Transactional(readOnly = true)
    public List<ResponsePost> getPageList(PostPage postPage) {
        List<Post> pageList = postRepository.getPageList(postPage);

        return pageList.stream()
                .map(ResponsePost::new)
                .collect(Collectors.toList());
    }

    public ResponsePost editPost(Long id, EditPost editPost) throws NullPostException {
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new NullPostException("글이 없습니다."));

        findPost.editPost(editPost);

        postRepository.save(findPost);

        return new ResponsePost(findPost);
    }

    public void deletePost(Long id) throws NullPostException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPostException("글이 없습니다."));

        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<ResponsePost> findAllDesc() {
        return postRepository.findAllDesc().stream()
                .map(ResponsePost::new)
                .collect(Collectors.toList());
    }
}
