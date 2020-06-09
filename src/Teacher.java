import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Teacher extends Person {
    private String Tno;

    public boolean setTno(String tno) {
        if (checkTno(tno)) {
            Tno = tno;
            CourseList courseList = CourseList.getInstance();
            for (Course course : courseList.getCourse_list()) {
                for (String t_no : course.getTeachers()) {
                    if (t_no.equals(Tno)) {
                        course_list.add(course.getCID());
                        break;
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean checkTno(String tno) {
        if (tno.length() != 5) {
            return false;
        }
        for (int i = 0; i < 5; i++) {
            if (!Character.isDigit(tno.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String getTno() {
        return Tno;
    }

    public void addCourse(String CID) {
        course_list.add(CID);
    }

    public void deleteCourse(String CID) {
        course_list.remove(CID);
    }

    public Set<String> getCourse_list() {
        return course_list;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return "Name:" + name + '\n' +
                "IDNum:" + ID.toString() + '\n' +
                "TID:" + Tno + '\n' +
                "Sex:" + sex + '\n' +
                "Birthday:" + birthday.toString();
    }
}
