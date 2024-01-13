package app.pages;

import app.user.User;

public final class PreviousPage implements Command {

    private User user;

    public PreviousPage(final User user) {
        this.user = user;
    }
    /**
     * Used to navigate within a sequence of pages, in this case to the previous page.
     */
    public void execute() {
        user.previousPage();
    }
}
