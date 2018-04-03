package common;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.UserInfo;
import form.FightForm;

@SuppressWarnings("serial")
public class SessionAccessor implements Serializable {
    private HttpSession session;
    private String LOGIN_USER = "entity.UserInfo";
    
    private final String FIGHT_FORM = "form.FightForm";
    
    public SessionAccessor(HttpServletRequest request) {
        this.session = request.getSession();
    }
    
    public String getId() {
        return session.getId();
    }
    
    public UserInfo getLoginUser() {
        return (UserInfo) session.getAttribute(LOGIN_USER);
    }
    
    public void setLoginUser(UserInfo loginUser) {
        session.setAttribute(LOGIN_USER, loginUser);
    }
    
	public FightForm getFightForm() {
		return (FightForm) session.getAttribute(FIGHT_FORM);
	}

	public void setFightForm(FightForm fightForm) {
		session.setAttribute(FIGHT_FORM, fightForm);
	}
    
}
