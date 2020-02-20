import java.util.*;

public class Points {

    //Using a set to avoid duplicate points
    private Set<Point> pointSet;
    private static Points instance;

    public Points() {
        pointSet = new HashSet<Point>();
    }

    public synchronized static Points getInstance(){
        if(instance==null) {
            instance = new Points();
        }
        return instance;
    }

    public Set<Point> getPointSet() {
        synchronized(pointSet) {
            return new HashSet<>(pointSet);
        }
    }

    public void  add(Point p){
        synchronized(pointSet) {
            pointSet.add(p);
        }
    }

    /* Returns a collection of line segments representing all the lines passing through n points
     * The idea behind this solution is simple: different pair of points are collinear if they form the same
     * angle with cardinal axis and if they intercept the axis from the same point. We build a list of all these
     * pairs and then group them through the angle and intercept point.*/
    public  Set<List<Point>> getLinesThrough(int n) {
        Set<List<Point>> lines = new HashSet<List<Point>>();
        Set<Point> pointSetCopy;

        /* trying to lock pointSet for as little time as possible, just the necessary to check if
         * we're being asked a trivial question, and if not we clone the pointSet and work with the
         * cloned one, releasing the original from the lock. */
        synchronized(pointSet) {
            //there are no lines passing through more points than the ones available
            if(n > pointSet.size()) {
                return lines;
            }
            /* infinite lines pass through every single point, but we're being asked to return all
             * line segments, so it's reasonable to just return all the points */
            if(n == 1) {
                for(Point p : pointSet) {
                    List<Point> singlePointLine = new ArrayList<Point>();
                    singlePointLine.add(p);
                    lines.add(singlePointLine);
                }
                return lines;
            }

            pointSetCopy = duplicatePoints();
        }

        /* The meat and potato of the method, it works in three phases:
         * 1) Pair all the points with each other
         * 2) Iterate through the pair list and put every pair into a bucket indexed by the angle formed by
         *    the segment with the cardinal axis and the intercept between the line formed by the segment and the
         *    Y axis (or with the X axis if the line would be perpendicular to it)
         * 3) Iterate through the buckets: if a bucket contains n or more points it will form a line segment solution */
        Map<AngleAndInterceptKey, Set<Point>> possibleLines = new HashMap<AngleAndInterceptKey, Set<Point>>();
        List<PairedPoints> pointPairsList = pairPoints(pointSetCopy);

        for(PairedPoints pp: pointPairsList) {
            AngleAndInterceptKey key = new AngleAndInterceptKey(pp.getAngle(), pp.getIntercept());
            if(!possibleLines.containsKey(key)) {
                possibleLines.put(key, new HashSet<Point>());
                possibleLines.get(key).add(pp.getFirstPoint());
            }
            possibleLines.get(key).add(pp.getSecondPoint());
        }

        for(AngleAndInterceptKey key : possibleLines.keySet()) {
            if (possibleLines.get(key).size() >= n) {
                List<Point> line = new ArrayList<Point>();
                for(Point linePoint : possibleLines.get(key)) {
                    line.add(linePoint);
                }
                lines.add(line);
            }
        }

        return lines;
    }

    //pair every point in the set with each other. Using an array to speed up the process
    public List<PairedPoints> pairPoints(Set<Point> pointSet) {
        List<PairedPoints> pairs = new ArrayList<PairedPoints>();
        Point[] pointArray = pointSet.toArray(new Point[pointSet.size()]);
        for(int i = 0; i < pointArray.length; i++) {
            for(int j = i+1; j < pointArray.length; j++) {
                pairs.add(new PairedPoints(pointArray[i], pointArray[j]));
            }
        }
        return pairs;
    }

    public void delete() {
        synchronized(pointSet) {
            pointSet = new HashSet<Point>();
        }
    }

    public Set<Point> duplicatePoints() {
        Set<Point> clonedSet = new HashSet<Point>();
        for(Point p : this.pointSet) {
            clonedSet.add(new Point(p));
        }
        return clonedSet;
    }

    //Just a wrapper for the two relevant values used to key all the segments in the hashmap
    private class AngleAndInterceptKey {
        private final double angle;
        private final Point interceptPoint;

        private AngleAndInterceptKey(double angle, Point interceptPoint) {
            this.angle = angle;
            this.interceptPoint = interceptPoint;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AngleAndInterceptKey that = (AngleAndInterceptKey) o;
            return Double.compare(that.angle, angle) == 0 &&
                    interceptPoint.equals(that.interceptPoint);
        }

        @Override
        public int hashCode() {
            return Objects.hash(angle, interceptPoint);
        }

        //used for debugging
        @Override
        public String toString() {
            return "" + angle + ", " + interceptPoint;
        }
    }
}