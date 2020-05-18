package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import constant.XOConstant;

@Controller
public class XOController {

	@Autowired
	public String[][] board;

	@Autowired
	@Qualifier("playerList")
	private List<String> playerList;

	@RequestMapping(value = { "", "/", "xo" })
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("size", XOConstant.SIZE);

		return "thymeleaf/xo";
	}

	@RequestMapping("/api/getPlayerList")
	@ResponseBody
	public List<String> getPlayerList() {
		return this.playerList;
	}

	@RequestMapping("/api/kickPlayer/{playerId}")
	@ResponseBody
	public boolean kickPlayer(@PathVariable String playerId) {
		return playerList.remove(playerId);
	}
}
