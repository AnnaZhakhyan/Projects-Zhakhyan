package businesslogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class BusinessTests {

    @Test
    public void testIsEmailValid() {
        Verifier verifier = new Verifier();
        Boolean test = verifier.isEmailValid("PrettyPlatesPlease@gmail.com");
        assertEquals(true, test);
    }

    @Test
    public void testIsEmailInvalid() {
        Verifier verifier = new Verifier();
        Boolean test = verifier.isEmailValid("PrettyPlatesPlease.com");
        assertEquals(false, test);
    }


    @Test
    public void testDateInThePast() {
        Verifier verifier = new Verifier();
        Boolean aDateInThePast = verifier.inThePast(LocalDate.of(2003,03,15));
        assertEquals(true, aDateInThePast);
    }

    @Test
    public void testDateInTheFuture() {
        Verifier verifier = new Verifier();
        Boolean aDateInThePast = verifier.inThePast(LocalDate.of(2033,03,15));
        assertEquals(false, aDateInThePast);
    }

    @Test
    public void calculateDistanceTest(){
        DistanceCalculator dCalc = new DistanceCalculator();
        dCalc.calculateDistance(52.308601, 4.76389, 39.224098, 125.669998);
        
    }
}