package daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.AbstractDao;
import dao.IHomeDao;
import entity.UserInfo;

@Component
public class HomeDaoImpl extends AbstractDao implements IHomeDao {

    @Autowired
    public HomeDaoImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public List<UserInfo> getAllUserInfo() throws SQLException {

        String sql = "select username, passwords, highScore from UserInf";

        createArgs();
        ResultSet rs = excuteQuery(sql);

        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        UserInfo userInfo = null;

        while (rs.next()) {
            userInfo = new UserInfo();
            userInfo.setUsername(rs.getString("username"));
            userInfo.setHighScore(rs.getInt("highScore"));
            userInfoList.add(userInfo);
        }

        return userInfoList;
    }

}
