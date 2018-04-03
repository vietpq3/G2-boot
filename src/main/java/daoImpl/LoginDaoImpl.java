package daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import param.LoginParam;
import dao.AbstractDao;
import dao.ILoginDao;
import entity.UserInfo;

@Component
public class LoginDaoImpl extends AbstractDao implements ILoginDao {

    @Autowired
    public LoginDaoImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public List<UserInfo> checkLogin(LoginParam param) throws SQLException {
        String sql = "select * from userInf where username = ? and passwords = ?";
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        createArgs();
        setArgs(param.getUsername());
        setArgs(param.getPassword());
        ResultSet rs = excuteQuery(sql);

        UserInfo userInfo = null;
        while (rs.next()) {
            userInfo = new UserInfo();
            userInfo.setUsername(rs.getString("username"));
            userInfo.setPassword(rs.getString("passwords"));
            userInfo.setHighScore(rs.getInt("highScore"));
            userInfoList.add(userInfo);
        }

        return userInfoList;
    }

    @Override
    public int registerAccount(LoginParam param) throws SQLException {
        String sql = "insert into userInf(username, passwords, authority_id, highScore) values(?, ?, 2, 0)";

        createArgs();
        setArgs(param.getUsername());
        setArgs(param.getPassword());

        int count = excuteUpdate(sql);
        return count;
    }
}
