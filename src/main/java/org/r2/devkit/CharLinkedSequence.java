package org.r2.devkit;

/**
 * 以链表形式实现字符串快速修改
 * Thread unsafe
 *
 * @author ruan4261
 */
public class CharLinkedSequence implements Appendable {

    private Node head;

    private Node tail;

    private int length;

    public CharLinkedSequence() {
        this.length = 0;
    }

    public CharLinkedSequence(String string) {
        this.length = string.length();
        if (this.length == 0) return;
        this.head = new Node(string.charAt(0));

        Node prev = head;
        Node curr;
        for (int i = 1; i < length; i++) {
            curr = new Node(string.charAt(i));
            prev.next = curr;
            curr.prev = prev;
            prev = curr;
        }
        this.tail = prev;
    }

    public int length() {
        return this.length;
    }

    @Override
    public String toString() {
        char[] chars = new char[this.length];
        Node curr = head;
        for (int i = 0; i < length; i++) {
            chars[i] = curr.character;
            curr = curr.next;
        }
        return new String(chars);
    }

    /**
     * 接口函数返回值为null时代表不进行任何修改
     */
    public void replace(CharEditor editor) {
        Node curr = new Node('\0');
        curr.next = head;

        while (curr.next != null) {
            curr = curr.next;
            char[] newp = editor.edit(curr.character);

            if (newp == null) {
                // do nothing
            } else if (newp.length == 0) {
                // delete
                curr.prev = curr.next;
                this.length--;
            } else if (newp.length == 1) {
                curr.character = newp[0];
            } else {
                // newp.length > 1
                this.length += newp.length - 1;
                curr.character = newp[0];

                Node incr;
                for (int i = 1; i < newp.length; i++) {
                    incr = new Node(newp[i]);
                    incr.next = curr.next;
                    curr.next = incr;
                    incr.prev = curr;
                    curr = incr;
                }
            }
        }
    }

    /**
     * 从头部开始删除指定的长度
     */
    public int removeHead(int len) {
        Assert.judge(() -> len < 0 || len > this.length);
        for (int i = 0; i < len; i++) {
            head = head.next;
        }
        head.prev = null;
        this.length -= len;
        return this.length;
    }

    /**
     * 从尾部开始删除指定的长度
     */
    public int removeTail(int len) {
        Assert.judge(() -> len < 0 || len > this.length);
        for (int i = 0; i < len; i++) {
            tail = tail.prev;
        }
        tail.next = null;
        this.length -= len;
        return this.length;
    }

    @Override
    public Appendable append(CharSequence csq) {
        return this.append(csq, 0, csq.length());
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) {
        Assert.legalOffset(csq, start);
        Assert.judge(() -> end > csq.length(), "Series length is " + csq.length() + ", but end offset is " + end);
        for (int i = start; i < end; i++) {
            this.append(csq.charAt(i));
        }
        return this;
    }

    @Override
    public Appendable append(char c) {
        Node curr = new Node(c);
        if (this.length == 0) {
            head = tail = curr;
            this.length = 1;
            return this;
        }

        this.tail.next = new Node(c);
        this.tail.next.prev = this.tail;
        this.tail = this.tail.next;
        this.length++;
        return this;
    }

    @FunctionalInterface
    public interface CharEditor {

        char[] edit(char c);

    }

    private static final class Node {
        private char character;
        private Node prev;
        private Node next;

        public Node(char character) {
            this.character = character;
        }
    }
}
