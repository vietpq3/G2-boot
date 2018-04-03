package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import logic.ILoginLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import param.LoginParam;
import entity.ErrorMessage;
import form.LoginForm;

@Controller
@RequestMapping(value = "register")
public class RegisterController extends AbstractController {

    private static final String REGISTER_JSP = "register";
	private static final String REDIRECT_REGISTER = "redirect:/register/";

    @Autowired
    private ILoginLogic loginLogic;

	@RequestMapping(value = { "/", "register/" }, method = RequestMethod.GET)
    public String index() {
        return REGISTER_JSP;
    }

	@RequestMapping(value = { "", "register" }, method = RequestMethod.GET)
	public String redirect() {
		return REDIRECT_REGISTER;
	}

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("form") LoginForm form, BindingResult binding,
            HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        if (binding.hasErrors()) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setErrorMessageList(resolveErrorMessage(form, binding));
            model.addAttribute("errorMessage", errorMessage);
            return REGISTER_JSP;
        }

        LoginParam param = new LoginParam();
        param.setUsername(form.getUsername());
        param.setPassword(form.getPassword());

        int result = 0;
        try {
            result = loginLogic.registerAccount(param);
        } catch (SQLException e) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.getErrorMessageList().add("Username is in use");
            model.addAttribute("errorMessage", errorMessage);
        }

        if (result == 1) {
            request.getRequestDispatcher("/login").forward(request, response);
        }

        return REGISTER_JSP;
    }

    @ModelAttribute("form")
    public LoginForm getForm() {
        return new LoginForm();
    }
}
