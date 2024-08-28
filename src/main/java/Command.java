public abstract class Command {
    public abstract void execute(TaskHandler taskHandler, Ui ui);

    public boolean isExit() {
        return false;
    }
}
