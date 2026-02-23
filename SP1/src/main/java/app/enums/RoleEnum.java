package app.enums;

// Can use @Data, @AllArgsContructor from Lombok, but doing it explicit to understand the backend
// structure of an enum.
// - Jonas

public enum RoleEnum {

    // Enums
    ADMIN("Full system access. Can modify, delete, and manage all data and users."),
    SUPPORT("Assistance level. Can help companies with their profiles and view data, but cannot delete or modify core movie information."),
    COMPANY("Content provider. Can upload, edit, and delete movies belonging to their own profile."),
    GUEST("Limited access. Can only view public information and trailers. No ability to rate or comment."),
    USER("Standard consumer. Can view movies, create watchlists, and provide ratings or reviews."),
    PREMIUM_USER("Subscribed consumer. Same as User, but with access to exclusive content and ad-free viewing.");

    // _____________________________________________________

    // Attributes
    private final String description;

    // _____________________________________________________

    RoleEnum(String description){
        this.description = description;
    }

    // _____________________________________________________

    public String getDescription(){
        return this.description;
    }

}