package ToyProject.RestApiPractice.web.controller;

import ToyProject.RestApiPractice.exception.NullPostException;
import ToyProject.RestApiPractice.service.PostService;
import ToyProject.RestApiPractice.web.request.AddPost;
import ToyProject.RestApiPractice.web.request.EditPost;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/post")
    public Long save(@RequestBody AddPost addPost) { // 책에 Long type을 반환해서, 최대한 맞춰봄.
        return postService.save(addPost).getId();
    }

    @PutMapping("/api/v1/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody EditPost editPost) throws NullPostException {
        return postService.editPost(id, editPost).getId();
    }

    @DeleteMapping("/api/v1/post/{id}")
    public Long delete(@PathVariable Long id) throws NullPostException {
        postService.deletePost(id);
        return id;
    }
}
