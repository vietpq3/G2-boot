package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import common.SessionAccessor;
import entity.ErrorMessage;
import entity.UserInfo;
import form.LoginForm;
import logic.ILoginLogic;
import param.LoginParam;

@Controller
public class LoginController extends AbstractController {

	private static final String LOGIN_JSP = "login";
	private static final String REDIRECT_LEARN_EN = "redirect:/learnEn/index";

	@Autowired
	private ILoginLogic loginLogic;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String index() {
		return LOGIN_JSP;
	}

	@RequestMapping(value = { "login" }, method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute("form") LoginForm form, BindingResult binding, Model model,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		if (binding.hasErrors()) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setErrorMessageList(resolveErrorMessage(form, binding));
			model.addAttribute("errorMessage", errorMessage);
			return LOGIN_JSP;
		}

		LoginParam param = new LoginParam();
		param.setUsername(form.getUsername());
		param.setPassword(form.getPassword());

		List<UserInfo> userInfoList = loginLogic.checkLogin(param);
		if (userInfoList != null && userInfoList.size() == 1) {
			SessionAccessor session = new SessionAccessor(request);
			session.setLoginUser(userInfoList.get(0));
			return REDIRECT_LEARN_EN;
		} else {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.getErrorMessageList().add("Username and password are not mapping");
			model.addAttribute("errorMessage", errorMessage);
			return LOGIN_JSP;
		}
	}

	@ModelAttribute("form")
	public LoginForm getForm() {
		return new LoginForm();
	}
}
