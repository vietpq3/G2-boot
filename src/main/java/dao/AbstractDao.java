package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class AbstractDao extends JdbcDaoSupport {

    private PreparedStatement pstm;
    private ResultSet rs = null;
    private List<Map<String, Object>> args;

    public ResultSet excuteQuery(String sql) throws SQLException {
        try {
            initStatement(sql);
            rs = pstm.executeQuery();
        } catch (CannotGetJdbcConnectionException ex) {
            throw ex;
        } catch (SQLException ex) {
            throw ex;
        }
        return rs;

    }

    public int excuteUpdate(String sql) throws SQLException {
        int result = -1;
        try {
            initStatement(sql);
            result = pstm.executeUpdate();
        } catch (CannotGetJdbcConnectionException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (SQLException ex) {
            throw ex;
        }
        return result;

    }

    protected void setArgs(final String arg) {
        this.args.add(new HashMap<String, Object>() {
            {
                put("String", arg);
            }
        });
    }

    protected void setArgs(final int arg) {
        this.args.add(new HashMap<String, Object>() {
            {
                put("int", arg);
            }
        });
    }

    protected void setArgs(final long arg) {
        this.args.add(new HashMap<String, Object>() {
            {
                put("long", arg);
            }
        });
    }

    protected void setArgs(final double arg) {
        this.args.add(new HashMap<String, Object>() {
            {
                put("double", arg);
            }
        });
    }

    protected void createArgs() {
        this.args = new ArrayList<Map<String, Object>>();
    }

    protected void initStatement(String sql) throws CannotGetJdbcConnectionException, SQLException {
        pstm = this.getConnection().prepareStatement(sql);
        int index = 1;
        for (Map<String, Object> map : args) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                switch (entry.getKey()) {
                    case "int":
                        pstm.setInt(index++, (int) entry.getValue());
                        break;

                    case "long":
                        pstm.setLong(index++, (long) entry.getValue());
                        break;

                    case "double":
                        pstm.setDouble(index++, (double) entry.getValue());
                        break;

                    default:
                        pstm.setString(index++, (String) entry.getValue());
                        break;
                }
            }
        }
    }
}
