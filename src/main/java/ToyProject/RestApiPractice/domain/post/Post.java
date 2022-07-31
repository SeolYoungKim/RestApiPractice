package ToyProject.RestApiPractice.domain.post;

import ToyProject.RestApiPractice.domain.BaseTimeEntity;
import ToyProject.RestApiPractice.web.request.EditPost;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pId;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private String author;

    @Builder
    public Post(String title, String text, String author) {
        this.title = title;
        this.text = text;
        this.author = author;
    }

    public void editPost(EditPost editPost) {
        this.title = editPost.getTitle();
        this.text = editPost.getText();
    }
}
