package ToyProject.RestApiPractice.web.response;

import ToyProject.RestApiPractice.domain.Post;
import lombok.Getter;

@Getter
public class ResponsePost {

    private final Long id;
    private final String title;
    private final String text;

    public ResponsePost(Post post) {
        this.id = post.getPId();
        this.title = post.getTitle();
        this.text = post.getText();
    }
}
