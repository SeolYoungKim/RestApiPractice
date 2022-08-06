package ToyProject.RestApiPractice.web.response;

import ToyProject.RestApiPractice.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponsePost {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime modifiedDate;

    public ResponsePost(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.modifiedDate = post.getModifiedDate();
    }
}
