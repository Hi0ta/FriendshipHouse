package sky.pro.friendshiphouse.constant;

public enum AnimalDogKind {
    PUPPY("щенок"),
    ADULT_DOG("взрослая собака"),
    DOG_WITH_RESTRICTION_OF_MOVEMENT("собака с ограниченным передвижением"),
    DOG_WITH_VISION_RESTRICTION("собака с ограниченным зрением");

    private final String description;

    AnimalDogKind(String description) {this.description = description;}

    public String getDescription() {return description;}

}
