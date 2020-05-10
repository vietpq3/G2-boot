package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import common.CryptUtil;
import common.SessionAccessor;
import entity.PictureInfo;
import exception.SystemException;
import form.FightForm;
import logic.IFightLogic;
import param.LoginParam;

@Controller
@RequestMapping("fight")
public class FightController extends AbstractController {

	private static final String REDIRECT_FIGHT_FIGHT = "redirect:/fight/fight";
	private static final String FIGHT_JSP = "fight";
	private static final int LIFE_DEFAULT = 5;
	private static final String REDIRECT_LOGIN = "redirect:/login";

	@Autowired
	private IFightLogic fightLogic;

	@RequestMapping(value = "index", method = RequestMethod.POST)
	public String index() {
		return FIGHT_JSP;
	}

	@RequestMapping(value = "fight")
	public String fight(@ModelAttribute("form") FightForm form, Model model, HttpServletRequest request)
			throws SystemException {

		SessionAccessor session = new SessionAccessor(request);
		super.checkLogin(request);

		FightForm formInSession = session.getFightForm();

		String gameMode = form.getGameMode() == null ? formInSession.getGameMode() : form.getGameMode();
		List<PictureInfo> picInfoList = null;
		List<String> alreadyUseQuestionList = new ArrayList<String>();

		if ("Picture".equals(gameMode)) {
			picInfoList = fightLogic.getListPictureForPictureMode();
		}

		try {
			picInfoList = fightLogic.encryptPictureName(picInfoList, session.getId());
		} catch (UnsupportedEncodingException e) {
			throw new SystemException("Encrypt fail");
		}
		String question = fightLogic.getQuestion(picInfoList, alreadyUseQuestionList);
		alreadyUseQuestionList.add(question);

		form.setGameMode(gameMode);
		form.setPicInfoList(picInfoList);
		form.setAlreadyUseQuestionList(alreadyUseQuestionList);
		form.setQuestion(question);
		form.setLife(null == formInSession ? LIFE_DEFAULT : formInSession.getLife());
		form.setScore(null == formInSession ? 0 : formInSession.getScore());

		session.setFightForm(form);
		model.addAttribute("form", form);

		return FIGHT_JSP;
	}

	@RequestMapping(value = "fighting", method = RequestMethod.POST)
	public String fighting(@ModelAttribute("form") FightForm form, Model model, HttpServletRequest request,
			HttpServletResponse response) throws SystemException {

		SessionAccessor session = new SessionAccessor(request);
		FightForm formInSession = session.getFightForm();

		String answer = form.getAnswer();
		String question = form.getQuestion();

		List<PictureInfo> picInfoList = formInSession.getPicInfoList();
		List<String> alreadyUseQuestionList = formInSession.getAlreadyUseQuestionList();

		try {
			if (question.equals(CryptUtil.decrypt(answer, session.getId()))) {

				formInSession.setScore(formInSession.getScore() + 1);
				form.setLife(formInSession.getLife());

				if (picInfoList.size() == alreadyUseQuestionList.size()) {
					return REDIRECT_FIGHT_FIGHT;
				}

				question = fightLogic.getQuestion(picInfoList, alreadyUseQuestionList);
				alreadyUseQuestionList.add(question);
			} else {
				form.setLife(formInSession.getLife() - 1);
				if (form.getLife() < 1) {
					if (formInSession.getScore() > session.getLoginUser().getHighScore()) {

						LoginParam param = new LoginParam();
						param.setUsername(session.getLoginUser().getUsername());
						param.setHighScore(formInSession.getScore());
						fightLogic.updateHighScore(param);

						form.setNewHighScore(true);
						session.getLoginUser().setHighScore(formInSession.getScore());
					}
					form.setLoseFlag(true);
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new SystemException("Encrypt/Decrypt fail");
		} catch (SQLException e) {
			throw new SystemException("SQL exception");
		} catch (IOException e) {
			throw new SystemException("IOException");
		}

		form.setScore(formInSession.getScore());
		form.setPicInfoList(picInfoList);
		form.setAlreadyUseQuestionList(alreadyUseQuestionList);
		form.setQuestion(question);

		session.setFightForm(form);
		model.addAttribute("form", form);
		return FIGHT_JSP;
	}

	@ModelAttribute("form")
	public FightForm getForm() {
		return new FightForm();
	}

}
