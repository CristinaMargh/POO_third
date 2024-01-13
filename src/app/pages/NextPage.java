package app.pages;

import app.user.User;

public final class NextPage implements Command {

    private User user;

    public NextPage(final User user) {
        this.user = user;
    }

    /**
     * Used to navigate within a sequence of pages, in this case to the next page.
     */
    public void execute() {
        user.nextPage();
    }
}
