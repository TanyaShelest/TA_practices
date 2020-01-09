package ua.edu.sumdu.ta.shelest.pr7;

import java.io.Serializable;
import java.util.Iterator;

/**
 * This is a class that works with tasks in a linked list
 *
 * @author Tatyana Shelest
 * @version 1.0
 */
public class LinkedTaskList extends AbstractTaskList implements Cloneable, Iterable<Task> {

    private Node first;
    private static int listCounter = 0;

    /**
     * Class LinkedTaskList constructor with a list counter
     */
    public LinkedTaskList () {
        listCounter++;
    }

    /**
     * Class Node describes an element of LinkedTaskList
     * which contains data (task) and link to the next element
     */
    public static class Node {
        Task task;
        Node next;

        /**
         * Class Node constructor
         *
         * @param task field with data (task)
         */
        private Node (Task task) {
            this.task = task;
        }
    }

    /**
     * An iterator for list that allows the programmer to traverse the list
     * and obtain the iterator's current position in the list
     * @return Iterator
     */
    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private Node current = first;

            public boolean hasNext() {
                return current != null;
            }

            public Task next() {
                Task temp = current.task;
                current = current.next;
                return temp;
            }

            public void remove() {

            }
        };
    }

    /**
     * add method adds a non-empty task to the LinkedTaskList
     *
     * @param task task to add must not be null
     * @throws IllegalArgumentException if the task is null
     */
    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        } else {
            Node node = new Node(task);
//            if (!(node.task.getTitle().contains(HEADER))) {
//                node.task.setTitle(String.format("%s %s", HEADER, node.task.getTitle()));
//            }
            if (first == null) {
                first = node;
            } else {
                Node current = first;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = node;
            }
            taskCounter++;
        }
    }

    /**
     * remove method removes tasks from the LinkedTaskList
     *
     * @param task task to remove must not be null
     * @throws IllegalArgumentException if the specified task is null or if the task is not in the list
     */
    @Override
    public void remove(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }
        Node current = first;
        Node previous = first;
        while (current != null) {
            if (current.task.equals(task)) {
                if (current == first) {
                    first = first.next;
                } else {
                    previous.next = current.next;
                }
                previous = current;
                current = current.next;
                taskCounter--;
            } else {
                previous = current;
                current = current.next;
            }
        }
        return;
    }
}
