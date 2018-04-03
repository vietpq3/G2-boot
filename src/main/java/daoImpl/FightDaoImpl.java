package daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import param.FightParam;
import param.LoginParam;
import dao.AbstractDao;
import dao.IFightDao;
import entity.PictureInfo;
import entity.Theme;

@Component
public class FightDaoImpl extends AbstractDao implements IFightDao {

    @Autowired
    public FightDaoImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public List<Theme> getAllTheme() throws SQLException {
        String sql = "select * from themes";
        createArgs();
        ResultSet rs = excuteQuery(sql);
        List<Theme> themeList = new ArrayList<Theme>();
        Theme element = null;
        while (rs.next()) {
            element = new Theme();
            element.setThemeId(rs.getInt("themeId"));
            element.setThemeName(rs.getString("themeName"));
            themeList.add(element);
        }
        return themeList;
    }

    @Override
    public List<PictureInfo> getPicInfoList(FightParam param) throws SQLException {
        String sql = "select * from pictureInfo where themeId = ?";
        createArgs();
        setArgs(param.getThemeId());
        ResultSet rs = excuteQuery(sql);
        List<PictureInfo> picInfoList = new ArrayList<PictureInfo>();
        PictureInfo picInfo = null;
        while (rs.next()) {
            picInfo = new PictureInfo();
            picInfo.setPictureId(rs.getInt("pictureId"));
            picInfo.setPictureName(rs.getString("pictureName"));
            picInfo.setUrl(rs.getString("url"));
            picInfo.setThemeId(rs.getInt("themeId"));
            picInfoList.add(picInfo);
        }
        return picInfoList;
    }

    @Override
    public int updateHighScore(LoginParam param) throws SQLException {
        String sql = "update userInf set highScore = ? where username = ?";

        createArgs();
        setArgs(param.getHighScore());
        setArgs(param.getUsername());

        int count = excuteUpdate(sql);

        return count;
    }

}
