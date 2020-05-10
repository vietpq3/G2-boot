package controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import annotation.MessageConfig;
import common.SessionAccessor;
import entity.UserInfo;
import exception.SystemException;
import form.AbstractForm;

public abstract class AbstractController {

	@Autowired
	MessageSource messageSource;

	@ExceptionHandler(SystemException.class)
	public ModelAndView SystemExceptionHandle(SystemException ex) {
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("message", ex.getMessage());
		return mav;
	}

	protected List<String> resolveErrorMessage(final AbstractForm form, final BindingResult binding) {

		List<FieldError> errorList = binding.getFieldErrors();
		List<String> messageList = new ArrayList<String>();
		Map<Integer, String> messageMap = new TreeMap<Integer, String>();

		for (FieldError fieldError : errorList) {

			String messageKey = fieldError.getDefaultMessage();
			String[] args = null;

			DefaultMessageSourceResolvable message = (DefaultMessageSourceResolvable) fieldError.getArguments()[0];
			String targetName = message.getDefaultMessage();

			MessageConfig messageConfig = null;
			boolean foundTarget = false;

			for (Field field : form.getClass().getDeclaredFields()) {
				if (field.getName().equals(targetName)) {
					foundTarget = true;
					messageConfig = field.getAnnotation(MessageConfig.class);
					break;
				}
			}

			if (!foundTarget) {
				for (Method method : form.getClass().getMethods()) {
					if (method.getName().toLowerCase().endsWith(targetName.toLowerCase())) {
						messageConfig = method.getAnnotation(MessageConfig.class);
						break;
					}
				}
			}

			MessageSourceAccessor accessor = new MessageSourceAccessor(messageSource);

			if (messageSource != null) {
				List<String> argList = new ArrayList<String>();
				for (String key : messageConfig.value()) {
					argList.add(accessor.getMessage(key));
				}
				args = argList.toArray(new String[] {});
			}

			String resolveMessage = null;

			if (args == null) {
				resolveMessage = accessor.getMessage(messageKey);
			} else {
				resolveMessage = accessor.getMessage(messageKey, args);
			}

			if (messageConfig != null) {
				messageMap.put(messageConfig.order(), resolveMessage);
			}
		}

		for (Integer key : messageMap.keySet()) {
			messageList.add(messageMap.get(key));
		}

		return messageList;
	}

	protected void checkLogin(HttpServletRequest request) {
		SessionAccessor session = new SessionAccessor(request);

		if (session.getLoginUser() == null) {
			UserInfo loginUser = new UserInfo();
			loginUser.setGuest(true);
			loginUser.setUsername("Guest");

			session.setLoginUser(loginUser);
		}
	}
}
