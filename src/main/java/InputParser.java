public class InputParser {
    enum Command {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, HELP, DELETE;

        public static Command getCommand(String command) throws IllegalGingerCommandException {
            try {
                return Command.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalGingerCommandException(
                        "Oh no! This command does not exist. Try again or enter help for a list of commands.");
            }
        }
    }

    public static Command parse(String input) throws IllegalGingerCommandException {
        String[] inputParts = input.split(" ", 2);
        return Command.getCommand(inputParts[0].trim());
    }
}
