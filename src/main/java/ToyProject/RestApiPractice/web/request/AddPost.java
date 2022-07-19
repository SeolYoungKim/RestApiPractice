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
    private String text;


    @Builder
    public AddPost(String title, String text) {
        this.title = title;
        this.text = text;
    }
}