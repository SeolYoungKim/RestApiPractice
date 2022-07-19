package ToyProject.RestApiPractice.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pId;

    @Column
    private String title;

    @Column
    private String text;

    @Builder
    public Post(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
