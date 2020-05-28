package ua.edu.sumdu.ta.shelest.pr7;

import java.util.Iterator;

/**
 * This class describes a list that stores and works with tasks
 *
 * @author Tatyana Shelest
 * @version 1.0
 */
public abstract class AbstractTaskList implements Cloneable, Iterable<Task> {

    protected int taskCounter = 0;
    public static final String HEADER = "[EDUCTR][TA]";


    abstract public Iterator<Task> iterator();


    public AbstractTaskList clone() throws CloneNotSupportedException {
        try {
            AbstractTaskList tasks = (AbstractTaskList) super.clone();
            Iterator<Task> iter = tasks.iterator();
            Iterator<Task> iter1 = this.iterator();
            while (iter1.hasNext()) {
                iter.next().set(iter1.next());
            }
            return tasks;
        } catch (CloneNotSupportedException e) {
            System.out.println("Not supported");
            return null;
                }
        }

    /**
     * Returns a hash code value for the object
     *
     * @return int hash code
     */
    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < this.size(); i++) {
            result = result * 15 + (this.iterator().next() == null ? 0 : this.iterator().next().hashCode());
        }
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this list.
     * Lists are considered equal if their elements are equal and they
     * have the same type.
     *
     * @param o some object
     * @return true if object is equal to the list
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        AbstractTaskList tasks = (AbstractTaskList) o;
        if (this.size() != tasks.size()) {
            return false;
        }
        Iterator<Task> iter = this.iterator();
        Iterator<Task> iter1 = tasks.iterator();

        boolean result = false;
        while (iter.hasNext() && iter1.hasNext()) {
            result = (iter.next().equals(iter1.next()));
        }
        return result;
    }

    /**
     * toString method returns a string that textually represents" this list
     *
     * @return a string representation of the list
     */
    public String toString() {
        StringBuilder str = new StringBuilder(this.getClass().getSimpleName() + " [");
        Iterator<Task> iter = this.iterator();
        while (iter.hasNext()) {
            str.append("Task " + iter.next().getTitle() + ",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("]");
        return str.toString();
    }

    /**
     * size method returns the size of the list
     *
     * @return number of tasks in the list
     */
    public int size() {
        return taskCounter;
    }

    public abstract void add(Task task);

    public abstract void remove(Task task);

    /**
     * extendList method returns extended task list
     *
     * @param tasks array to extend
     * @param extension required extension size must be greater or equal to 0
     * @throws IllegalArgumentException if given extension is less than 0
     * @return extended list
     */
    public static Task[] extendList(Task[] tasks, int extension) {
        if (extension < 0) {
            throw new IllegalArgumentException("Wrong extension");
        } else {
            Task[] extendedList = new Task[tasks.length + extension];

            for (int i = 0; i < tasks.length; i ++) {
                extendedList[i] = tasks[i];
            }
            tasks = extendedList;
            return tasks;
        }
    }

    /**
     * incoming method returns an array of tasks from the list with notification time
     * between from (exclusively) and to (inclusive)
     *
     * @param from int time must not be less than 0
     * @param to int time must be greater than @param from
     * @return array of tasks
     * @throws InvalidTimeException if the specified period is invalid
     */
    public Task[] incoming(int from, int to) {
        if (from < 0 || to < from) {
            throw new InvalidTimeException();
        }
        Iterator<Task> iter = this.iterator();
        Task[] selectedTasks = new Task[0];
        int k = 0;
        while (iter.hasNext()) {
            Task task = iter.next();
            if (task.nextTimeAfter(from) >= from && task.nextTimeAfter(from) <= to) {
                Task[] tempList = extendList(selectedTasks, 1);
                tempList[k] = task;
                selectedTasks = tempList;
                k++;
            }
        }
        return selectedTasks;
    }
}
