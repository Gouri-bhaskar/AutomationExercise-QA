package helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    static Date date = new Date();
    static Timestamp ts = new Timestamp(date.getTime());
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d HH_mm_ss");
    static String timeStamp = simpleDateFormat.format(ts).replace(" ", "_").replace("-", "_");
    static String[] splitedDate = timeStamp.split("_");

    public static String generateEmail() {
        return "test" + timeStamp + "@gmail.com";
    }

    public static String generateUserName() {
        return "UserName_" + timeStamp;
    }

    public static String generateFirstName() {
        return "FName_" + timeStamp;
    }

    public static String generateLastName() {
        return "LName_" + timeStamp;
    }

    public static String generatePassword() {
        return timeStamp.replace("_", "");
    }

    public static String generateAddress() {
        return "Address " + timeStamp;
    }

    public static String generateAddress2() {
        return "Address2 " + timeStamp;
    }

    public static String generateState() {
        return "N/A";
    }

    public static String generateCity() {
        return "Dhaka";
    }

    public static String generateZip() {
        return "201";
    }

    public static String generateMobile() {
        return "123456789";
    }

    public static String getCurrentYear() {
        return splitedDate[0];
    }

    public static String getCurrentMonth() {
        return splitedDate[1];
    }

    public static String getCurrentDay() {
        return splitedDate[2];
    }

    public static String generateComment() {
        return "hello, make burger extra spicy " + timeStamp;
    }
}
