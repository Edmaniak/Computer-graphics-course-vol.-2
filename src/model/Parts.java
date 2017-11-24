package model;

public class Parts {
    public enum Type {
        LINE,TRIANGLE
    }
    private Type type;
    private int count;
    private int start;

    public Type getType() {
        return type;
    }

    public Parts(Type type, int count, int start) {
        this.type = type;
        this.count = count;
        this.start = start;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
