import java.util.Objects;

public class PairedPoints {
    //these should be final but there is no point since Points are mutable (they shouldn't be either!)
    private Point firstPoint;
    private Point secondPoint;

    public PairedPoints(Point a, Point b) {
        this.firstPoint = a;
        this.secondPoint = b;
    }

    //no need to check if the two points occupy the same position since we're working with a set of points
    public double getAngle() {
        if(secondPoint.getX() == firstPoint.getX()) {
            return Double.POSITIVE_INFINITY;
        }
        else if(secondPoint.getY() == firstPoint.getY()) {
            return 0;
        }
        else {
            return ((secondPoint.getY() - firstPoint.getY())) / (secondPoint.getX() - firstPoint.getX());
        }
    }

    /* checks if the segment between the paired points is perpendicular to the Y axis and if so returns the
     * intercept with the X axis. Otherwise returns the intercept of the segment with the Y axis. */
    public Point getIntercept() {
        double angle = getAngle();
        if(angle == Double.POSITIVE_INFINITY) {
            return new Point(firstPoint.getX(), 0);
        }
        return new Point(0, firstPoint.getY() - (angle * firstPoint.getX()));
    }

    public Point getFirstPoint() {
        return firstPoint;
    }
    public Point getSecondPoint() {
        return secondPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairedPoints that = (PairedPoints) o;
        return firstPoint.equals(that.firstPoint) &&
                secondPoint.equals(that.secondPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPoint) + Objects.hash(secondPoint);
    }

    //used for debugging
    @Override
    public String toString() {
        return "(" + firstPoint + ", " + secondPoint + ": " + getAngle() + ", " + getIntercept() + ")";
    }
}