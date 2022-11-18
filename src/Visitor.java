
/*
 * Interface for the visitor pattern
 * 
 * Defines a visit method to retrieve data
 */

public interface Visitor {
    String visit(UserTotal userTotal);

    String visit(GroupTotal groupTotal);

    String visit(MessageTotal messageTotal);

    String visit(PositiveMessage positiveMessage);

}
