package controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import common.SessionAccessor;
import entity.UserInfo;
import exception.SystemException;
import form.LearnEnForm;
import logic.ILearnEnLogic;

@Controller
@RequestMapping("learnEn")
public class LearnEnController extends AbstractController {

	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final String HIGH_SCORE_JSP = "highScore";
	private static final String HIGHSCORE = "highscore";
	private static final String INDEX = "index";
	private static final String LEARN_EN_JSP = "learnEn";
	private static final String REDIRECT_INDEX = "redirect:/learnEn/index";

	@Autowired
	private ILearnEnLogic learnEnLogic;

	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String index() {
		return REDIRECT_INDEX;
	}

	@RequestMapping(value = { INDEX, "/" })
	public String index(HttpServletRequest request, Model model) {
		SessionAccessor session = new SessionAccessor(request);

		super.checkLogin(request);

		model.addAttribute("loginUser", session.getLoginUser().getUsername());
		model.addAttribute("highScore", session.getLoginUser().getHighScore());

		session.setFightForm(null);

		return LEARN_EN_JSP;
	}

	@RequestMapping(value = HIGHSCORE)
	public String highScore(@ModelAttribute("form") LearnEnForm form, HttpServletRequest request, Model model)
			throws SystemException {
		SessionAccessor session = new SessionAccessor(request);

		super.checkLogin(request);

		int pageNumber = form.getPageNumber() == null ? DEFAULT_PAGE_NUMBER : form.getPageNumber();

		List<UserInfo> userInfoList = null;
		try {
			userInfoList = learnEnLogic.getAllUserInfo();
		} catch (SQLException e) {
			throw new SystemException("SQL Exception");
		}

		model.addAttribute("userInfoList", userInfoList);
		model.addAttribute("loginUser", session.getLoginUser().getUsername());
		model.addAttribute("highScore", session.getLoginUser().getHighScore());

		return HIGH_SCORE_JSP;
	}

	@ModelAttribute("form")
	public LearnEnForm initForm() {
		return new LearnEnForm();
	}
}
