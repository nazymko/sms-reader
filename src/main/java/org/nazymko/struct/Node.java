package org.nazymko.struct;

/**
 * Created by Andrew Nazymko
 */
public class Node {
    private String nodeValue;
    private Meta nodeMeta;
    private Node previous;
    private Node next;

    public Node(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public Node() {
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public Meta getNodeMeta() {
        return nodeMeta;
    }

    public void setNodeMeta(Meta nodeMeta) {
        this.nodeMeta = nodeMeta;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
