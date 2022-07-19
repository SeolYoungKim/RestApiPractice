package ToyProject.RestApiPractice.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;

@Getter @Setter
@Builder
public class PostPage {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    public long getOffset() {
        return (long) (max(this.page, 1) - 1) * min(500, size);
    }
}
