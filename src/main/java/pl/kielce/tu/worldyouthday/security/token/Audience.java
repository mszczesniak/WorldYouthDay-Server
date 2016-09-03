package pl.kielce.tu.worldyouthday.security.token;

public enum Audience {
    UNKNOWN("unknown"),
    WEB("web"),
    MOBILE("mobile"),
    TABLET("tablet");

    private String value;

    Audience(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
