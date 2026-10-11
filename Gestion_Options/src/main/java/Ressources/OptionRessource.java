package Ressources;

import entities.Option;
import metiers.OptionBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
public class OptionRessource {

    OptionBusiness optionBusiness = new OptionBusiness();

    // 1. Liste de TOUTES les options
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Option> getlist() {
        return optionBusiness.getListeOptions();
    }

    // 2. Une option par son CODE (ID)
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByCode(@PathParam("code") int code) {
        Option opt = optionBusiness.getOptionByCode(code);
        if (opt != null) {
            return Response.status(Response.Status.OK).entity(opt).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("opt not found").build();
    }

    // 3. Liste des options par SEMESTRE
    @GET
    @Path("/semestre") // 👈 C'EST CETTE LIGNE QUI EMPÊCHE LE CONFLIT !
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBySemestre(@QueryParam("s") int semestre) {
        List<Option> opt = optionBusiness.getOptionsBySemestre(semestre);

        if (opt != null && !opt.isEmpty()) {
            return Response.status(Response.Status.OK).entity(opt).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("aucun semestre").build();
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addoption(Option opt){
        if (optionBusiness.addOption(opt))
            return Response.status(Response.Status.CREATED).entity("added with sucess").build();
        return Response.status(Response.Status.NOT_FOUND).entity("echec d'ajout").build();

    }
    @PUT
    @Path("/{code}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateoption(@PathParam("code") int code,  Option opt){
        if (optionBusiness.updateOption(code,opt))
            return Response.status(Response.Status.OK).entity("updated with sucess").build();
        return Response.status(Response.Status.NOT_FOUND).entity("echec de mise à jour ").build();

    }
    @DELETE
    @Path("/{code}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteOption(@PathParam("code") int code){
        if (optionBusiness.deleteOption(code))
            return Response.status(Response.Status.OK).entity("delete with sucess").build();
        return Response.status(Response.Status.NOT_FOUND).entity("echec de supression").build();
    }


}