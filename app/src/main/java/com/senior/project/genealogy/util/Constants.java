package com.senior.project.genealogy.util;

public class Constants {

    public static String BASE_URL = "https://genealogysystem.herokuapp.com";

    public static String SHARED_PREFERENCES_NAME = "genealogy";

    public final static int PERMISSION_CAMERA = 0;

    public final static int REQUEST_CAMERA = 1;

    static final String IMAGE_FOLDER = "Genealogy";

    public static class SHARED_PREFERENCES_KEY {
        public static String TOKEN = "token";
        public static String USERNAME = "username";
        public static String PASSWORD = "password";
        public static String AVATAR = "avatar";
        public static String FULLNAME = "fullname";
        public static String DEVICE_ID = "device_id";
    }

    /**
     * Empty String
     */
    public static String EMPTY_STRING = "";

    public class HTTPCodeResponse {
        public static final int SUCCESS = 0;
        public static final int OBJECT_EXISTED = 302;
        public static final int OBJECT_NOT_FOUND = 404;
        public static final int UNAUTHORIZED = 401;
    }

    public class ROLE {
        public static final int OWNER_ROLE = 1;
        public static final int EDITOR_ROLE = 2;
        public static final int MEMBER_ROLE = 3;
        public static final int GUEST_ROLE = 4;
        public static final int WAITING = 5;
    }

    public class RELATIVE_TYPE {
        public static final String SON = "Son";
        public static final String DAUGHTER = "Daughter";
        public static final String BROTHER = "Brother";
        public static final String SISTER = "Sister";
        public static final String HUSBAND = "Husband";
        public static final String WIFE = "Wife";
    }

    public static final int DOUBLE_CLICK_TIME_DELTA = 400;

    public class NODE_TYPE {
        public static final int FIRST_MAN_SINGLE = 1;
        public static final int FIRST_MAN_MARRIED = 2;
        public static final int MAN_SINGLE = 3;
        public static final int MAN_MARRIED = 4;
        public static final int WOMAN_SINGLE = 5;
        public static final int WOMAN_MARRIED = 6;
        public static final int PARTNER = 7;
    }

    public class NOTIFICATION_TYPE {
        public static final int MEMBER_JOIN = 1;
        public static final int ACCEPT_JOIN = 2;
        public static final int DEATH_ANNIVERSARY = 3;
        public static final int BIRTHDAY_PARTY = 4;
        public static final int FAMILY_ACTIVITIES = 5;
    }
}
