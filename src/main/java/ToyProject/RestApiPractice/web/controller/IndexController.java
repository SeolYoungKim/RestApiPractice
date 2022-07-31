package ToyProject.RestApiPractice.web.controller;

import ToyProject.RestApiPractice.configure.auth.LoginUser;
import ToyProject.RestApiPractice.configure.auth.dto.SessionUser;
import ToyProject.RestApiPractice.exception.NullPostException;
import ToyProject.RestApiPractice.service.PostService;
import ToyProject.RestApiPractice.web.response.ResponsePost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postService.findAllDesc());

//        // CustomOAuth2UserService에서, 로그인 성공 시 세션에 SessionUser가 저장되도록 구성함.
//        // 아래의 코드로 값을 가져오는 것.
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/post/save")
    public String postSave() {
        return "post-save";
    }

    @GetMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) throws NullPostException {
        ResponsePost dto = postService.findById(id);
        model.addAttribute("post", dto);

        return "post-update";
    }
}
