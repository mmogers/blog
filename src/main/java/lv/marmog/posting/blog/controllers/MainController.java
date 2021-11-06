package lv.marmog.posting.blog.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller //obrabotka vseh perehodov na saite
public class MainController {

    @GetMapping("/main") //kakoi address obrabatyvaem "/" - glavnaya stranica
    public String home(Model model) { //pri perehode na glavnuju stranicy vyzyvaetsya funkciya home - peredaesta parametr model kot vsegda prinimaetsya , pri pomoschi parametra peredaem dannye vnutr'shablona title so znacheniem main page i vyzyvaem shablon "home"
        model.addAttribute("title", "Main Page"); //parametr "title" budet peredan v shablon
        return "home"; //vyzyvaem shablon "home"
    }

    @GetMapping("/about") //kakoi address obrabatyvaem "/" - glavnaya stranica
    public String about(Model model) { //pri perehode na glavnuju stranicy vyzyvaetsya funkciya home - peredaesta parametr model kot vsegda prinimaetsya , pri pomoschi parametra peredaem dannye vnutr'shablona title so znacheniem main page i vyzyvaem shablon "home"
        model.addAttribute("title", "About Us Page"); //parametr "title" budet peredan v shablon
        return "about"; //vyzyvaem shablon "home"
    }
}