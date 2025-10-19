package friday.logic;

public final class CommandResult {
    private final String feedbackToUser;
    private final boolean exit;

    public CommandResult(String feedbackToUser, boolean exit) {
        this.feedbackToUser = feedbackToUser;
        this.exit = exit;
    }
    public String getFeedbackToUser() {
        return feedbackToUser;
    }
    public boolean isExit() {
        return exit;
    }
}