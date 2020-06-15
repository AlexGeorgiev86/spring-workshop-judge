package judgev2.web;

import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(HttpSession httpSession) {
        return httpSession.getAttribute("user") == null ? "index" : "home";
    }

}
