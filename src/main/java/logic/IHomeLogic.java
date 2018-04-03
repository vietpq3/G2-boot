package logic;

import java.sql.SQLException;
import java.util.List;

import entity.UserInfo;

public interface IHomeLogic {

    List<UserInfo> getAllUserInfo() throws SQLException;

}
