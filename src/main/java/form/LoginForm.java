package form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import annotation.MessageConfig;
import constant.MessageConstant;
import constant.SystemConstant;

public class LoginForm extends AbstractForm {

    @NotEmpty(message = MessageConstant.MUST_NOT_EMPTY)
    @Size(max = 12, message = MessageConstant.MIN_6_MAX_12_CHARS)
    @Pattern(regexp = SystemConstant.REGEX_VALID_USERNAME, message = MessageConstant.ONLY_DIGITS_ALPHABETS)
    @MessageConfig(order = 0, value = "Username")
    private String username;

    @NotEmpty(message = MessageConstant.MUST_NOT_EMPTY)
    @MessageConfig(order = 1, value = "Password")
    private String password;

    private String submit;

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @AssertTrue(message = MessageConstant.MIN_6_MAX_12_CHARS)
    @MessageConfig(order = 2, value = "Password")
    public boolean isValidPassword() {
        return !"Register".equals(submit) || (password != null && password.length() >= 6);
    }

}
