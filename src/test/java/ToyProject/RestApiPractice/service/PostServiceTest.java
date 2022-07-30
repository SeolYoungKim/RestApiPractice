package ToyProject.RestApiPractice.service;

import ToyProject.RestApiPractice.domain.Post;
import ToyProject.RestApiPractice.exception.NullPostException;
import ToyProject.RestApiPractice.repository.PostRepository;
import ToyProject.RestApiPractice.web.request.AddPost;
import ToyProject.RestApiPractice.web.request.EditPost;
import ToyProject.RestApiPractice.web.request.PostPage;
import ToyProject.RestApiPractice.web.response.ResponsePost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void clean() {
        postRepository.deleteAllInBatch();
    }

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
        Post post = Post.builder()
                .title("제목")
                .text("내용")
                .build();

        postRepository.save(post);

        //when
        ResponsePost findPost = postService.findById(post.getPId());

        //then
        assertThat(findPost.getId()).isEqualTo(post.getPId());
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
        assertThatThrownBy(() -> postService.findById(234L))
                .isInstanceOf(NullPostException.class);
    }

    @DisplayName("글 1page 조회")
    @Test
    void get1Page() {
        List<Post> postList = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("title" + i)
                        .text("text" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(postList);

        PostPage postPage = PostPage.builder()
                .build();

        List<Post> pageList = postRepository.getPageList(postPage);

        assertThat(pageList.size()).isEqualTo(10);
        assertThat(pageList.get(0).getTitle()).isEqualTo("title30");
        assertThat(pageList.get(9).getTitle()).isEqualTo("title21");

    }

    @DisplayName("글 수정 테스트")
    @Test
    void editPost() {
        Post post = Post.builder()
                .title("제목")
                .text("내용")
                .build();

        postRepository.save(post);

        EditPost editPost = EditPost.builder()
                .title("하하하")
                .text("호호호")
                .build();


        post.editPost(editPost);

        assertThat(post.getTitle()).isEqualTo("하하하");
        assertThat(post.getText()).isEqualTo("호호호");
    }

    @DisplayName("글 삭제 테스트")
    @Test
    void deletePost() {
        Post post = Post.builder()
                .title("제목")
                .text("내용")
                .build();

        postRepository.save(post);

        postService.deletePost(post.getPId());

        assertThat(postRepository.findAll().size()).isEqualTo(0);
        assertThatThrownBy(() -> postService.findById(post.getPId()))
                .isInstanceOf(NullPostException.class);
    }

    @DisplayName("저장 시간 조회 테스트")
    @Test
    void saveTimeTest() {
        LocalDateTime now = LocalDateTime.of(2022, 7, 30, 0, 0, 0);

        Post post = Post.builder()
                .title("제목")
                .text("내용")
                .build();

        postRepository.save(post);

        List<Post> posts = postRepository.findAll();

        Post post1 = posts.get(0);

        System.out.println(">>>>>>>>>>>> createDate=" + post1.getCreatedDate() + ", modifiedDate=" + post1.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }

    @DisplayName("수정 시간 조회 테스트")
    @Test
    void EditTimeTest() throws InterruptedException {
        LocalDateTime now = LocalDateTime.of(2022, 7, 30, 0, 0, 0);

        Post post = Post.builder()
                .title("제목")
                .text("내용")
                .build();

        postRepository.save(post);

        Thread.sleep(10000);

        post.editPost(EditPost.builder()
                .title("zz")
                .text("yy")
                .build());

        postRepository.save(post);

        List<Post> posts = postRepository.findAll();

        Post post1 = posts.get(0);

        System.out.println(">>>>>>>>>>>> createDate=" + post1.getCreatedDate() + ", modifiedDate=" + post1.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}