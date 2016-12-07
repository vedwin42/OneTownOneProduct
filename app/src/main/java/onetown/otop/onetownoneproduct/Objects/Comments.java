package onetown.otop.onetownoneproduct.Objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by EasyBreezy on 12/5/2016.
 */

public class Comments {
    public int _id;
    public String currentEmail;
    public String currentTimeStamp;
    public String commentContent;

    public Comments() {

    }
    public Comments(String _currentEmail,String _currentTimeStamp, String _commentContent) {
        this.currentEmail=_currentEmail;
        this.currentTimeStamp=_currentTimeStamp;
        this.commentContent=_commentContent;
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

    public void setCurrentEmail(Credentials currentEmail) {
        this.currentEmail = currentEmail.getEmail();
    }

    public String getCurrentTimeStamp() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date= new Date();
        return sdf.format(date);
    }


    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
