package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class XOController {

	@Autowired
	public String[][] board;
	private int size = 25;

	@RequestMapping(value = { "", "/", "xo" })
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("size", size);

		return "thymeleaf/xo";
	}
}
