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
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("INSERT INTO dispositivo (nombre, estado, descripcion, tipo, usuario) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, nuevo.getNombre());
            st.setString(2, nuevo.getEstado());
            st.setString(3, nuevo.getDescripcion());
            st.setString(4, nuevo.getTipo());
            st.setString(5, nuevo.getUsuario());
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
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("DELETE FROM alarma WHERE id_dispositivo = ?; DELETE FROM dispositivo WHERE id_dispositivo = ?");
            st.setInt(1, Integer.parseInt(id));
            st.setInt(2, Integer.parseInt(id));
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
            st.setInt(2, Integer.parseInt(actualizado.getId()));
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
            st.setInt(1, Integer.parseInt(id));
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
                disp.setId(rs.getString("id_dispositivo"));
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
    
    @GET
    @Path("focos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Dispositivo> getFocos() {
        try {
            List<Dispositivo> arrayDisp = new ArrayList<>();
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("SELECT * FROM dispositivo where tipo = 'Foco'");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dispositivo disp = new Dispositivo();
                disp.setId(rs.getString("id_dispositivo"));
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
    
    @GET
    @Path("cortinas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Dispositivo> getCortinas() {
        try {
            List<Dispositivo> arrayDisp = new ArrayList<>();
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("SELECT * FROM dispositivo where tipo = 'Cortina'");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dispositivo disp = new Dispositivo();
                disp.setId(rs.getString("id_dispositivo"));
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
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("INSERT INTO alarma (nombre, estado, hora_inicio, hora_fin, descripcion, fecha_inicio, fecha_fin, id_dispositivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, nuevo.getNombre());
            st.setString(2, "OFF");
            st.setString(3, nuevo.getHora_inicio());
            st.setString(4, nuevo.getHora_fin());
            st.setString(5, nuevo.getDescripcion());
            st.setString(6, nuevo.getFecha_inicio());
            st.setString(7, nuevo.getFecha_fin());
            st.setInt(8, Integer.parseInt(nuevo.getId_dispositivo()));
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
            st.setInt(1, Integer.parseInt(id));
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
            st.setInt(5, Integer.parseInt(actualizado.getId_alarma()));
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
    public List<Alarma> getAlarmas(@QueryParam("usuario") String usuario) {
        try {
            List<Alarma> arrayAlarma = new ArrayList<>();
            PreparedStatement st = ConexionUnica.getInstance().getConnection().prepareStatement("select E.* from usuario X inner join dispositivo S on S.usuario = X.usuario inner join alarma E on E.id_dispositivo = S.id_dispositivo where X.usuario = ?");
            st.setString(1, usuario);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Alarma alarma = new Alarma();
                alarma.setId_alarma(rs.getString("id_alarma"));
                alarma.setNombre(rs.getString("nombre"));
                alarma.setEstado(rs.getString("estado"));
                alarma.setDescripcion(rs.getString("descripcion"));
                alarma.setFecha_inicio(rs.getString("fecha_inicio"));
                alarma.setFecha_fin(rs.getString("fecha_fin"));
                alarma.setHora_inicio(rs.getString("hora_inicio"));
                alarma.setHora_fin(rs.getString("hora_fin"));
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
