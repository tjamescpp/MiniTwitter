import java.util.ArrayList;

public class MessageTotal implements AdminAnalytics {

    ArrayList<String> messageList;

    public MessageTotal() {
        messageList = new ArrayList<>();
    }

    public void addMessage(String message) {
        messageList.add(message);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getMessageTotal() {
        return messageList.size();
    }

    public ArrayList<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<String> messageList) {
        this.messageList = messageList;
    }

}
