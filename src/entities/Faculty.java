package entities;

import java.util.Objects;

public class Faculty {
    private String initials;
    private String name;
    private String rank;

    public Faculty(String initials, String name, String rank) {
        this.initials = initials;
        this.name = name;
        this.rank = rank;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "initials='" + initials + '\'' +
                ", name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(getInitials(), faculty.getInitials()) &&
                Objects.equals(getName(), faculty.getName()) &&
                Objects.equals(getRank(), faculty.getRank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInitials(), getName(), getRank());
    }
}
