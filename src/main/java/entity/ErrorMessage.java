package entity;

import java.util.LinkedList;
import java.util.List;

public class ErrorMessage {
    List<String> errorMessageList = new LinkedList<String>();

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }

    public void setErrorMessageList(List<String> errorMessageList) {
        this.errorMessageList = errorMessageList;
    }

}
