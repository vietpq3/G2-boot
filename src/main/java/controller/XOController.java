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

	@RequestMapping(value = { "", "/", "/*", "xo" })
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("playerId", request.getSession().getId());
		model.addAttribute("size", 50);
		board = new String[50][50];

		return "xo";
	}

}
