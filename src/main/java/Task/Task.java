package Task;

/**
 * Represents one task and its completion state.
 */
public class Task {

    private final String taskName;
    private boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String isDone() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as complete.
     */
    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + isDone() + "] " + taskName;
    }
}
