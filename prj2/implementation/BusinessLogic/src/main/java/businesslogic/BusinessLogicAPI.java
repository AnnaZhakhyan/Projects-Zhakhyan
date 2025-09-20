package businesslogic;

public class BusinessLogicAPI {
    DiscountValidator discountValidator = new DiscountValidator();
    DistanceCalculator distanceCalculator = new DistanceCalculator();
    EmployeeService employeeService = new EmployeeService();
    Hasher hasher = new Hasher();
    Verifier verifier = new Verifier();

    public BusinessLogicAPI(){};


    public DiscountValidator getDiscountValidator() {
        return discountValidator;
    }
    public DistanceCalculator getDistanceCalculator() {
        return distanceCalculator;
    }
    public EmployeeService getEmployeeService() {
        return employeeService;
    }
    public Hasher getHasher() {
        return hasher;
    }
    public Verifier getVerifier() {
        return verifier;
    }

}
