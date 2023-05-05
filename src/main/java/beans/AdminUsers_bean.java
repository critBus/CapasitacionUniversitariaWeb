package beans;

import Entity.Authorities;
import Entity.Users;
import Rest.RestAuthorities;
import Rest.RestUsers;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

@ManagedBean
@SessionScoped
public class AdminUsers_bean {

    private static List<Users> list_users = new ArrayList<Users>();
    private static String username = "";
    private static String identificacion = "";
    private static String nombre = "";
    private static String email = "";
    private static String password = "";
    private static boolean enabled = true;
    private static String descripcion = "";
    private static Users user = new Users();
    private static List<String> list_roles = new ArrayList<>();

    private static RestUsers restUsers = new RestUsers();
    private static RestAuthorities restAuthorities = new RestAuthorities();

    public void init(){
        try {
            list_users.clear();
//        clean_variables();
            list_users = restUsers.findAllUsers();
        }catch (Exception ex){responderException(ex);}

    }

    public List<String> getList_roles() {
        return list_roles;
    }

    public void setList_roles(List<String> list_roles) {
        this.list_roles = list_roles;
    }

    public static RestUsers getRestUsers() {
        return restUsers;
    }

    public static void setRestUsers(RestUsers restUsers) {
        AdminUsers_bean.restUsers = restUsers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Users> getList_users() {
        return list_users;
    }

    public void setList_users(List<Users> list_users) {
        this.list_users = list_users;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void clean_variables(){
        username = "";
        identificacion = "";
        nombre = "";
        email = "";
        password = "";
        enabled = true;
        descripcion = "";
        list_roles.clear();
    }

    public void create_user(){
        try{
        password = DigestUtils.shaHex(password);
        Users user = new Users(username,identificacion,nombre,email,password,enabled,descripcion);
        Users user_finded =  restUsers.findUserByUsername(username);
        if(user_finded!=null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya existe un usuario con ese username", ""));
                PrimeFaces.current().ajax().update("form:messages");
                return;
            }

        if(restUsers.createUser(user)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Adicionado", ""));
            for (String u : list_roles){
                Authorities auth = new Authorities(username,u);
                restAuthorities.createAuthority(auth);
            }
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existen errores en el formulario", ""));
        init();
        list_roles.clear();
        PrimeFaces.current().executeScript("PF('addUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
        }catch (Exception ex){responderException(ex);}
    }

    public void copyedit(Users us) {
        try{
        if(us!=null)
            user = new Users(us.getUsername(),us.getIdentificacion(),us.getNombre(),us.getEmail(),"",us.getEnabled(),us.getDescripcion());
        List<Authorities> l = restAuthorities.findAuthorityByUsername(us.getUsername());
        list_roles.clear();
        for (Authorities lr : l){
            list_roles.add(lr.getAuthoritiesPK().getAuthority());
        }
        }catch (Exception ex){responderException(ex);}
    }
    private void responderException(Exception ex){
        ex.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                , "Errores en el servidor", ex.getMessage()));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }
    public void edit(){
        try{


        Users user_finded = restUsers.findUserByUsername(user.getUsername());
        if(user_finded == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existen errores al editar el usuario", "Usuario inexistente"));
        }
        else{
            if(user.getPassword().equals("")){
                user.setPassword(DigestUtils.shaHex(user_finded.getPassword()));
            }
            else{
                user.setPassword(DigestUtils.shaHex(user.getPassword()));
            }
            if(!restUsers.updateUser(user)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existen errores al editar el usuario", "Errores en el formulario"));
            }
            else{
                List<Authorities> la = restAuthorities.findAuthorityByUsername(user.getUsername());
                for (Authorities a : la){
                    restAuthorities.deleteAuthority(a.getAuthoritiesPK());
                }
                for(String s: getList_roles())
                    restAuthorities.createAuthority(new Authorities(user.getUsername(),s));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Actualizado", ""));
            }
        }
        init();
        list_roles.clear();
        PrimeFaces.current().executeScript("PF('editUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
        }catch (Exception ex){responderException(ex);}
    }

    public void delete(){
        try{
        if(user==null)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existen errores al eliminar el usuario", "Usuario inexistente"));
        else {
            List<Authorities> la = restAuthorities.findAuthorityByUsername(user.getUsername());
            for (Authorities a : la) {
                restAuthorities.deleteAuthority(a.getAuthoritiesPK());
            }
            if (restUsers.deleteUser(user.getUsername())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Eliminado", ""));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , "Errores en el servidor", "No se pudo eliminar el Usuario"));
            }
        }
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
        }catch (Exception ex){responderException(ex);}
    }

    public String translate_estado(boolean text){
        String p =  (text)? "habilitado" : "deshabilitado";
        return p;
    }

    public String translate_color_estado(boolean text){
        String p =  (text)? "green" : "red";
        return p;
    }
}
