package ToyProject.RestApiPractice.repository.post;

import ToyProject.RestApiPractice.domain.post.Post;
import ToyProject.RestApiPractice.web.request.PostPage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static ToyProject.RestApiPractice.domain.post.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPageList(PostPage postPage) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postPage.getSize())
                .offset(postPage.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }

    @Override
    public List<Post> findAllDesc() {
        return jpaQueryFactory.selectFrom(post)
                .orderBy(post.id.desc())
                .fetch();
    }
}
