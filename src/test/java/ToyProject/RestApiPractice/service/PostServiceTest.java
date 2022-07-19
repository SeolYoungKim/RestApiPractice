package ToyProject.RestApiPractice.service;

import ToyProject.RestApiPractice.repository.PostRepository;
import ToyProject.RestApiPractice.web.request.AddPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @DisplayName("글이 저장된다.")
    @Test
    void addTest() {
        //given
        AddPost addPost = AddPost.builder()
                .title("제목")
                .text("내용")
                .build();

        //when
        postService.save(addPost);

        //then
        assertThat(postRepository.findAll().size()).isEqualTo(1);
        assertThat(postRepository.findAll().get(0).getTitle()).isEqualTo("제목");
        assertThat(postRepository.findAll().get(0).getText()).isEqualTo("내용");
    }
}