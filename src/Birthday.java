import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Birthday {
    private int year, month, day;
    boolean isLeapYear = false;
    public Birthday(String IDNum){
        year = (IDNum.charAt(6) - '0')*1000 + (IDNum.charAt(7) - '0')*100 + (IDNum.charAt(8) - '0')*10 + IDNum.charAt(9) - '0';
        month = (IDNum.charAt(10) - '0')*10 + IDNum.charAt(11) - '0';
        day  = (IDNum.charAt(12) - '0')*10 + IDNum.charAt(13) - '0';
        if ((year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0))
            isLeapYear = true;
    }
    public boolean checkBirthday(){
//        if (month > 12 || month < 1 || day < 1)
//            return false;
//        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day > 31)
//            return false;
//        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
//            return false;
//        if ((month == 2) && ((isLeapYear && day > 29) || (!isLeapYear && day > 28)))
//            return false;
//        return true;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sd.setLenient(false);
            sd.parse((String.format("%4d", year) + '-' + String.format("%2d", month) + '-' + String.format("%2d", day)).replaceAll(" ", "0"));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    public String toString() {
        return (String.format("%4d", year) + '/' + String.format("%2d", month) + '/' + String.format("%2d", day)).replaceAll(" ", "0");
    }
}
