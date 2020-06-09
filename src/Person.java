import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Person {
    protected IDNum ID;
    protected String name;
    protected char sex;
    protected Birthday birthday;
    protected String password = "a12345";
    protected Set<String> course_list = new TreeSet<>();

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean checkPassword(String password) {
        int length = password.length();
        int upper_letter = 0, lower_letter = 0, number = 0, other = 0;
        if (length < 6 || length > 18) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char c = password.charAt(i);
            if (c < 33 || c > 126) {
                return false;
            }
            if (Character.isUpperCase(c)) {
                upper_letter = 1;
            }
            else if (Character.isLowerCase(c)) {
                lower_letter = 1;
            }
            else if (Character.isDigit(c)) {
                number = 1;
            }
            else {
                other = 1;
            }
        }
        if (upper_letter + lower_letter + number + other < 2) {
            return false;
        }
        return true;
    }

    public String getPassword() {
        return password;
    }

    public String getID() {
        return ID.toString();
    }

    public String getName() {
        return name;
    }

    public char getSex() {
        return sex;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public boolean setID(String id) {
        if (IDNum.checkIDNum(id)) {
            this.ID = new IDNum(id);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean setName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i))) {
                return false;
            }
        }
        this.name = name;
        return true;
    }

    public boolean setSex(char sex) {
        this.sex = sex;
        return true;
    }

    public boolean setBirthday(Birthday birthday) {
        this.birthday = birthday;
        return true;
    }

    public Person() {
    }

    public void init() {
        this.birthday = new Birthday(ID.toString());
        if (ID.toString().charAt(16) % 2 == 0) {
            this.sex = 'F';
        }
        else {
            this.sex = 'M';
        }
    }
    private Person(String ID, String name) {
        this.ID = new IDNum(ID);
        this.name = name;
        this.birthday = new Birthday(ID);
        if (ID.charAt(16) % 2 == 0) {
            this.sex = 'F';
        }
        else {
            this.sex = 'M';
        }
    }
    public static Person newPerson(String name, String ID){
        if (!IDNum.checkIDNum(ID)){
            return null;
        }
        return new Person(ID, name);
    }

    public Set<String> getCourse_list() {
        return course_list;
    }

    @Override
    public String toString() {
        return "Name:" + name + '\n' +
                "IDNum:" + ID.toString() + '\n' +
                "Sex:" + sex + '\n' +
                "Birthday:" + birthday.toString();
    }
}
