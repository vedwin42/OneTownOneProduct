package onetown.otop.onetownoneproduct.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by EasyBreezy on 12/5/2016.
 */

public class Comments implements Parcelable {
    public int _id;
    public String currentTimeStamp;
    public String commentContent;
    public LocationsData locationsData;
    public Credentials credentials;

    public Comments() {
        super();
    }

    public Comments( String currentTimeStamp, String commentContent, LocationsData locationsData, Credentials credentials) {
        this.currentTimeStamp = currentTimeStamp;
        this.commentContent = commentContent;
        this.locationsData = locationsData;
        this.credentials = credentials;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public LocationsData getLocationsData() {
        return locationsData;
    }

    public void setLocationsData(LocationsData locationsData) {
        this.locationsData = locationsData;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public static Creator<Comments> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.currentTimeStamp);
        dest.writeString(this.commentContent);
        dest.writeParcelable(this.locationsData, flags);
        dest.writeParcelable(this.credentials, flags);
    }

    protected Comments(Parcel in) {
        this._id = in.readInt();
        this.currentTimeStamp = in.readString();
        this.commentContent = in.readString();
        this.locationsData = in.readParcelable(LocationsData.class.getClassLoader());
        this.credentials = in.readParcelable(Credentials.class.getClassLoader());
    }

    public static final Parcelable.Creator<Comments> CREATOR = new Parcelable.Creator<Comments>() {
        @Override
        public Comments createFromParcel(Parcel source) {
            return new Comments(source);
        }

        @Override
        public Comments[] newArray(int size) {
            return new Comments[size];
        }
    };
}
