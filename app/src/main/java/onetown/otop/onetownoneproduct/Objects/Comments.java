package onetown.otop.onetownoneproduct.Objects;

/**
 * Created by EasyBreezy on 12/5/2016.
 */

public class Comments {
    public int _id;
    public String currentUserName;
    public String currentTimeStamp;
    public String commentContent;

    public Comments() {

    }
    public Comments(String _currentUserName,String _currentTimeStamp, String _commentContent) {
        this.currentUserName=_currentUserName;
        this.currentTimeStamp=_currentTimeStamp;
        this.commentContent=_commentContent;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(String currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
