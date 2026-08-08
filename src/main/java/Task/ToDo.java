package Task;

public class ToDo extends Task {
    public String taskName;

    public ToDo(String taskName) {
        super(taskName);
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "[T] [" + this.isDoneStatus() + "] " + taskName;
    }
}
