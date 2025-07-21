package RestaurantApplication.Resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import RestaurantApplication.DTOs.CustomerDTO;
import RestaurantApplication.DTOs.LoginDTO;
import RestaurantApplication.Entities.Customer;
import RestaurantApplication.Services.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    // @GET
    // public Response listAllCustomers() {

    //     return Response.ok(
    //         Customer.listAll().stream().map(
    //             (c) -> CustomerDTO.fromEntity((Customer) c)
    //             )
    //             .collect(Collectors.toList())
    //         ).build();
    // }

    // @POST
    // @Path("/login")
    // public Response login(@Valid LoginDTO dto) {
    //     List<Customer> matchingCustomers = Customer.list("email = ?1 AND password = ?2", dto.email, dto.password);
    //     matchingCustomers.forEach((cus)->System.out.println(cus.firstName));
    //     Response response;
    //     if(matchingCustomers.size()>0){
    //         response = Response.ok(
    //                     "Successfully logged in."
    //                 ).build();
    //     }else{
    //         response = Response.status(Response.Status.UNAUTHORIZED).entity("Invalid email or password").build();
    //     }
    //     return response;
    // }
    
    @POST
    @Path("/register")
    @Transactional
    public Response register(@Valid CustomerDTO dto) {
        return CustomerService.register(dto);
    }
}
