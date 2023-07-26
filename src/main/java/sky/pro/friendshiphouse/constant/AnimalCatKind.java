package sky.pro.friendshiphouse.constant;

public enum AnimalCatKind {
    KITTY("котенок"),
    ADULT_CAT("взрослая кошка"),
    CAT_WITH_RESTRICTION_OF_MOVEMENT("кошка с ограниченным передвижением"),
    CAT_WITH_VISION_RESTRICTION("кошка с ограниченным зрением");

    private final String description;

    AnimalCatKind(String description) {this.description = description;}

    public String getDescription() {return description;}
}
