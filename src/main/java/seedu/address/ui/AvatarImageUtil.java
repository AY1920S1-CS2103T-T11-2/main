package seedu.address.ui;

import javafx.scene.image.Image;
import seedu.address.commons.util.AppUtil;

/**
 * A utility class to get Avatar images.
 */
public class AvatarImageUtil {

    public static final int TOTAL_NUM = 151;
    private static final String AVATAR_PATH = "/images/avatars/%03d.png";

    private AvatarImageUtil() {} // this class should not be initiated

    public static Image get(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("id must be between 1-" + TOTAL_NUM + " inclusive");
        }
        return AppUtil.getImage(String.format(AVATAR_PATH, id));
    }
}
