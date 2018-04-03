package logic;

import java.sql.SQLException;
import java.util.List;

import param.LoginParam;
import entity.UserInfo;

public interface ILoginLogic {

    List<UserInfo> checkLogin(LoginParam param) throws SQLException;

    int registerAccount(LoginParam param) throws SQLException;

}
