package dao;

import java.sql.SQLException;
import java.util.List;

import entity.UserInfo;

public interface IHomeDao {

    List<UserInfo> getAllUserInfo() throws SQLException;

}
