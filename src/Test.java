import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Test {
    public static void main(String[] qwe) {
        PersonList personList = PersonList.getInstance();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            String[] args = input.split(" ");
            switch (args[0]) {
                case "QUIT":
                    return;
                case "login":
                    if (args.length != 4) {
                        System.out.println("Error:input illegal.");
                    }
                    else {
                        switch (args[1]) {
                            case "-t":
                                Teacher teacher = personList.login_teacher(args[2], args[3]);
                                if (teacher == null) {
                                    System.out.println("Error:Login Error. Your ID or password maybe wrong.");
                                }
                                else {
                                    System.out.println("Login success.");
                                    if (secondLevelCommand(teacher, scan)) {
                                        return;
                                    }
                                }
                                break;
                            case "-s":
                                Student student = personList.login_student(args[2], args[3]);
                                if (student == null) {
                                    System.out.println("Error:Login Error. Your ID or password maybe wrong.");
                                }
                                else {
                                    System.out.println("Login success.");
                                    if (secondLevelCommand(student, scan)) {
                                        return;
                                    }
                                }
                                break;
                            default:
                                System.out.println("Error:input illegal.");
                        }
                    }
                    break;
                case "SUDO":
                    if (sudo(scan, personList)) {
                        return;
                    }
                    break;
                default:
                    System.out.println("Error:input illegal.");
            }
        }
        scan.close();
    }


    private static boolean secondLevelCommand(Person person, Scanner scan) {
        CourseList courseList = CourseList.getInstance();
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            String[] args = input.split(" ");
            switch (args[0]) {
                case "chgpw":
                    if (args.length != 3) {
                        System.out.println("Error:input illegal.");
                    }
                    else if (!Person.checkPassword(args[1])) {
                        System.out.println("Error:Password illegal.");
                    }
                    else if (!args[1].equals(args[2])) {
                        System.out.println("Error:The password you entered must be the same as the former one.");
                    }
                    else {
                        person.setPassword(args[1]);
                        System.out.println("Password changed successfully.");
                    }
                    break;
                case "gc":
                    if (args.length < 2) {
                        System.out.println("Error:input illegal.");
                    }
                    else {
                        switch (args[1]) {
                            case "-id":
                                if (args.length != 3) {
                                    System.out.println("Error:input illegal.");
                                }
                                else {
                                    Course course = courseList.getCourseById(args[2]);
                                    if (course == null) {
                                        System.out.println("Error:Course does not exist.");
                                    }
                                    else {
                                        System.out.println(course.toString());
                                    }
                                }
                                break;
                            case "-key":
                                if (args.length != 5 || NumberCheck.isNumeric(args[3]) != 0 || NumberCheck.isNumeric(args[4]) != 0) {
                                    System.out.println("Error:input illegal.");
                                }
                                else {
                                    int n = Integer.parseInt(args[3]);
                                    int m = Integer.parseInt(args[4]);
                                    List<Course> course_list = courseList.getCoursesByKeyword(args[2]);
                                    OutputGcByKey(scan, n, m, course_list);
                                }
                                break;
                            case "-all":
                                if (args.length != 4 || NumberCheck.isNumeric(args[2]) != 0 || NumberCheck.isNumeric(args[3]) != 0) {
                                    System.out.println("Error:input illegal.");
                                }
                                else {
                                    int n = Integer.parseInt(args[2]);
                                    int m = Integer.parseInt(args[3]);
                                    List<Course> course_list = courseList.getCourse_list();
                                    OutputGcByKey(scan, n, m, course_list);
                                }
                                break;
                            default:
                                System.out.println("Error:input illegal.");
                        }
                    }
                    break;
                case "myinfo":
                    System.out.println(person.toString());
                    break;
                case "myc":
                    if (args.length != 3 || NumberCheck.isNumeric(args[1]) != 0 || NumberCheck.isNumeric(args[2]) != 0) {
                        System.out.println("Error:input illegal.");
                    }
                    else {
                        int n = Integer.parseInt(args[1]);
                        int m = Integer.parseInt(args[2]);
                        List<Course> course_list = new ArrayList<> ();
                        for (String CID : person.getCourse_list()) {
                            course_list.add(courseList.getCourseById(CID));
                        }
                        OutputGcByKey(scan, n, m, course_list);
                    }
                    break;
                case "clist":
                    if (person instanceof Teacher) {
                        Course course = courseList.getCourseById(args[1]);
                        if (course == null) {
                            System.out.println("Error:Course does not exist.");
                        }
                        else if (args.length != 4 || NumberCheck.isNumeric(args[2]) != 0 || NumberCheck.isNumeric(args[3]) != 0) {
                            System.out.println("Error:input illegal.");
                        }
                        else {
                            int n = Integer.parseInt(args[2]);
                            int m = Integer.parseInt(args[3]);
                            OutputClist(scan, n, m, course.getStudents());
                        }
                    }
                    else {
                        System.out.println("Error:input illegal.");
                    }
                    break;
                case "getc":
                    boolean flag = false;
                    if (person instanceof Student) {
                        String CID = args[1];
                        Student student = (Student) person;
                        Course course = courseList.getCourseById(CID);
                        if (course == null) {
                            System.out.println("Error:Course does not exist.");
                            break;
                        }
                        int[] SelectingTime = courseList.getCourseById(CID).getTime();
                        for (String cid : student.getCourse_list()) {
                            if (cid.equals(CID)) {
                                System.out.println("Error:The course has been selected.");
                                flag = true;
                                break;
                            }
                            int[] SelectedTime = courseList.getCourseById(cid).getTime();
                            if (SelectingTime[2] == SelectedTime[2] && SelectingTime[3] == SelectedTime[3]) {
                                if ((SelectingTime[0] >= SelectedTime[0] && SelectingTime[0] <= SelectedTime[1]) || (SelectingTime[1] >= SelectedTime[0] && SelectingTime[1] <= SelectedTime[1]) || (SelectingTime[0] <= SelectedTime[0] && SelectingTime[1] >= SelectedTime[1])) {
                                    System.out.println("Error:Course time conflict.");
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (flag) {
                            break;
                        }
                        if (course.getNumStudents() == course.getCapacity()) {
                            System.out.println("Error:The course is full.");
                        }
                        else {
                            student.selectCourse(CID);
                            course.addStudent(student.getSno());
                            System.out.println("Course chosen success.");
                        }
                    }
                    else {
                        System.out.println("Error:input illegal.");
                    }
                    break;
                case "dropc":
                    flag = false;
                    if (person instanceof Student) {
                        String CID = args[1];
                        Student student = (Student) person;
                        Course course = courseList.getCourseById(CID);
                        if (course == null) {
                            System.out.println("Error:Course does not exist.");
                            break;
                        }
                        for (String cid : student.getCourse_list()) {
                            if (cid.equals(CID)) {
                                student.dropoutCourse(CID);
                                course.deleteStudent(student.getSno());
                                System.out.println("Drop out successful.");
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            break;
                        }
                        System.out.println("Error:The course has not been selected.");
                    }
                    else {
                        System.out.println("Error:input illegal.");
                    }
                    break;
                case "DROPOUT":
                    if (person instanceof Student) {
                        Student student = (Student)person;
                        if (!args[1].equals(args[2])) {
                            System.out.println("Error:The password you entered must be the same as the former one.");
                        }
                        else if (!args[1].equals(student.getPassword())) {
                            System.out.println("Error:Password illegal.");
                        }
                        else {
                            for (String cid : student.getCourse_list()) {
                                courseList.getCourseById(cid).deleteStudent(student.getSno());
                            }
                            PersonList.getInstance().deleteStudent(student);
                            System.out.println("Congratulations, drop out successfully.");
                            return false;
                        }
                    }
                    else {
                        System.out.println("Error:input illegal.");
                    }
                    break;
                case "back":
                    return false;
                case "QUIT":
                    return true;
                default:
                    System.out.println("Error:input illegal.");
            }
        }
        return false;
    }

    public static boolean sudo(Scanner scan, PersonList personList) {
        CourseList courseList = CourseList.getInstance();
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            String[] args = input.split(" ");
            switch (args[0]) {
                case "np":
                    if (args.length != 5) {
                        System.out.println("Error:input illegal.");
                    }
                    else {
                        switch (args[1]) {
                            case "-t":
                                Teacher teacher = new Teacher();
                                if (!teacher.setID(args[3])) {
                                    System.out.println("Error:ID illegal.");
                                }
                                else if (personList.getID_list().contains(args[3].toUpperCase())) {
                                    System.out.println("Error:ID exists.");
                                }
                                else if (!teacher.setName(args[2])) {
                                    System.out.println("Error:Name illegal.");
                                }
                                else if (!teacher.setTno(args[4])) {
                                    System.out.println("Error:TID illegal.");
                                }
                                else if (personList.getTno_list().contains(args[4])) {
                                    System.out.println("Error:TID exists.");
                                }
                                else {
                                    teacher.init();
                                    personList.addTeacher(teacher);
                                    System.out.println("Add teacher success.");
                                }
                                break;
                            case "-s":
                                Student student = new Student();
                                if (!student.setID(args[3])) {
                                    System.out.println("Error:ID illegal.");
                                }
                                else if (personList.getID_list().contains(args[3].toUpperCase())) {
                                    System.out.println("Error:ID exists.");
                                }
                                else if (!student.setName(args[2])) {
                                    System.out.println("Error:Name illegal.");
                                }
                                else if (!student.setSno(args[4])) {
                                    System.out.println("Error:SID illegal.");
                                }
                                else if (personList.getSno_list().contains(args[4])) {
                                    System.out.println("Error:SID exists.");
                                }
                                else {
                                    student.init();
                                    personList.addStudent(student);
                                    System.out.println("Add student success.");
                                }
                                break;
                            default:
                                System.out.println("Error:input illegal.");
                        }
                    }
                    break;
                case "udc":
                    if (args.length != 4) {
                        System.out.println("Error:input illegal.");
                    }
                    else {
                        switch (args[2]) {
                            case "-n" :
                                Test.OutputUDC(courseList.modifyCourseName(args[1], args[3]));
                                break;
                            case "-c" :
                                Test.OutputUDC(courseList.modifyCourseCapacity(args[1], args[3]));
                                break;
                            case "-t" :
                                Test.OutputUDC(courseList.modifyCourseTeachers(args[1], args[3]));
                                break;
                            default:
                                System.out.println("Error:input illegal.");
                        }
                    }
                    break;
                case "nc" :
                    if (args.length != 6 || NumberCheck.isNumeric(args[4]) == 1) {
                        System.out.println("Error:input illegal.");
                    }
                    else {
                        switch (courseList.addCourse(args[1], args[2], args[3], args[4], args[5])) {
                            case 1:
                                System.out.println("Error:Course exists.");
                                break;
                            case 2:
                                System.out.println("Error:Course add illegal.");
                                break;
                            case 0:
                                break;
                        }
                    }
                    break;
                case "clist":
                    Course course = courseList.getCourseById(args[1]);
                    if (course == null) {
                        System.out.println("Error:Course does not exist.");
                    }
                    else if (args.length != 4 || NumberCheck.isNumeric(args[2]) != 0 || NumberCheck.isNumeric(args[3]) != 0) {
                        System.out.println("Error:input illegal.");
                    }
                    else {
                        int n = Integer.parseInt(args[2]);
                        int m = Integer.parseInt(args[3]);
                        OutputClist(scan, n, m, course.getStudents());
                    }
                    break;
                case "back":
                    return false;
                case "QUIT":
                    return true;
                default:
                    System.out.println("Error:input illegal.");
            }
        }
        return false;
    }

    private static void OutputUDC(int flag) {
        switch (flag) {
            case 0:
                System.out.println("Update success.");
                break;
            case 1:
                System.out.println("Error:Course does not exist.");
                break;
            case 2:
                System.out.println("Error:Update fail.");
                break;
            case 3:
                System.out.println("Error:input illegal.");
        }
    }

    private static void OutputClist(Scanner scan, int n, int m, Set<String> snos) {
        PersonList personList = PersonList.getInstance();
        List<String> Snos = new ArrayList<String>(snos);
        String input;
        boolean flag = false;
        do {
            if (flag) {
                input = scan.nextLine();
                switch (input) {
                    case "n":
                        n++;
                        break;
                    case "l":
                        n--;
                        break;
                    case "q":
                        return;
                    default:
                        System.out.println("Error:input illegal.");
                        return;
                }
            }
            else {
                flag = true;
            }
            if (Snos.size() <= (n - 1) * m || n < 1) {
                System.out.println("Error:Record does not exist.");
                break;
            }
            System.out.println("Page:" + n);
            for (int i = 1; i <= m && i + (n - 1) * m <= Snos.size(); i++) {
                System.out.println(i + "." + Snos.get(i + (n - 1) * m - 1) + "," + personList.getStudentNameBySno(Snos.get(i + (n - 1) * m - 1)));
            }
            System.out.println("n-next page, l-last page, q-quit");
        }while (scan.hasNext());
    }

    private static void OutputGcByKey(Scanner scan, int n, int m, List<Course> course_list) {
        String input;
        boolean flag = false;
        do {
            if (flag) {
                input = scan.nextLine();
                switch (input) {
                    case "n":
                        n++;
                        break;
                    case "l":
                        n--;
                        break;
                    case "q":
                        return;
                    default:
                        System.out.println("Error:input illegal.");
                        return;
                }
            }
            else {
                flag = true;
            }
            if (course_list.size() <= (n - 1) * m || n < 1) {
                System.out.println("Error:Course does not exist.");
                break;
            }
            System.out.println("Page:" + n);
            for (int i = 1; i <= m && i + (n - 1) * m <= course_list.size(); i++) {
                System.out.println(i + "." + course_list.get(i + (n - 1) * m - 1).toString());
            }
            System.out.println("n-next page, l-last page, q-quit");
        }while (scan.hasNext());
    }
}