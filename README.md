# Pattern-Recognition
Programming Test for the WellD's interview process

# Build

    git clone https://github.com/NicolaBonassi/Pattern-Recognition
    cd Pattern-Recognition/
    gradle shadowJar

# Run

    cd build/libs/
    java -jar Pattern-Recognition-1.0-SNAPSHOT-all.jar

The REST services are exposed at http://localhost:1337/pattern/ 
    
# Program Structure
This program is formed by 5 files:
- `StartServer.java` sets up the HTTP server;
- `PointService.java` contains the REST API, as specified by the requirements;
- `Point.java` contains the Point class, representing points in space;
- `PairedPoints.java` contains the PairedPoints class, representing a pair of points and the segment they form;
- `Points.Java` contains the Points class, represeting a set of Points, and also holds the main logic of the program in the `getLinesThrough` method.


# Considerations

- The ServerClass general structure is taken from a REST webservice example provided during an uni's class.  
- The exercise was coded under the assumption that we are working in a finite space. However, due to how the solutions was implemented, it's possible to input points at non-integer coordinates (eg: {"x": 1.6, "y": 3.14}). This is an unintended side-effect of an improvement in the solution, so (unfortunately) no testing was made about this functionality.
- The server will happily accept POST requests that do not specify either or both coordinates, defaulting to the value 0 for the missing value(s). Efforts were made to refuse this kind of requests but I didn't manage to do it.
