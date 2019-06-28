package entities;

import java.util.Objects;

public class Section {
    private int id;
    private int sectionNumber;
    private int semester;
    private int seatLimit;
    private String courseCode;
    private String initials;

    public Section(int id, int sectionNumber, int semester, int seatLimit, String courseCode, String initials) {
        this.id = id;
        this.sectionNumber = sectionNumber;
        this.semester = semester;
        this.seatLimit = seatLimit;
        this.courseCode = courseCode;
        this.initials = initials;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getSeatLimit() {
        return seatLimit;
    }

    public void setSeatLimit(int seatLimit) {
        this.seatLimit = seatLimit;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String toCSV(){
        return (id + "," + sectionNumber + "," + semester + "," + seatLimit + "," + courseCode + "," + initials);
    }

    public static Section fromCSV(String csv){
        String tokens[] = csv.split(",");
        if (tokens.length != 6)
            return null;
        return new Section(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), tokens[4], tokens[5]);
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", sectionNumber=" + sectionNumber +
                ", semester=" + semester +
                ", seatLimit=" + seatLimit +
                ", courseCode='" + courseCode + '\'' +
                ", initials='" + initials + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return getId() == section.getId() &&
                getSectionNumber() == section.getSectionNumber() &&
                getSemester() == section.getSemester() &&
                getSeatLimit() == section.getSeatLimit() &&
                Objects.equals(getCourseCode(), section.getCourseCode()) &&
                Objects.equals(getInitials(), section.getInitials());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSectionNumber(), getSemester(), getSeatLimit(), getCourseCode(), getInitials());
    }
}
