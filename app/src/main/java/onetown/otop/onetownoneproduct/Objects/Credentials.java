package onetown.otop.onetownoneproduct.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EasyBreezy on 12/5/2016.
 */

public class Credentials implements Parcelable {
    public int _id;
    public String password;
    public String email;

    public Credentials() {

    }

    public Credentials(String email,String password) {
        this.password=password;
        this.email= email;
    }

    public Credentials(int id,String email,String password) {
        this._id=id;
        this.email=email;
        this.password=password;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.password);
        dest.writeString(this.email);
    }

    protected Credentials(Parcel in) {
        this._id = in.readInt();
        this.password = in.readString();
        this.email = in.readString();
    }

    public static final Parcelable.Creator<Credentials> CREATOR = new Parcelable.Creator<Credentials>() {
        @Override
        public Credentials createFromParcel(Parcel source) {
            return new Credentials(source);
        }

        @Override
        public Credentials[] newArray(int size) {
            return new Credentials[size];
        }
    };
}
