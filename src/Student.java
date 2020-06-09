import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String Sno;

    public String getSno() {
        return Sno;
    }

    public void selectCourse(String CID) {
        course_list.add(CID);
    }

    public void dropoutCourse(String CID) {
        course_list.remove(CID);
    }

    public boolean setSno(String sno) {
        if (sno.length() != 8) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(sno.charAt(i))) {
                return false;
            }
        }
        Sno = sno;
        return true;
    }

    @Override
    public String toString() {
        return "Name:" + name + '\n' +
                "IDNum:" + ID.toString() + '\n' +
                "SID:" + Sno + '\n' +
                "Sex:" + sex + '\n' +
                "Birthday:" + birthday.toString();
    }
}
