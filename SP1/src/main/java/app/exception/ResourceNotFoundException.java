package app.exception;

// Created by: Guacamoleboy
// ________________________
// Last updated: 21/02-2026
// By: Guacamoleboy

public class ResourceNotFoundException extends RuntimeException {

    //  Custom exception for missing Resource calls
    //      - Unchecked exception (runtime) no try-catch needed
    //      - RuntimeException -> Exception -> Throwable

    // Attributes
    private final String entity;
    private final String entityValue;
    private final String location;

    // ___________________________________________________
    // (+)Message

    public ResourceNotFoundException(String entity, String entityValue) {
        super(entity + " not found: " + entityValue);
        this.entity = entity;
        this.entityValue = entityValue;
        this.location = null;
    }

    // ____________________________________________
    // (+)Message (+)location

    public ResourceNotFoundException(String entity, String entityValue, String location) {
        super(entity + " not found: " + entityValue + " | " + location);
        this.entity = entity;
        this.entityValue = entityValue;
        this.location = location;
    }

    // ____________________________________________
    // (+)Cause (-)location

    public ResourceNotFoundException(String entity, String entityValue, Throwable cause) {
        super(entity + " not found: " + entityValue, cause);
        this.entity = entity;
        this.entityValue = entityValue;
        this.location = null;
    }

    // ____________________________________________
    // (+)Cause (+)location
    public ResourceNotFoundException(String entity, String entityValue, String location, Throwable cause) {
        super(entity + " not found: " + entityValue + " | " + location, cause);
        this.entity = entity;
        this.entityValue = entityValue;
        this.location = location;
    }

    // ___________________________________________________

    public String getEntity() {
        return this.entity;
    }

    // ___________________________________________________

    public String getEntityValue() {
        return this.entityValue;
    }

    // ___________________________________________________

    private String getLocation(){
        return this.location;
    }

}