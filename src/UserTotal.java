import java.util.ArrayList;

/*
 * Class: total users
 * Design pattern: Visitor
 */

public class UserTotal implements AdminAnalytics {

    private ArrayList<User> userList;

    public UserTotal() {
        userList = new ArrayList<User>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getUserTotal() {
        return userList.size();
    }

    // getters and setters

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
