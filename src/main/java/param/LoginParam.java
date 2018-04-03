package param;

public class LoginParam {
    private String username;
    private String password;
    private Integer highScore;
    
    public Integer getHighScore() {
        return highScore;
    }
    
    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
}
