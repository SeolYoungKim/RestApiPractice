package ToyProject.RestApiPractice.web.controller;

import ToyProject.RestApiPractice.domain.post.Post;
import ToyProject.RestApiPractice.repository.post.PostRepository;
import ToyProject.RestApiPractice.service.PostService;
import ToyProject.RestApiPractice.web.request.AddPost;
import ToyProject.RestApiPractice.web.request.EditPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(roles = "USER")
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @DisplayName("글이 저장된다.")
    @Test
    void addPost() throws Exception {

        AddPost addPost = AddPost.builder()
                .title("제목")
                .content("내용")
                .author("김씨")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andExpect(jsonPath("$.author").value("김씨"))
                .andDo(print());
    }

    @DisplayName("글 저장 시, 검증이 작동한다")
    @Test
    void validAddPost() throws Exception {

        AddPost addPost = AddPost.builder()
                .title("")
                .content(null)
                .author("")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addPost)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.field.content").value("글이 없습니다."))
                .andExpect(jsonPath("$.field.title").value("제목이 없습니다."))
                .andExpect(jsonPath("$.field.author").value("작성자를 입력해주세요"))
                .andDo(print());

    }

    @DisplayName("글 단건 조회")
    @Test
    void readPost() throws Exception {

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author("작성자")
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/post/{id}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andExpect(jsonPath("$.author").value("작성자"))
                .andDo(print());
    }

    @DisplayName("잘못된 id로 조회 시, 예외 발생 및 메시지 출력")
    @Test
    void readPostByNullId() throws Exception {

        AddPost addPost = AddPost.builder()
                .title("제목")
                .content("내용")
                .author("작성자")
                .build();

        postService.save(addPost);

        mockMvc.perform(MockMvcRequestBuilders.get("/post/23434")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("글이 없습니다."))
                .andDo(print());
    }

    @DisplayName("글 1페이지 조회")
    @Test
    void get1Page() throws Exception {

        List<Post> postList = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("title" + i)
                        .content("text" + i)
                        .author("author" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(postList);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=5")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("title30"))
                .andExpect(jsonPath("$.[0].content").value("text30"))
                .andExpect(jsonPath("$.[4].title").value("title26"))
                .andExpect(jsonPath("$.[4].content").value("text26"))
                .andDo(print());
    }

    @DisplayName("글 수정 테스트")
    @Test
    void editPost() throws Exception {

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author("작성자")
                .build();

        postRepository.save(post);

        EditPost editPost = EditPost.builder()
                .title("title")
                .content("text")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/post/{id}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("text"))
                .andExpect(jsonPath("$.author").value("작성자"))
                .andDo(print());
    }

    @DisplayName("글 삭제 테스트")
    @Test
    void deletePost() throws Exception {

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.delete("/post/{id}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Delete ok"))
                .andDo(print());
    }
}