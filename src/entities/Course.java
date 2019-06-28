package entities;

import java.util.Objects;

public class Course {
    private String code;
    private String title;
    private double credit;

    public Course(String code, String title, double credit) {
        this.code = code;
        this.title = title;
        this.credit = credit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String toCSV(){
        return (code + "," + title + "," + credit);
    }

    public static Course fromCSV(String csv){
        String tokens[] = csv.split(",");
        if (tokens.length != 3)
            return null;
        return new Course(tokens[0], tokens[1], Double.parseDouble(tokens[2]));
    }

    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", credit=" + credit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Double.compare(course.getCredit(), getCredit()) == 0 &&
                Objects.equals(getCode(), course.getCode()) &&
                Objects.equals(getTitle(), course.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getTitle(), getCredit());
    }
}
