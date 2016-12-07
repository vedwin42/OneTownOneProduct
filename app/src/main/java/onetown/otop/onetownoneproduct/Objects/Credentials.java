package onetown.otop.onetownoneproduct.Objects;

/**
 * Created by EasyBreezy on 12/5/2016.
 */

public class Credentials {
    public int _id;
    public String password;
    public String email;

    public Credentials() {

    }

    public Credentials(String password,String email) {
        this.password=password;
        this.email= email;
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
}
