package logic;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import param.FightParam;
import param.LoginParam;
import entity.PictureInfo;
import entity.Theme;
import exception.SystemException;

public interface IFightLogic {

    List<PictureInfo> getPicInfoList(FightParam param) throws SQLException;

    List<Theme> getAllTheme() throws SQLException;

    List<PictureInfo> getListPictureForPictureMode() throws SystemException;

    List<PictureInfo> encryptPictureName(List<PictureInfo> picInfoList, String id) throws UnsupportedEncodingException;

    String getQuestion(List<PictureInfo> picInfoList, List<String> alreadyUseQuestionList);

    int updateHighScore(LoginParam param) throws SQLException;

}
