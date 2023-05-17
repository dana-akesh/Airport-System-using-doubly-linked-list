package DataStructure;


public class DoublyLinkedList<T extends Comparable<T>> {
    private DNode<T> head;

    public DNode<T> getHead() {
        return head;
    }

    public void setHead(DNode<T> head) {
        this.head = head;
    }

    public void insert(T data) {
        DNode<T> newNode = new DNode<>(data);
        DNode<T> prev = null, curr = head;
        for (; curr != null && curr.getData().compareTo(data) < 0;
             prev = curr, curr = curr.getNext())
            ;

        if (head == null)
            head = newNode;
        else if (curr == head) {
            newNode.setNext(head);
            head = newNode;
        } else if (curr == null) {
            newNode.setNext(curr);
            prev.setNext(newNode);
        } else {
            newNode.setNext(curr);
            prev.setNext(newNode);
        }


    }

    public void delete(T data) {
        if (head == null)
            return;
        DNode<T> prev = null, curr = head;
        for (; curr != null && curr.getData().compareTo(data) < 0; prev = curr, curr = curr.getNext()) ;

        if (curr == null)
            return;
        if (curr.getData().equals(data))
            if (curr == head)
                head = head.getNext();
            else
                prev.setNext(curr.getNext());
    }

    public void clear(){
        head = null;
    }

    public DNode<T> search(T data) {
        DNode<T> curr = head;
        while (curr != null) {
            if (curr.getData().compareTo(data) == 0)
                return curr;
            curr = curr.getNext();
        }
        return null;
    }

    public int length() {
        int n = 0;
        DNode<T> curr = head;
        while (curr != null) {
            n++;
            curr = curr.getNext();
        }
        return n;
    }

    public void traverse() {
        DNode<T> curr = head;
        System.out.print("head -> ");
        while (curr != null) {
            System.out.print(" [" + curr.getData() + "] -> ");
            curr = curr.getNext();
        }
        System.out.println("null");
    }

    public T get(int index) {
        DNode<T> curr = head;
        int counter = 0;
        while (curr != null) {
            if (index == counter)
                return curr.getData();
            counter++;
            curr = curr.getNext();
        }
        return null;
    }

    @Override
    public String toString() {
        String res = "";
        DNode<T> curr = head;
        while (curr != null) {
            res += curr.getData();
            curr = curr.getNext();
        }
        return res;
    }
}
