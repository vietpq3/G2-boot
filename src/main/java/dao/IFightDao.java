package dao;

import java.sql.SQLException;
import java.util.List;

import param.FightParam;
import param.LoginParam;
import entity.PictureInfo;
import entity.Theme;

public interface IFightDao {

    List<PictureInfo> getPicInfoList(FightParam param) throws SQLException;

    List<Theme> getAllTheme() throws SQLException;

    int updateHighScore(LoginParam param) throws SQLException;

}
