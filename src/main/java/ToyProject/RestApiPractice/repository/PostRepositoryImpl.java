package ToyProject.RestApiPractice.repository;

import ToyProject.RestApiPractice.domain.Post;
import ToyProject.RestApiPractice.web.request.PostPage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static ToyProject.RestApiPractice.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPageList(PostPage postPage) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postPage.getSize())
                .offset(postPage.getOffset())
                .orderBy(post.pId.desc())
                .fetch();
    }
}
