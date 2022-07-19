package ToyProject.RestApiPractice.service;

import ToyProject.RestApiPractice.exception.NullPostException;
import ToyProject.RestApiPractice.repository.PostRepository;
import ToyProject.RestApiPractice.web.request.AddPost;
import ToyProject.RestApiPractice.web.response.ResponsePost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("id로 글이 조회된다.")
    @Test
    void findById() throws NullPostException {
        //given
        AddPost addPost = AddPost.builder()
                .title("제목")
                .text("내용")
                .build();

        postService.save(addPost);

        //when
        ResponsePost findPost = postService.findById(1L);

        //then
        assertThat(findPost.getId()).isEqualTo(1L);
        assertThat(findPost.getTitle()).isEqualTo("제목");
        assertThat(findPost.getText()).isEqualTo("내용");

    }

    @DisplayName("id에 맞는 글이 없으면, NullPostException을 발생시킨다.")
    @Test
    void findByNullId() throws NullPostException {
        //given
        AddPost addPost = AddPost.builder()
                .title("제목")
                .text("내용")
                .build();

        postService.save(addPost);

        //then
        assertThatThrownBy(() -> postService.findById(2L))
                .isInstanceOf(NullPostException.class);
    }
}