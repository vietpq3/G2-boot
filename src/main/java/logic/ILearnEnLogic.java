package logic;

import java.sql.SQLException;
import java.util.List;

import entity.UserInfo;

public interface ILearnEnLogic {

    List<UserInfo> getAllUserInfo() throws SQLException;

}
