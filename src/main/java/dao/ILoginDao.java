package dao;

import java.sql.SQLException;
import java.util.List;

import param.LoginParam;
import entity.UserInfo;

public interface ILoginDao {
    public List<UserInfo> checkLogin(LoginParam param) throws SQLException;

    public int registerAccount(LoginParam param) throws SQLException;
}
