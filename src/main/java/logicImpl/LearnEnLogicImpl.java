package logicImpl;

import java.sql.SQLException;
import java.util.List;

import logic.ILearnEnLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.ILearnEnDao;
import entity.UserInfo;

@Component
public class LearnEnLogicImpl implements ILearnEnLogic {

    @Autowired
    private ILearnEnDao homeDao;

    @Override
    public List<UserInfo> getAllUserInfo() throws SQLException {
        return homeDao.getAllUserInfo();
    }

}
