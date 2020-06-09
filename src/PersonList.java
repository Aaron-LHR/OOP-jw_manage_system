import java.util.*;

public class PersonList {
    private List<Student> students_list = new ArrayList<>();
    private List<Teacher> teachers_list = new ArrayList<>();
    private Set<String> Tno_list = new TreeSet<>();
    private Set<String> Sno_list = new TreeSet<>();
    private Set<String> ID_list = new TreeSet<>();

    private PersonList(){}
    private static PersonList instance = new PersonList();
    public static PersonList getInstance() {
        return instance;
    }

    public Set<String> getTno_list() {
        return Tno_list;
    }

    public Set<String> getSno_list() {
        return Sno_list;
    }

    public Set<String> getID_list() {
        return ID_list;
    }

    public void addTeacher(Teacher teacher) {
        Tno_list.add(teacher.getTno());
        ID_list.add(teacher.getID());
        teachers_list.add(teacher);
        Collections.sort(teachers_list, new TeacherComparator());
    }

    public void addStudent(Student student) {
        Sno_list.add(student.getSno());
        ID_list.add(student.getID());
        students_list.add(student);
        Collections.sort(students_list, new StudentComparator());
    }

    public void deleteStudent(Student student) {
        ID_list.remove(student.getID());
        Sno_list.remove(student.getSno());
        students_list.remove(student);
    }

    public Teacher login_teacher(String ID, String password) {
        for (Teacher teacher : teachers_list) {
            if (teacher.getID().equals(ID.toUpperCase())) {
                if (teacher.getPassword().equals(password)) {
                    return teacher;
                }
                else {
                    return null;
                }
            }
        }
        return null;
    }

    public Student login_student(String ID, String password) {
        for (Student student : students_list) {
            if (student.getID().equals(ID.toUpperCase())) {
                if (student.getPassword().equals(password)) {
                    return student;
                }
                else {
                    return null;
                }
            }
        }
        return null;
    }

    public String getStudentNameBySno(String Sno) {
        for (Student student : students_list) {
            if (student.getSno().equals(Sno)) {
                return student.name;
            }
        }
        return null;
    }

    public Teacher getTeacherByTno(String Tno) {
        for (Teacher teacher : teachers_list) {
            if (teacher.getTno().equals(Tno)) {
                return teacher;
            }
        }
        return null;
    }

    static class TeacherComparator implements Comparator<Teacher> {
        @Override
        public int compare(Teacher t1, Teacher t2) {
            return t1.getTno().compareTo(t2.getTno());
        }
    }

    static class StudentComparator implements Comparator<Student> {
        @Override
        public int compare(Student t1, Student t2) {
            return t1.getSno().compareTo(t2.getSno());
        }
    }
}
