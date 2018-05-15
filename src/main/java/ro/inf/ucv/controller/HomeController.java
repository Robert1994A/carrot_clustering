package ro.inf.ucv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ro.inf.ucv.wrapper.SearchModel;

@Controller
public class HomeController extends BaseController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("searchModel", new SearchModel());
		return "home";
	}

	@GetMapping("/500")
	public String error500() {
		return "500";
	}
}
