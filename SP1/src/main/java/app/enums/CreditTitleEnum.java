package app.enums;

public enum CreditTitleEnum {

    // General Crew
    ACTOR("Actor Description"),
    DIRECTOR("Director Description"),
    SCREENWRITER("Screenwriter - Author of the script and dialogue"),
    PRODUCER("Producer - Oversees the entire production process"),
    EXECUTIVE_PRODUCER("Executive Producer - High-level oversight and financing"),

    // Visual & Technical Crew
    CINEMATOGRAPHER("Cinematographer - Director of Photography (DP)"),
    EDITOR("Editor - Responsible for post-production assembly"),
    PRODUCTION_DESIGNER("Production Designer - Visual environment and set design"),
    COSTUME_DESIGNER("Costume Designer - Responsible for clothing and wardrobe"),
    MAKEUP_ARTIST("Makeup Artist - Character look and prosthetics"),

    // Sound & Music Crew
    COMPOSER("Composer - Creator of the original musical score"),
    SOUND_DESIGNER("Sound Designer - Responsible for the overall sonic experience"),
    VOICE_ACTOR("Voice Actor - Provides voice-overs and dubbing"),

    // Specialist Crew
    CHOREOGRAPHER("Choreographer - Design of dance, movement or fight sequences"),
    STUNT_COORDINATOR("Stunt Coordinator - Safety and design of physical stunts"),
    CASTING_DIRECTOR("Casting Director - Finds and audits talent for roles"),
    SCRIPT_SUPERVISOR("Script Supervisor - Maintains continuity across takes"),
    SHOWRUNNER("Showrunner - Creative and managerial lead on TV series"),

    // Lighting Crew
    GAFFER("Gaffer - Head of the electrical department and lighting"),
    KEY_GRIP("Key Grip - Head of camera support and rigging"),

    // Extra Crew
    NARRATOR("Narrator - The voice telling the story"),
    EXTRA("Extra - Background talent without dialogue"),
    STUNT_DOUBLE("Stunt Double - Performs specific stunts for an actor");

    // ______________________________________________

    // Attributes
    private String description;

    // ______________________________________________

    CreditTitleEnum(String description) {
        this.description = description;
    }

    // ______________________________________________

    public String getDescription(){
        return this.description;
    }

}
