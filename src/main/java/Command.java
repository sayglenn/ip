public abstract class Command {
    public abstract void execute(TaskHandler taskHandler, Ui ui) throws IllegalGingerArgumentException;

    public boolean isExit() {
        return false;
    }
}
