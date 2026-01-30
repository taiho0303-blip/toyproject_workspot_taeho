package toyproject.workspot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home(@RequestParam(value = "status", required = false) boolean status,
                       @RequestParam(value = "type",required = false)String type,
                       Model model) {
        if (status) {
            if (type.equals("review")) {
                model.addAttribute("message", "리뷰 등록에 성공했습니다.");
            } else if (type.equals("user")) {
                model.addAttribute("message", "회원 등록에 성공했습니다.");
            }
        }
        log.info("home controller");
        return "home";
    }
}
