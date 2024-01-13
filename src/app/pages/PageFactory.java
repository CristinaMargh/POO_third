package app.pages;

import app.user.UserAbstract;
import app.user.Artist;
import app.user.User;
import app.user.Host;

public class PageFactory {
        public enum PageType {
            LIKE,
            HOME,
            ARTIST,
            HOST
        }
        public PageFactory() {

        }

    /**
     * Used to create a Page
     * @param type is the type of the page we are creating
     * @param user the user for who we are creating the page
     * @return the created page
     */
        public static  Page createPage(final PageType type, final UserAbstract user) {
            if (type != null) {
                return switch (type) {
                case LIKE -> new LikedContentPage((User) user);
                case HOME -> new HomePage((User) user);
                case ARTIST -> new ArtistPage((Artist) user);
                case HOST -> new HostPage((Host) user);
                };
            }
            return null;
        }
    }
