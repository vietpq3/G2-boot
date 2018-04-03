package entity;

public class PictureInfo {
    private int pictureId;
    private String url;
    private String pictureName;
    private String encryptPictureName;
    private int themeId;
    
    public String getEncryptPictureName() {
        return encryptPictureName;
    }
    
    public void setEncryptPictureName(String encryptPictureName) {
        this.encryptPictureName = encryptPictureName;
    }
    
    public int getPictureId() {
        return pictureId;
    }
    
    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
    
    public int getThemeId() {
        return themeId;
    }
    
    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getPictureName() {
        return pictureName;
    }
    
    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}
