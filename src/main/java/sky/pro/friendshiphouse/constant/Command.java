package sky.pro.friendshiphouse.constant;

public enum Command {
    ADDRESS("/address","Часы работы, адресс, схема проезда", "address");

    private final String title;
    private final String description;
    private final String callbackData;

    Command(String title, String description, String callbackData) {
        this.title = title;
        this.description = description;
        this.callbackData = callbackData;
    }

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public String getCallbackData() {return callbackData;}
}
