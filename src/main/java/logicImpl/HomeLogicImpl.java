package logicImpl;

import java.sql.SQLException;
import java.util.List;

import logic.IHomeLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.IHomeDao;
import entity.UserInfo;

@Component
public class HomeLogicImpl implements IHomeLogic {

    @Autowired
    private IHomeDao homeDao;

    @Override
    public List<UserInfo> getAllUserInfo() throws SQLException {
        return homeDao.getAllUserInfo();
    }

}
