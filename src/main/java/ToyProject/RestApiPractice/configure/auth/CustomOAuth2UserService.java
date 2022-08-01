package ToyProject.RestApiPractice.configure.auth;

import ToyProject.RestApiPractice.configure.auth.dto.OAuthAttributes;
import ToyProject.RestApiPractice.configure.auth.dto.SessionUser;
import ToyProject.RestApiPractice.domain.user.User;
import ToyProject.RestApiPractice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

//TODO: 코드를 왜 이렇게 구성하였을까? 찾아보자.

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 구글 로그인 정보들을 서비스에서 사용하기 위해 변환하는 과정임.

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        // OAuth2 로그인 진행 시, 키가 되는 필드값. == primary key
        // google 기본 코드 = "sub" (네이버, 카카오는 기본 코드를 지원하지 않음.)
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User attribute를 담을 클래스 (이후 다른 소셜 로그인도 해당 클래스 사용)
        // OAuth2User -> OAuthAttributes(static 메서드를 이용하여 객체 생성)
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);  // 구글 사용자 정보가 업데이트 되었을 때를 대비한 update 기능.

        // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스.
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())  // email로 user 검색
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))  // 검색한 user를 업데이트.
                .orElse(attributes.toEntity());  // email로 유저 찾아봤는데 없으면 게스트

        return userRepository.save(user);
    }

}
