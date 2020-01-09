package ua.edu.sumdu.ta.shelest.pr7;

import java.io.Serializable;
import java.util.Iterator;

/**
 * This is a class that works with tasks in an array
 *
 * @author Tatyana Shelest
 * @version 1.0
 */
public class ArrayTaskList extends AbstractTaskList implements Cloneable, Iterable<Task> {

    private int size = 25;
    private final int extension = 25;
    public Task[] tasks = new Task[size];
    private static int listCounter = 0;

    /**
     * Class ArrayTaskList constructor with a list counter
     */
    public ArrayTaskList () {
        listCounter++;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task> () {
            int index = 0;

            public boolean hasNext() {
                return index < taskCounter;
            }

            public Task next() {
                return tasks[index++];
            }

            public void remove() {

            }
        };
    }

    /**
     * extendList method returns extended task list if current task list is full
     *
     * @return extended ArrayTaskList
     */
    private Task[] extendList () {
        Task[] extendedList = new Task[this.size() + extension];

        for (int i = 0; i < this.size(); i++) {
            extendedList[i] = tasks[i];
        }
        tasks = extendedList;
        return tasks;
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
            throw new IllegalArgumentException("An empty task is not allowed!");
        } else {
            if ((this.size() != 0) && (this.size() % size == 0)) {
                this.extendList();
            }
//            if (!(task.getTitle().contains(HEADER))) {
//                task.setTitle(String.format("%s %s", HEADER, task.getTitle()));
//            }
            tasks[this.size()] = task;
            taskCounter++;
        }
    }

    /**
     * remove method removes a task from the ArrayTaskList
     *
     * @param task task to remove must not be null
     * @throws IllegalArgumentException if the task is null
     */
    @Override
    public void remove(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("An empty task is not allowed!");
        } else {
            for (int i = 0; i < this.size(); i++) {
                if (tasks[i].equals(task)) {
                    taskCounter--;
                    for (int k = i; k < (this.size()); k++) {
                        tasks[k] = tasks[k+1];
                    }
                }
            }
        }
    }
}