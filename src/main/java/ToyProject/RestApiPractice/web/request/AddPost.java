package ToyProject.RestApiPractice.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class AddPost {

    @NotBlank(message = "제목이 없습니다.")
    private String title;

    @NotBlank(message = "글이 없습니다.")
    private String content;

    @NotBlank(message = "작성자를 입력해주세요")
    private String author;

    @Builder
    public AddPost(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
