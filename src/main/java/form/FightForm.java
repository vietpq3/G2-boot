package form;

import java.util.List;

import entity.PictureInfo;

public class FightForm {
    private String gameMode;
    private String answer;
    private String question;
    private List<PictureInfo> picInfoList;
    private List<String> alreadyUseQuestionList;
    private Integer life;
    private int score;
    private boolean loseFlag = false;
    private boolean isNewHighScore = false;
    
    public boolean isNewHighScore() {
        return isNewHighScore;
    }
    
    public void setNewHighScore(boolean isNewHighScore) {
        this.isNewHighScore = isNewHighScore;
    }
    
    public boolean isLoseFlag() {
        return loseFlag;
    }
    
    public void setLoseFlag(boolean loseFlag) {
        this.loseFlag = loseFlag;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public Integer getLife() {
        return life;
    }
    
    public void setLife(Integer life) {
        this.life = life;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public List<PictureInfo> getPicInfoList() {
        return picInfoList;
    }
    
    public void setPicInfoList(List<PictureInfo> picInfoList) {
        this.picInfoList = picInfoList;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public List<String> getAlreadyUseQuestionList() {
        return alreadyUseQuestionList;
    }
    
    public void setAlreadyUseQuestionList(List<String> alreadyUseQuestionList) {
        this.alreadyUseQuestionList = alreadyUseQuestionList;
    }
    
    public String getGameMode() {
        return gameMode;
    }
    
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
    
}
