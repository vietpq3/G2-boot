package dao;

import java.sql.SQLException;
import java.util.List;

import entity.UserInfo;

public interface ILearnEnDao {

    List<UserInfo> getAllUserInfo() throws SQLException;

}
