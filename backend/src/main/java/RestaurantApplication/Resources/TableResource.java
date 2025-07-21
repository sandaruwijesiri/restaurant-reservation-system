package RestaurantApplication.Resources;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import RestaurantApplication.DTOs.AddTableDTO;
import RestaurantApplication.DTOs.CustomerDTO;
import RestaurantApplication.DTOs.TableAvailabilityDTO;
import RestaurantApplication.Entities.Customer;
import RestaurantApplication.Entities.TableInfo;
import RestaurantApplication.Services.TableService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/tables")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TableResource {
    
    @Inject
    TableService tableService;

    @POST
    public Response getAvailableTables(@Valid TableAvailabilityDTO dto) {

        return Response.ok(tableService.getAvailableTables(dto)).build();
    }

    @POST
    @Path("/add")
    public Response addTable(@Valid AddTableDTO dto) {

        return tableService.addTable(dto);
    }
}
