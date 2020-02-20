import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("pattern")
public class PointService {

    @GET
    @Produces({"application/json"})
    @Path("/space")
    public Response getPointList(){
        return Response.ok(Points.getInstance().getPointSet()).build();
    }

    @POST
    @Consumes({"application/json"})
    @Path("/point")
    public Response addPoint(Point p) {
        Points.getInstance().add(p);
        return Response.ok().build();
    }

    @GET
    @Produces({"application/json"})
    @Path("/lines/{n}")
    public Response getLines(@PathParam("n") int number) {
        //No need to even call the method here, the answer is always the empty list
        if(number == 0) {
            return Response.ok("{}").build();
        }
        else {
            return Response.ok(Points.getInstance().getLinesThrough(number)).build();
        }
    }

    @DELETE
    @Path("space")
    public Response deleteSpace() {
        Points.getInstance().delete();
        return Response.ok().build();
    }

}