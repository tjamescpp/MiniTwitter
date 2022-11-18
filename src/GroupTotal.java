import java.util.ArrayList;

/*
 * Class: manages the total number of groups 
 * Design pattern: Visitor
 */

public class GroupTotal implements AdminAnalytics {

    private ArrayList<UserGroup> userGroupList;

    public GroupTotal() {
        userGroupList = new ArrayList<UserGroup>();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void addGroup(UserGroup userGroup) {
        userGroupList.add(userGroup);
    }

    public int getGroupTotal() {
        return userGroupList.size();
    }

    // getters and setters

    public ArrayList<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(ArrayList<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

}
