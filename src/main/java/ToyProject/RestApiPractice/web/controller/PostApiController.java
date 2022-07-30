package ToyProject.RestApiPractice.web.controller;

import ToyProject.RestApiPractice.service.PostService;
import ToyProject.RestApiPractice.web.request.AddPost;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/post")
    public Long save(@RequestBody AddPost addPost) { // 책에 Long type을 반환해서, 최대한 맞춰봄.
        return postService.save(addPost).getId();
    }
}
