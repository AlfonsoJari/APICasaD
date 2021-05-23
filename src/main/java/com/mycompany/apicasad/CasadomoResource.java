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

    @POST
    @Path("newdispositivo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Dispositivo postDispositivo(Dispositivo nuevo) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("INSERT INTO dispositivo (id_dispositivo, nombre, estado, descripcion, tipo, usuario) VALUES (?, ?, ?, ?, ?, ?)");
            st.setLong(1, Long.parseLong(nuevo.getId()));
            st.setString(2, nuevo.getNombre());
            st.setString(3, nuevo.getEstado());
            st.setString(4, nuevo.getDescripcion());
            st.setString(5, nuevo.getTipo());
            st.setString(6, nuevo.getUsuario());
            st.executeUpdate();
            st.close();
            return nuevo;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @DELETE
    @Path("deletedispositivo")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteDispositivo(@QueryParam("id") String id) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("DELETE FROM dispositivo WHERE id_dispositivo = ?");
            st.setLong(1, Long.parseLong(id));
            st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("cambioestado")
    @Consumes(MediaType.APPLICATION_JSON)
    public Dispositivo putEstado(Dispositivo actualizado) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("UPDATE dispositivo SET estado = ? WHERE id_dispositivo = ?");
            st.setString(1, actualizado.getEstado());
            st.setLong(2, Long.parseLong(actualizado.getId()));
            st.executeUpdate();
            st.close();
            return actualizado;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("consultarestado")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEstado(@QueryParam("id") String id) {
        try {
            //Dispositivo cor = new Dispositivo();
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("SELECT * FROM dispositivo WHERE id_dispositivo = ?");
            st.setLong(1, Long.parseLong(id));
            ResultSet rs = st.executeQuery();
            rs.next();
            //cor.setId(rs.getString("id"));
            //cor.setNombre(rs.getString("nombre"));
            //cor.setEstado(rs.getString("estado"));
            //cor.setDescripcion(rs.getString("descripcion"));
            //cor.setRepetir(rs.getString("repetir"));
            String estado = rs.getString("estado");
            rs.close();
            st.close();
            return estado;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("dispositivos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Dispositivo> getDispositivos() {
        try {
            List<Dispositivo> arrayDisp = new ArrayList<>();
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("SELECT * FROM dispositivo");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dispositivo disp = new Dispositivo();
                disp.setId(rs.getString("id"));
                disp.setNombre(rs.getString("nombre"));
                disp.setEstado(rs.getString("estado"));
                disp.setDescripcion(rs.getString("descripcion"));
                disp.setTipo(rs.getString("tipo"));
                disp.setUsuario(rs.getString("usuario"));
                arrayDisp.add(disp);
            }
            rs.close();
            st.close();
            return arrayDisp;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @POST
    @Path("newalarma")
    @Consumes(MediaType.APPLICATION_JSON)
    public Alarma postAlarma(Alarma nuevo) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("INSERT INTO alarma (id_alarma, nombre, estado, hora_inicio, hora_fin, descripcion, fecha_inicio, fecha_fin, id_dispositivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setLong(1, Long.parseLong(nuevo.getId_alarma()));
            st.setString(2, nuevo.getNombre());
            st.setString(3, nuevo.getEstado());
            st.setString(4, nuevo.getHora_inicio());
            st.setString(5, nuevo.getHora_fin());
            st.setString(6, nuevo.getDescripcion());
            st.setString(7, nuevo.getFecha_inicio());
            st.setString(8, nuevo.getFecha_fin());
            st.setString(9, nuevo.getId_dispositivo());
            st.executeUpdate();
            st.close();
            return nuevo;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @DELETE
    @Path("deletealarma")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteAlarma(@QueryParam("id") String id) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("DELETE FROM alarma WHERE id_alarma = ?");
            st.setLong(1, Long.parseLong(id));
            st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("cambioalarma")
    @Consumes(MediaType.APPLICATION_JSON)
    public Alarma putAlarma(Alarma actualizado) {
        try {
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("UPDATE alarma SET hora_inicio = ?, hora_fin = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_dispositivo = ?");
            st.setString(1, actualizado.getHora_inicio());
            st.setString(2, actualizado.getHora_fin());
            st.setString(3, actualizado.getFecha_inicio());
            st.setString(4, actualizado.getFecha_fin());
            st.setLong(5, Long.parseLong(actualizado.getId_alarma()));
            st.executeUpdate();
            st.close();
            return actualizado;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("alarmas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alarma> getDispositivos(@QueryParam("usuario") String usuario) {
        try {
            List<Alarma> arrayAlarma = new ArrayList<>();
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("SELECT * FROM dispositivo");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Alarma alarma = new Alarma();
                alarma.setId_alarma(rs.getString("id_alarma"));
                alarma.setNombre(rs.getString("nombre"));
                alarma.setEstado(rs.getString("estado"));
                alarma.setHora_inicio(rs.getString("hora_inicio"));
                alarma.setHora_fin(rs.getString("hora_fin"));
                alarma.setDescripcion(rs.getString("descripcion"));
                alarma.setFecha_inicio(rs.getString("fecha_inicio"));
                alarma.setFecha_fin(rs.getString("fecha_fin"));
                alarma.setId_dispositivo(rs.getString("id_dispositivo"));
                arrayAlarma.add(alarma);
            }
            rs.close();
            st.close();
            return arrayAlarma;
        } catch (SQLException ex) {
            Logger.getLogger(CasadomoResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
