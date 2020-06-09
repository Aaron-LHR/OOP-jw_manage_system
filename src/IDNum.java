public class IDNum {
    private String ID;
    public IDNum(String input) {
        if (input.charAt(input.length() - 1) == 'x') {
            ID = input.replace('x', 'X');
        }
        else {
            ID = input;
        }
    }

    @Override
    public String toString() {
        return ID;
    }

    public static boolean checkIDNum(String IDNum){
        if (IDNum.length()!=18){
            return false;
        }
        int i;
        for(i = 0; i < 18; i++){
            if (!(Character.isDigit(IDNum.charAt(i)) || (i==17 && (IDNum.charAt(i)=='x' || IDNum.charAt(i)=='X')))){
                return false;
            }
        }
        if (IDNum.charAt(17) == 'x' || IDNum.charAt(17) == 'X'){
            IDNum = IDNum.replace(IDNum.charAt(17), ':');
        }
        Birthday birthday = new Birthday(IDNum);
        if (!birthday.checkBirthday()){
            return false;
        }
        int CheckKey = 0, w = 1;
        for(i = 17; i >= 0; i--){
            CheckKey = (CheckKey + (IDNum.charAt(i) - '0') * w) % 11;
            w = (w * 2) % 11;
        }
        if (CheckKey != 1){
            return false;
        }
        return true;
    }
}
