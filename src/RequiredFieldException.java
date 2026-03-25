public class RequiredFieldException extends IllegalArgumentException {

    public RequiredFieldException(String fieldName, Object value) {
        super("The field " + fieldName + " is required, yet it's value is [" + value + "].");
    }

    public RequiredFieldException(String fieldName, Object value, String message) {
        super("The field " + fieldName + " is required, yet it's value is [" + value + "].\n" + message);
    }

}
