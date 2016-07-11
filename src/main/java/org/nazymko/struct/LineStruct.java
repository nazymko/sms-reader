package org.nazymko.struct;

/**
 * Created by Andrew Nazymko
 */
public class LineStruct {
    private Node start;

    public LineStruct(Node start) {
        this.start = start;
    }

    public void add(Node newNode) {
        start.getPrevious().setNext(newNode);
        start.setPrevious(newNode);
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return start.getPrevious();
    }
}
