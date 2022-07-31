package ToyProject.RestApiPractice.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//TODO : Enum 좀 더 공부해보기!
@Getter
@RequiredArgsConstructor
public enum Role {

    // Spring security에서는 항상 "ROLE_" 이 앞에있어야만 한다.
    // 그래서 코드 별 Key 값을 ROLE_GUEST, ROLE_USER 등으로 지정한다.

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ;

    private final String key;
    private final String title;
}
