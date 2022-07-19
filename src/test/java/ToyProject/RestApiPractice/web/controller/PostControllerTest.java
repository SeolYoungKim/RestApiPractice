package ToyProject.RestApiPractice.web.controller;

import ToyProject.RestApiPractice.service.PostService;
import ToyProject.RestApiPractice.web.request.AddPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("글이 저장된다.")
    @Test
    void addPost() throws Exception {

        AddPost addPost = AddPost.builder()
                .title("제목")
                .text("내용")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.text").value("내용"))
                .andDo(print());
    }

    @DisplayName("글 저장 시, 검증이 작동한다")
    @Test
    void validAddPost() throws Exception {

        AddPost addPost = AddPost.builder()
                .title("")
                .text(null)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addPost)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.field.text").value("글이 없습니다."))
                .andExpect(jsonPath("$.field.title").value("제목이 없습니다."))
                .andDo(print());

    }
}