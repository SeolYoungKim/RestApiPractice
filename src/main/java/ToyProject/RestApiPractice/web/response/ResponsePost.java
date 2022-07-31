package ToyProject.RestApiPractice.web.response;

import ToyProject.RestApiPractice.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponsePost {

    private final Long id;
    private final String title;
    private final String text;
    private final String author;
    private final LocalDateTime modifiedDate;

    public ResponsePost(Post post) {
        this.id = post.getPId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.author = post.getAuthor();
        this.modifiedDate = post.getModifiedDate();
    }
}
