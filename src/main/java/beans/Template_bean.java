package beans;

import Entity.Authorities;
import Entity.Users;
import Rest.RestAuthorities;
import Rest.RestUsers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class Template_bean {

    private static String username;
    private static List<String> roles = new ArrayList<String>();
    private Users user;
    private static final RestAuthorities restAuthorities= new RestAuthorities();
    private static final RestUsers restUsers = new RestUsers();

    /**
     *
     * Role Admin. Colocar más roles si lo necesitan
     */
    private boolean role_admin = false;
    private boolean role_user = false;

    public String currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public void init() {
        username = currentUser();
        List<Authorities> list_auth = restAuthorities.findAuthorityByUsername(username);
        roles.clear();
        for (Authorities auth: list_auth)
            roles.add(auth.getAuthoritiesPK().getAuthority());
        user = restUsers.findUserByUsername(username);
        verifyRol();
    }



    public void verifyRol(){
        for(String l : roles)
        {
            switch (l){
                case "ROLE_ADMIN":
                    role_admin = true;
                    break;
                case "ROLE_USER":
                    role_user = true;
            }
        }
    }

    public boolean translateRole_Boolean(boolean... r) {
        boolean t = false;
        for (int i = 0; i < r.length; i++) {
            t = t || r[i];
        }
        return t;
    }

    public String translateRole_visible(boolean... r){
        if(translateRole_Boolean(r))
            return "";
        return "display: none;";
    }

    public boolean isRole_admin() {
        return role_admin;
    }

    public void setRole_admin(boolean role_admin) {
        this.role_admin = role_admin;
    }

    public boolean isRole_user() {
        return role_user;
    }

    public void setRole_user(boolean role_user) {
        this.role_user = role_user;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Template_bean.username = username;
    }

    public static List<String> getRoles() {
        return roles;
    }

    public static void setRoles(List<String> roles) {
        Template_bean.roles = roles;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
