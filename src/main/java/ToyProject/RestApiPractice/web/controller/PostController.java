package ToyProject.RestApiPractice.web.controller;

import ToyProject.RestApiPractice.exception.NullPostException;
import ToyProject.RestApiPractice.service.PostService;
import ToyProject.RestApiPractice.web.request.AddPost;
import ToyProject.RestApiPractice.web.request.PostPage;
import ToyProject.RestApiPractice.web.response.ResponsePost;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/add")
    public ResponsePost addPost(@RequestBody @Validated AddPost addPost) {
        return postService.save(addPost);
    }

    @GetMapping("/post/{id}")
    public ResponsePost readPost(@PathVariable Long id) throws NullPostException {
        return postService.findById(id);
    }

    @GetMapping("/posts")
    public List<ResponsePost> readPostByPage(@ModelAttribute PostPage postPage) {
        return postService.getPageList(postPage);
    }
}
