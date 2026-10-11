package Ressources;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiants")
public class EtudiantRessource {

    EtudiantBusiness etudiantBusiness = new EtudiantBusiness();

    // 1. Liste de TOUS les étudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etudiant> getlist() {
        return etudiantBusiness.getAllEtudiants(); // 👈 Correction ici
    }
    // 2. Un étudiant par son IDENTIFIANT (ex: "I001")
    @GET
    @Path("/{identifiant}") // 👈 Changé en {identifiant}
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByIdentifiant(@PathParam("identifiant") String identifiant) { // 👈 String au lieu de int

        Etudiant etud = etudiantBusiness.getEtudiantByIdentifiant(identifiant); // 👈 On passe la String

        if (etud != null) {
            return Response.status(Response.Status.OK).entity(etud).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("etudiant not found").build();
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etud) {
        if (etudiantBusiness.addEtudiant(etud)) {
            return Response.status(Response.Status.CREATED).entity("added with sucess").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("echec d'ajout").build();
    }
    // 3. Liste des étudiants par Option
    @GET
    @Path("/option/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByOption(@PathParam("code") int code) {

        // 1. On crée un objet Option temporaire avec le code reçu
        Option opt = new Option();
        opt.setCodeOption(code);

        // 2. On récupère la liste des étudiants
        List<Etudiant> etudiants = etudiantBusiness.getEtudiantsByOption(opt);

        // 3. On vérifie si la liste n'est pas vide (ta structure préférée)
        if (etudiants != null && !etudiants.isEmpty()) {
            return Response.status(Response.Status.OK).entity(etudiants).build();
        }

        // 4. Sinon, on renvoie un message clair
        return Response.status(Response.Status.NOT_FOUND)
                .entity("aucun etudiant pour cette option").build();
    }
    @PUT
    @Path("/{identifiant}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant etud) { // 👈 String au lieu de int
        if (etudiantBusiness.updateEtudiant(identifiant, etud))
            return Response.status(Response.Status.OK).entity("updated with sucess").build();
        return Response.status(Response.Status.NOT_FOUND).entity("echec de mise à jour").build();
    }
    @DELETE
    @Path("/{identifiant}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {  // ✅ String avec majuscule
        if (etudiantBusiness.deleteEtudiant(identifiant))  // ✅ etudiantBusiness sans 'i'
            return Response.status(Response.Status.OK).entity("delete with sucess").build();
        return Response.status(Response.Status.NOT_FOUND).entity("echec de supression").build();
    }
}
