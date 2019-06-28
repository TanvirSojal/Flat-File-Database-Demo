package entities;

import java.util.Objects;

public class Registration {
    private int id;
    private String studentId;
    private int sectionId;

    public Registration(int id, String studentId, int sectionId) {
        this.id = id;
        this.studentId = studentId;
        this.sectionId = sectionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String toCSV(){
        return (id + "," + studentId + "," + sectionId);
    }

    public static Registration fromCSV(String csv){
        String tokens[] = csv.split(",");
        if (tokens.length != 3)
            return null;
        return new Registration(Integer.parseInt(tokens[0]), tokens[1], Integer.parseInt(tokens[2]));
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", sectionId=" + sectionId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registration)) return false;
        Registration that = (Registration) o;
        return getId() == that.getId() &&
                getSectionId() == that.getSectionId() &&
                Objects.equals(getStudentId(), that.getStudentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStudentId(), getSectionId());
    }
}
