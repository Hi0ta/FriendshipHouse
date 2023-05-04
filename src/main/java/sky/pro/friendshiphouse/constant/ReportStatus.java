package sky.pro.friendshiphouse.constant;

public enum ReportStatus {
    AWAITING_VERIFICATION("ожидает проверки"),
    VERIFIED_ACCEPTED("проверен, принят"),
    VERIFIED_REQUESTED_IMPROVEMENTS("проверен, запрошены доработки");
    private final String description;

    ReportStatus(String description) {this.description = description;}

    public String getDescription() {return description;}

}
