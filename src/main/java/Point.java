import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Point {

    //these should be final but I also need an empty constructor and I'm not familiar enough with this stuff...
    @XmlElement
    private double x;
    @XmlElement
    private double y;

    public Point() {

    }

    //used for testing
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point (Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    //used for debugging
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
