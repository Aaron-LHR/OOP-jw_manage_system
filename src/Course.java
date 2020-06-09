import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Course {
    private String CID, Name;
    private Set<String> Teachers = new TreeSet<>();
    private Set<String> Students = new TreeSet<>();
    private int Capacity;
    private int NumStudents = 0;
    private int[] Time = new int[4];  //x-y周 周n第m节
    public Course(String CID, String name, String[] teachers, int capacity, String time) {
        this.CID = CID;
        this.Name = name;
        Teachers.addAll(Arrays.asList(teachers));
        this.Capacity = capacity;
        String[] temp = time.replace("[","").split("\\]|-|,");
        Time[0] = Integer.parseInt(temp[0]);
        Time[1] = Integer.parseInt(temp[1]);
        Time[2] = Integer.parseInt(temp[2]);
        Time[3] = Integer.parseInt(temp[3]);
    }

    public int getNumStudents() {
        return NumStudents;
    }

    public Set<String> getStudents() {
        return Students;
    }

    public String getCID() {
        return CID;
    }

    public String getName() {
        return Name;
    }

    public Set<String> getTeachers() {
        return Teachers;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCID(String CID) {
        this.CID = CID.toUpperCase();
    }

    public void setName(String name) {
        Name = name;
    }

    public void setTeachers(String[] teachers) {
        Teachers.clear();
        Teachers.addAll(Arrays.asList(teachers));
        PersonList personList = PersonList.getInstance();
        for (String Tno : Teachers) {
            Teacher teacher = personList.getTeacherByTno(Tno);
            if (teacher != null) {
                teacher.addCourse(CID);
            }
        }
    }

    public boolean setCapacity(int capacity) {
        if (capacity < 0) {
            return false;
        }
        else {
            Capacity = capacity;
            return true;
        }
    }

    public int[] getTime() {
        return Time;
    }

    public void addStudent(String Sno) {
        Students.add(Sno);
        NumStudents++;
    }

    public void deleteStudent(String Sno) {
        Students.remove(Sno);
        NumStudents--;
    }

    public static boolean CheckName (String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetterOrDigit(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean CheckCID(String CID) {
        Pattern p = Pattern.compile("^[bB][hH]\\d{8}$");
        Matcher m = p.matcher(CID);
        return m.matches();
    }

    public static boolean CheckTeachers (String teachers) {
        if (teachers.charAt(0) != '[' || teachers.charAt(teachers.length()-1) != ']') {
            return false;
        }
        for (int i = 1; i < teachers.length() - 1; i++) {
            if (!(Character.isDigit(teachers.charAt(i)) || teachers.charAt(i) == ',')) {
                return false;
            }
            if (teachers.charAt(i) == ',' && (teachers.charAt(i - 1) == '[' || teachers.charAt(i - 1) == ',' || teachers.charAt(i + 1) == ']')) {
                return false;
            }
        }

        if (teachers.length() == 2) {
            return true;
        }

        String[] teacher_list = teachers.substring(1, teachers.length() - 1).split(",");
        Set<String> tmp = new HashSet<String>();
        for (String tno : teacher_list) {
            if (!Teacher.checkTno(tno)) {
                return false;
            }
            if (tmp.contains(tno)) {
                return false;
            }
            else {
                tmp.add(tno);
            }
        }
        return true;
    }

    public static boolean CheckTime (String time) {
        if (!Pattern.matches("^\\[([1-9]|1[0-8])-([1-9]|1[0-8])][1-7],([1-9]|10)",time)) {
            return false;
        }
        else {
            String[] strings = time.replace("[","").split("\\]|-|,");
            int x = Integer.parseInt(strings[0]);
            int y = Integer.parseInt(strings[1]);
            return x <= y;
        }
    }

    @Override
    public String toString() {
        boolean flag = false;
        PersonList personList = PersonList.getInstance();
        StringBuilder result = new StringBuilder("CID:" + CID + ",Name:" + Name + ",Teachers:[");
        for (String Tno : Teachers) {
            if (!flag) {
                flag =true;
            }
            else {
                result.append(",");
            }
            Teacher teacher = personList.getTeacherByTno(Tno);
            if (teacher == null) {
                result.append(Tno);
            }
            else {
                result.append(teacher.getName());
            }
        }
        return  result + "],Capacity:" + NumStudents + "/" + Capacity + ",Time:[" + Time[0] + "-" + Time[1] + "]" + Time[2] + "," + Time[3];
    }
}

class NumberCheck {
    public static int isNumeric(String str) {
        for (int i = 1; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return 1; //不是数字
            }
        }
        if (str.charAt(0) == '-') {
            return 2; //是负数
        }
        if (!Character.isDigit(str.charAt(0))) {
            return 1;
        }
        return 0;
    }
}