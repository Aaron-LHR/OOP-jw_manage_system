import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CourseList {
    private List<Course> course_list = new ArrayList<>();
    private CourseList(){}
    private static CourseList instance = new CourseList();
    public static CourseList getInstance() {
        return instance;
    }

    public List<Course> getCourse_list() {
        return course_list;
    }

    protected Course getCourseById(String cid) {
        for (Course course : course_list) {
            if (course.getCID().toUpperCase().equals(cid.toUpperCase())) {
                return course;
            }
        }
        return null;
    }

    protected List<Course> getCoursesByKeyword(String keyword) {

        List<Course> result = new ArrayList<>();
        for (Course course : course_list) {
            if (course.getName().toUpperCase().contains(keyword.toUpperCase())) {
                result.add(course);
            }
        }
        return result;
    }

    protected boolean addCourse(Course course) {
        if (this.getCourseById(course.getCID())!=null) {
            return false;
        }
        else {
            course_list.add(course);
            return true;
        }
    }

    protected int addCourse(String cid, String name, String teachers, String capacity, String time) {
        if (this.getCourseById(cid) != null) {
            return 1;
        }
        else if (!Course.CheckName(name) || !Course.CheckTeachers(teachers) || NumberCheck.isNumeric(capacity) != 0 || !Course.CheckCID(cid) || !Course.CheckTime(time)) {
            return 2;
        }
        else {
            PersonList personList = PersonList.getInstance();
            String[] Teachers = teachers.substring(1, teachers.length() - 1).split(",");
            Course course = new Course(cid, name, Teachers, Integer.parseInt(capacity), time);
            for (String Tno : Teachers) {
                Teacher teacher = personList.getTeacherByTno(Tno);
                if (teacher != null) {
                    teacher.addCourse(cid);
                }
            }
            course_list.add(course);
            Collections.sort(course_list, new CourseComparator());
            return 0;
        }
    }

    protected  boolean modifyCourseCID(String old_cid, String new_cid) {
        Course course = this.getCourseById(old_cid);
        if (course == null || this.getCourseById(new_cid) != null) {
            return false;
        }
        else {
            course.setCID(new_cid);
            return true;
        }
    }

    protected  int modifyCourseName(String cid, String name) {
        Course course = this.getCourseById(cid);
        if (course == null) {
            return 1;
        }
        else if (!Course.CheckName(name)) {
            return 2;
        }
        else {
            course.setName(name);
            return 0;
        }
    }

    protected  int modifyCourseTeachers(String cid, String teachers) {
        Course course = this.getCourseById(cid);
        if (course == null) {
            return 1;
        }
        else if (!Course.CheckTeachers(teachers)) {
            return 2;
        }
        else {
            PersonList personList = PersonList.getInstance();
            String[] Teachers = teachers.substring(1, teachers.length() - 1).split(",");
            for (String Tno : course.getTeachers()) {
                Teacher teacher = personList.getTeacherByTno(Tno);
                if (teacher!=null) {
                    teacher.deleteCourse(cid);
                }
            }
            course.setTeachers(Teachers);
            return 0;
        }
    }

    protected int modifyCourseCapacity(String cid, String capacity) {
        Course course = this.getCourseById(cid);
        int flag = NumberCheck.isNumeric(capacity);
        if (course == null) {
            return 1;
        }
        else if (flag == 1) {
            return 3;
        }
        else if (flag == 2) {
            return 2;
        }
        else {
            course.setCapacity(Integer.parseInt(capacity));
            return 0;
        }
    }

    protected int modifyCourseCapacity(String cid, int capacity) {
        Course course = this.getCourseById(cid);
        if (course == null) {
            return 1;
        }
        else if (capacity < 0) {
            return 2;
        }
        else {
            course.setCapacity(capacity);
            return 0;
        }
    }


}

class CourseComparator implements Comparator<Course> {
    @Override
    public int compare(Course t1, Course t2) {
        return t1.getCID().compareTo(t2.getCID());
    }
}