package app.pages;

import app.user.*;

public class PageFactory {
        public enum PageType {
            LIKE,
            HOME,
            ARTIST,
            HOST
        }

        public static Page createPage(PageType type, UserAbstract user) {
            if(type != null)
            return switch (type) {
                case LIKE -> new LikedContentPage((User) user);
                case HOME -> new HomePage((User) user);
                case ARTIST -> new ArtistPage((Artist) user);
                case HOST -> new HostPage((Host) user);
            };
            return null;
        }

}
