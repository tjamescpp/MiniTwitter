import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PositiveMessage implements AdminAnalytics {

    ArrayList<String> positiveMessages;
    List<String> tokens;
    MessageTotal messageTotal;
    String positiveMessagePercent;

    public PositiveMessage(MessageTotal messageTotal) {
        positiveMessages = new ArrayList<>();
        tokens = new ArrayList<String>();
        addPositiveWords();
        this.messageTotal = messageTotal;
    }

    public void findPositiveWords(ArrayList<String> messageList) {

        String patternString = "\\b(" + String.join("|", tokens) + ")\\b";
        Pattern pattern = Pattern.compile(patternString);

        for (int i = 0; i < messageList.size(); i++) {
            String message = messageList.get(i).toLowerCase();
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                positiveMessages.add(matcher.group(1));
            }
        }

        NumberFormat formatter = new DecimalFormat("#0.00");
        setPositiveMessagePercent(formatter.format((double) positiveMessages.size() / (double) messageList.size()));

    }

    public void addPositiveWords() {
        tokens.add("good");
        tokens.add("great");
        tokens.add("excellent");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getPositiveMessagePercent() {
        return positiveMessagePercent;
    }

    public void setPositiveMessagePercent(String percent) {
        this.positiveMessagePercent = percent;
    }

}
