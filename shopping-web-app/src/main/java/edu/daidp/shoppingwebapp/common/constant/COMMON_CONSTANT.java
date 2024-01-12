package edu.daidp.shoppingwebapp.common.constant;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class COMMON_CONSTANT {
    public static class SEX {
        public static final String MALE = "MALE";
        public static final String FEMALE = "FEMALE";
    }

    public static class PARK_STATUS {
        public static final String AVAILABLE = "AVAILABLE";
        public static final String RESERVED = "RESERVED";
        public static final String OPEN = "OPEN";
        public static final String CLOSED = "CLOSED";

    }
    public static class DATE_FORMATTER {
        public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
        public static final SimpleDateFormat FORMAT_MM_dd_yyyy = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        public static final DateTimeFormatter FORMAT_HH_mm_ss = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    public static class APP_STATUS {
        public static final class SUCCESS{
            public static final int CODE = 2200;
            public static final String MESSAGE = "SUCCESS";
        }
        public static final class NO_DATA_FOUND {
            public static final int CODE = 1100;

            public static final String MESSAGE = "No data found";

        }
        public static final class DUPLICATE_DATA {
            public static final int CODE = 1200;

            public static final String MESSAGE = "Duplicate data";

        }

        public static final class AUTHENTICATION_ERROR {
            public static final int CODE = 1300;

            public static final String MESSAGE = "Authentication error";

        }

        public static final class DEFAULT_EXCEPTION {
            public static final int CODE = 1000;
        }

        public static final class VALIDATE_EXCEPTION {
            public static final int CODE = 1200;
            public static final String MESSAGE = "Validation Failed";
        }
    }

}
