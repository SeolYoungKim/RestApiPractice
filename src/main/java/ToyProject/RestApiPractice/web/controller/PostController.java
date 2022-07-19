package ToyProject.RestApiPractice.web.controller;

import ToyProject.RestApiPractice.service.PostService;
import ToyProject.RestApiPractice.web.request.AddPost;
import ToyProject.RestApiPractice.web.response.ResponsePost;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/add")
    public ResponsePost addPost(@RequestBody @Validated AddPost addPost) {
        return postService.save(addPost);
    }
}
