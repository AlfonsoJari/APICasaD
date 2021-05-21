/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.apicasad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author alfon
 */
@Path("casadomo")
public class CasadomoResource {

    @Context
    private UriInfo context;
    private ArrayList<Usuario> arrayUsuario;
    private Usuario usr;

    public CasadomoResource() {
    }

    @POST
    @Path("newuser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Usuario postUsuario(Usuario nuevo) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("INSERT INTO usuario (usuario, contrasena) VALUES (?, ?)");
            st.setString(1, nuevo.getUsuario());
            st.setString(2, nuevo.getContrasena());
            st.executeUpdate();
            st.close();
            return nuevo;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Usuario getUsuario(Usuario user) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("SELECT * FROM usuario WHERE usuario = ? AND contrasena = ?");
            st.setString(1, user.getUsuario());
            st.setString(2, user.getContrasena());
            ResultSet rs = st.executeQuery();
            rs.next();
            user.setUsuario(rs.getString("usuario"));
            user.setContrasena(rs.getString("contrasena"));
            rs.close();
            st.close();
            return user;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
/*
    @POST
    @Path("newcortina")
    @Consumes(MediaType.APPLICATION_JSON)
    public Cortina postCortina(Cortina nuevo) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("INSERT INTO cortinas (id, nombre, estado, descripcion, repetir) VALUES (?, ?, ?, ?, ?)");
            st.setLong(1, Long.parseLong(nuevo.getId()));
            st.setString(2, nuevo.getNombre());
            st.setString(3, nuevo.getEstado());
            st.setString(4, nuevo.getDescripcion());
            st.setString(5, nuevo.getRepetir());
            st.executeUpdate();
            st.close();
            return nuevo;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @DELETE
    @Path("deletecortina")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteCortina(@QueryParam("id") String id) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("DELETE FROM cortinas WHERE id = ?");
            st.setLong(1, Long.parseLong(id));
            st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("updatecortina")
    @Consumes(MediaType.APPLICATION_JSON)
    public Cortina putCortina(Cortina actualizado) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("UPDATE cortinas SET nombre = ?, estado = ?, descripcion = ? WHERE id = ?");
            st.setString(1, actualizado.getNombre());
            st.setString(2, actualizado.getEstado());
            st.setString(3, actualizado.getDescripcion());
            st.setString(4, actualizado.getRepetir());
            st.setLong(5, Long.parseLong(actualizado.getId()));
            st.executeUpdate();
            st.close();
            return actualizado;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("searchcortina")
    @Produces(MediaType.APPLICATION_JSON)
    public Cortina getCortina(@QueryParam("id") String id) {
        try {
            Cortina cor = new Cortina();
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("SELECT * FROM Cortina WHERE id = ?");
            st.setLong(1, Long.parseLong(id));
            ResultSet rs = st.executeQuery();
            rs.next();
            cor.setId(rs.getString("id"));
            cor.setNombre(rs.getString("nombre"));
            cor.setEstado(rs.getString("estado"));
            cor.setDescripcion(rs.getString("descripcion"));
            cor.setRepetir(rs.getString("repetir"));
            rs.close();
            st.close();
            return cor;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("cortinas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cortina> getCotinas() {
        try {
            List<Cortina> arrayCor = new ArrayList<>();
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("SELECT * FROM Cortina");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cortina cor = new Cortina();
                cor.setId(rs.getString("id"));
                cor.setNombre(rs.getString("nombre"));
                cor.setEstado(rs.getString("estado"));
                cor.setDescripcion(rs.getString("descripcion"));
                cor.setRepetir(rs.getString("repetir"));
                arrayCor.add(cor);
            }
            rs.close();
            st.close();
            return arrayCor;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
*/
}
