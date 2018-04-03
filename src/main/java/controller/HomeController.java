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
import form.HomeForm;
import logic.IHomeLogic;

@Controller
@RequestMapping("home")
public class HomeController extends AbstractController {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final String HIGH_SCORE_JSP = "highScore";
    private static final String HIGHSCORE = "highscore";
    private static final String INDEX = "index";
    private static final String HOME_JSP = "home";
    private static final String REDIRECT_LOGIN = "redirect:/login";
	private static final String REDIRECT_HOME = "redirect:/home/index";

    @Autowired
    private IHomeLogic homeLogic;

	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String index() {
		return REDIRECT_HOME;
	}

	@RequestMapping(value = { INDEX, "/" })
    public String index(HttpServletRequest request, Model model) {
        SessionAccessor session = new SessionAccessor(request);

        if (session.getLoginUser() == null) {
            return REDIRECT_LOGIN;
        }

        model.addAttribute("loginUser", session.getLoginUser().getUsername());
        model.addAttribute("highScore", session.getLoginUser().getHighScore());

        session.setFightForm(null);

        return HOME_JSP;
    }

    @RequestMapping(value = HIGHSCORE)
    public String highScore(@ModelAttribute("form") HomeForm form, HttpServletRequest request, Model model)
            throws SystemException {
        SessionAccessor session = new SessionAccessor(request);

        if (session.getLoginUser() == null) {
            return REDIRECT_LOGIN;
        }

        int pageNumber = form.getPageNumber() == null ? DEFAULT_PAGE_NUMBER : form.getPageNumber();

        List<UserInfo> userInfoList = null;
        try {
            userInfoList = homeLogic.getAllUserInfo();
        } catch (SQLException e) {
            throw new SystemException("SQL Exception");
        }

        model.addAttribute("userInfoList", userInfoList);
        model.addAttribute("loginUser", session.getLoginUser().getUsername());
        model.addAttribute("highScore", session.getLoginUser().getHighScore());

        return HIGH_SCORE_JSP;
    }

    @ModelAttribute("form")
    public HomeForm initForm() {
        return new HomeForm();
    }
}
