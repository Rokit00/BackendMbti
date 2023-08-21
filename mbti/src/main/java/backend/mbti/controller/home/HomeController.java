package backend.mbti.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"/", "/lists", "/debate/{id}", "/login", "/signup"})
    public String index() {
        return "forward:/index.html";
    }
}

