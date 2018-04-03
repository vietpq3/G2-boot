package logicImpl;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import logic.IFightLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import param.FightParam;
import param.LoginParam;

import common.CryptUtil;

import dao.IFightDao;
import entity.PictureInfo;
import entity.Theme;
import exception.SystemException;

@Component
public class FightLogicImpl implements IFightLogic {

    @Autowired
    private IFightDao fightDao;

    @Override
    public List<PictureInfo> getPicInfoList(FightParam param) throws SQLException {
        return fightDao.getPicInfoList(param);
    }

    @Override
    public List<Theme> getAllTheme() throws SQLException {
        return fightDao.getAllTheme();
    }

    @Override
    public List<PictureInfo> getListPictureForPictureMode() throws SystemException {
        FightParam param = new FightParam();
        Theme theme = getTheme();
        param.setThemeId(theme.getThemeId());
        List<PictureInfo> picInfoList = null;
        try {
            picInfoList = fightDao.getPicInfoList(param);
        } catch (SQLException e) {
            throw new SystemException("SQL Exception");
        }
        if (picInfoList != null && picInfoList.size() > 0) {
            while (picInfoList.size() > 9) {
                Collections.shuffle(picInfoList);
                picInfoList.remove(0);
            }
        } else {
            throw new SystemException("Theme " + theme.getThemeName() + " is empty (in DB)");
        }
        return picInfoList;
    }

    @Override
    public List<PictureInfo> encryptPictureName(List<PictureInfo> picInfoList, String sessionId)
            throws UnsupportedEncodingException {
        for (PictureInfo picInfo : picInfoList) {
            picInfo.setEncryptPictureName(CryptUtil.encrypt(picInfo.getPictureName(), sessionId));
        }
        return picInfoList;
    }

    @Override
    public String getQuestion(List<PictureInfo> picInfoList, List<String> alreadyUseQuestionList) {
        Random rd = new Random();
        Collections.shuffle(picInfoList);
        String question = picInfoList.get(0).getPictureName();
        while (alreadyUseQuestionList.contains(question)) {
            question = picInfoList.get(rd.nextInt(picInfoList.size())).getPictureName();
        }

        return question;
    }

    @Override
    public int updateHighScore(LoginParam param) throws SQLException {
        return fightDao.updateHighScore(param);
    }

    private Theme getTheme() throws SystemException {
        List<Theme> themeList;
        try {
            themeList = fightDao.getAllTheme();
            Random rd = new Random();
            return themeList.get(rd.nextInt(themeList.size()));
        } catch (SQLException e) {
            throw new SystemException("SQL Exception");
        }
    }

}
