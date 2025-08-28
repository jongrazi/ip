public enum TaskType {
    TODO("[T]"),
    DEADLINE("[D]"),
    EVENT("[E]");

    private final String icon;

    TaskType(String icon) {
        this.icon = icon;
    }

    public String icon() {
        return icon;
    }
}