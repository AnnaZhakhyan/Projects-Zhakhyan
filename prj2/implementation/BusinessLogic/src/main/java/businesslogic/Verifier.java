package businesslogic;

import java.time.LocalDate;

public class Verifier {
    public boolean inThePast(LocalDate date){
        LocalDate currentDate = LocalDate.now();
        if(date.isBefore(currentDate)){
            return true;
        } else {
            return false;
        }
    }
    public boolean verifyIATA(String string){
        if(string.length() == 3){
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmailValid(String string){
        if(string.contains("@")){
            return true;
        } else {
            return false;
        }
    }
}
