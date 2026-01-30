package toyproject.workspot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toyproject.workspot.domain.Agite;
import toyproject.workspot.domain.Spot;
import toyproject.workspot.domain.User;
import toyproject.workspot.service.AgiteService;
import toyproject.workspot.service.SpotService;
import toyproject.workspot.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AgiteController {

    private final AgiteService agiteService;
    private final UserService userService;
    private final SpotService spotService;

    // "내 아지트로 등록" 버튼을 눌렀을 때
    // 어떤 유저의 아지트로 등록할 것인지 선택하는 페이지
    @PostMapping("/agite/selectUser")
    public String selectUser(@RequestParam("spotId") Long spotId, Model model) {

        model.addAttribute("spotId", spotId);

        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "agite/selectUser";
    }

    // 여기서 userId와 spotId를 통해서 아지트로 등록되어 있는지 아닌지 확인해야될듯...
    @PostMapping("/agite/add")
    public String addAgite(@ModelAttribute("agiteForm") AgiteForm form, Model model) {
        model.addAttribute("agiteForm", form);

        if (agiteService.existsAgite(form.getUserId(), form.getSpotId())) {
            return "agite/alreadyRegistered"; //이미 등록된 아지트입니다 -> 화면에 띄움
        }
        return "agite/add"; // 추가하는 view로 넘어감
    }

    @PostMapping("/agite/new")
    public String createAgite(@ModelAttribute("agiteForm") AgiteForm form, Model model) {
        Agite agite = new Agite();
        agite.setCustomName(form.getCustomName());
        User user = userService.findOne(form.getUserId());
        agite.setUser(user);
        Spot spot = spotService.findOne(form.getSpotId());
        agite.setSpot(spot);
        agite.setHasOutlet(form.getHasOutlet());
        agite.setMemo(form.getMemo());

        agiteService.register(agite);

        List<Agite> agites = agiteService.findAllByUserId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("agites", agites);
        return "agite/agites";
    }

    @GetMapping("/agite/chooseUser")
    public String chooseUser(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "agite/chooseUser";
    }

    // pathValue를 이용해서 GetMapping으로 바꾸고
    // createAgite을 PRG 패턴으로 변경해봐야 할듯
    @PostMapping("/agite/list")
    public String list(@RequestParam("userId") Long userId, Model model) {
        List<Agite> agites = agiteService.findAllByUserId(userId);
        model.addAttribute("agites", agites);

        User user = userService.findOne(userId);
        model.addAttribute("user", user);
        return "agite/agites";
    }


}
