package onetown.otop.onetownoneproduct.Objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by EasyBreezy on 12/5/2016.
 */

public class Comments{
    public int _id;
    public String currentEmail;
    public String currentTimeStamp;
    public String commentContent;
    public String currentUser;
    public int otop_id;

    public Comments() {

    }
    public Comments(String _currentEmail,String _currentTimeStamp, String _commentContent,String currentUser,int otop_id) {
        this.currentEmail=_currentEmail;
        this.currentTimeStamp=_currentTimeStamp;
        this.commentContent=_commentContent;
        this.currentUser=currentUser;
        this.otop_id=otop_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(String currentEmail) {
        this.currentEmail = currentEmail;
    }

    public String getCurrentTimeStamp() {
       return currentTimeStamp;
    }

    public void setCurrentTimeStamp(String timeStamp) {
        currentTimeStamp= timeStamp;
    }


    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public int getOtop_id() {
        return otop_id;
    }

    public void setOtop_id(int otop_id) {
        this.otop_id = otop_id;
    }
}
