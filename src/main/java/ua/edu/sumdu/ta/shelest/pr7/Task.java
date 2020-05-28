package ua.edu.sumdu.ta.shelest.pr7;


/**
 * Task is the class that describes the data type "task", which contains information
 * about the essence of the task, its status (active / inactive), notification time interval,
 * time after which it is necessary to repeat the notification about it.
 * @author Tatyana Shelest
 * @version 1.0
 */
public class Task implements Cloneable {

    private String title;
    private boolean active;
    private int time;
    private int start;
    private int end;
    private int repeat;
    private boolean repetitive;

    /**
     * Class Task constructor for non-repetitive task
     *
     * @param title is a task title (must not be null or empty)
     * @param time  is a time for a once notification (must not be less than 0)
     * @throws IllegalArgumentException if task title is null or empty or if time is less than 0
     */
    public Task(String title, int time) {
        if (title == null || title == "" || time < 0) {
            throw new IllegalArgumentException("Invalid task title or time.");
        } else {
            active = false;
            this.title = title;
            this.time = time;
        }

    }

    /**
     * Class Task constructor for repetitive task
     *
     * @param title  task title must not be null or empty
     * @param start  task notification start must be greater or equal to 0
     * @param end    task notification end must be greater or equal to start
     * @param repeat interval after which the notification of the task must be repeated.
     *               Interval must be greater or equal to 0
     * @throws IllegalArgumentException if given title or time is incorrect
     */
    public Task(String title, int start, int end, int repeat) {
        if (title == null || title == "" || (start | repeat) <= 0 || end <= start) {
            throw new IllegalArgumentException("Invalid task title or time");
        } else {
            active = false;
            this.title = title;
            this.start = start;
            this.end = end;
            this.repeat = repeat;
            repetitive = true;
        }
    }

    /**
     * getTitle method returns title of the task
     *
     * @return task title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * setTitle method sets new task title
     *
     * @param title new task title must not be null or empty
     * @throws IllegalArgumentException if the title is empty or null
     */
    public void setTitle(String title) {
        if (title == null || title == "") {
            throw new IllegalArgumentException("Invalid title");
        } else {
            this.title = title;
        }
    }

    public void set(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }
        this.time = task.time;
        this.title = task.title;
        this.active = task.active;
        this.repetitive = task.repetitive;
        this.start = task.start;
        this.end = task.end;
        this.repeat = task.repeat;
    }

    /**
     * isActive method returns task status (active or not)
     *
     * @return current status of the task
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * setActive method sets new status fot the task
     *
     * @param active active/inactive status
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * setTime method sets a time for one-time notification
     *
     * @param time task notification time must be greater or equal to 0
     * @throws InvalidTimeException if given time is less than 0
     */
    public void setTime(int time) {
        if (time <= 0) {
            throw new InvalidTimeException();
        } else if (isRepeated()) {
            this.time = time;
            this.repetitive = false;
        }
    }

    /**
     * setTime method sets the time for repetitive task
     *
     * @param start  task notification start must be greater or equal to 0
     * @param end    task notification end must be greater or equal to start
     * @param repeat interval after which the notification of the task must be repeated.
     *               Interval must be greater or equal to 0
     * @throws InvalidTimeException if given time is incorrect
     */
    public void setTime(int start, int end, int repeat) {
        if ((start | repeat) <= 0 || end <= start) {
            throw new InvalidTimeException();
        }
        this.time = start;
        this.start = start;
        this.end = end;
        this.repeat = repeat;
        this.repetitive = true;
    }

    /**
     * getTime method returns the time for once notification (non-repetitive task)
     * or task notification start (repetitive task)
     *
     * @return task notification time or task notification start
     */
    public int getTime() {
        if (isRepeated()) {
            return this.start;
        } else {
            return this.time;
        }
    }

    /**
     * getStartTime method returns task notification start for repetitive task.
     * For non-repetitive task this method returns the time for once notification.
     *
     * @return start time or once time
     */
    public int getStartTime() {
        if (isRepeated()) {
            return this.start;
        } else {
            return this.time;
        }
    }

    /**
     * getEndTime method returns task notification end for repetitive task.
     * For non-repetitive task this method returns the time for once notification.
     *
     * @return end time or once time
     */
    public int getEndTime() {
        if (isRepeated()) {
            return this.end;
        } else {
            return this.time;
        }
    }

    /**
     * getRepeatInterval method returns task notification interval for repetitive task.
     * For non-repetitive task this method returns 0.
     *
     * @return notification interval or 0
     */
    public int getRepeatInterval() {
        if (isRepeated()) {
            return this.repeat;
        } else {
            return 0;
        }
    }

    /**
     * nextTimeAfter returns the time of the next notification after the specified time.
     * For inactive task this method returns -1
     *
     * @param time some time
     * @return next notification time or -1
     */
    public int nextTimeAfter(int time) {
        if (time < 0) {
            throw new InvalidTimeException();
        }
        if (!isActive()) {
            return -1;
        }
        if (isRepeated()) {
            int result = start;
            while (result <= end) {
                if (time < result) {
                    return result;
                } else if (time == result) {
                    return result + repeat;
                }
                result += repeat;
            }
        } else if (time < this.time) {
            return this.time;
        } else {
            return -1;
        }
        return -1;
    }

    /**
     * isRepeated method defines type of the task - repetitive or non-repetitive
     *
     * @return repetitive
     */
    public boolean isRepeated () {
        return this.repetitive;
    }

    /**
     * toString method returns a description of this task
     *
     * @return string with information about the task
     */
    public String toString () {
        if (!isActive()) {
            return "Task " + '"' + title + '"' + " is inactive";
        } else if (!isRepeated() && isActive()) {
            return "Task " + '"' + title + '"' + " at " + time;
        } else if (isRepeated() && isActive()) {
            return "Task " + '"' + title + '"' + " from " + start +
                    " to " + end + " every " + repeat + " seconds";
        } else {
            return null;
        }
    }

    /**
     * equals() method returns true if the task title and time are equal
     * @param o some object to compare
     * @return true if this task and object are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;
        return active == task.active &&
                time == task.time &&
                start == task.start &&
                end == task.end &&
                repeat == task.repeat &&
                repetitive == task.repetitive &&
                title.equals(task.title);
    }

    /**
     * Returns a hash code value for the object
     *
     * @return int hash code
     */
    @Override
    public int hashCode() {
        int result = 1;

        if (isRepeated()) {
            if (isActive()) {
                result = result * 3 + start + end + repeat + title.hashCode();
            } else {
                result = result * 2 + start + end + repeat + title.hashCode();
            }
        } else {
            if (isActive()) {
                result = result * 3 + time + title.hashCode();
            } else {
                result = result * 2 + time + title.hashCode();
            }
        }
        return result;
    }

    /**
     * clone method returns deep copy of a list
     *
     * @return a clone of this instance
     * @throws CloneNotSupportedException if the class doesn't implement Cloneable interface
     */
    @Override
    public Task clone() throws CloneNotSupportedException {

        Task task = (Task) super.clone();
        task.title = this.title;
        task.time = this.time;
        task.repetitive = this.repetitive;
        task.start = this.start;
        task.end = this.end;
        task.repeat = this.repeat;
        task.active = this.active;
        return task;

    }
}
