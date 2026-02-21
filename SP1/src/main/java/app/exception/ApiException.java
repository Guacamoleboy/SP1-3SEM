package app.exception;

// Created by: Guacamoleboy
// ________________________
// Last updated: 21/02-2026
// By: Guacamoleboy

public class ApiException extends RuntimeException {

    //  Custom exception for API calls
    //      - Unchecked exception (runtime) no try-catch needed
    //      - RuntimeException -> Exception -> Throwable

    // Attributes
    private final int code;
    private final String location;

    // ___________________________________________________
    // (+)Message (-)Location

    public ApiException(String message) {
        super(message);
        this.code = 500;
        this.location = null;
    }

    // ___________________________________________________
    // (+)Message (+)Location

    public ApiException(String message, String location) {
        super(message);
        this.code = 500;
        this.location = location;
    }

    // ___________________________________________________
    // (+)Message (+)Cause (-)Location
    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
        this.location = null;
    }

    // ___________________________________________________
    // (+)Message (+)Cause (+)Location

    public ApiException(String message, Throwable cause, String location) {
        super(message, cause);
        this.code = 500;
        this.location = location;
    }

    // ___________________________________________________

    // (+)Code (+)Message (-)Location
    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.location = null;
    }

    // ___________________________________________________
    // (+)Code (+)Message (+)Location

    public ApiException(int code, String message, String location) {
        super(message);
        this.code = code;
        this.location = location;
    }

    // ___________________________________________________
    // (+)Code (+)Message (+)Cause (-)Location

    public ApiException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.location = null;
    }

    // ___________________________________________________
    // (+)Code (+)Message (+)Cause (+)Location

    public ApiException(int code, String message, Throwable cause, String location) {
        super(message, cause);
        this.code = code;
        this.location = location;
    }

    // ___________________________________________________

    public int getCode(){
        return this.code;
    }

    // ___________________________________________________

    public String getLocation(){
        return this.location;
    }

}