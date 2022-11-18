
/*
 * Visitor that visits users and user groups
 */

public class UserVisitor implements Visitor {

    @Override
    public String visit(UserTotal userTotal) {
        return String.valueOf(userTotal.getUserTotal());
    }

    @Override
    public String visit(GroupTotal groupTotal) {
        return String.valueOf(groupTotal.getGroupTotal());
    }

    @Override
    public String visit(MessageTotal messageTotal) {
        return String.valueOf(messageTotal.getMessageTotal());
    }

    @Override
    public String visit(PositiveMessage positiveMessage) {
        return positiveMessage.getPositiveMessagePercent();
    }

    /*
     * 1. count positive messages
     * 2. 4 buttons on the admin panel
     * 3. Each buttonn when clicked should send a visitor
     */

}
