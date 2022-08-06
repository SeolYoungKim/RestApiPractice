package ToyProject.RestApiPractice.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class EditPost {

    @NotBlank(message = "제목이 없습니다.")
    private String title;

    @NotBlank(message = "내용이 없습니다.")
    private String content;

    @Builder
    public EditPost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
