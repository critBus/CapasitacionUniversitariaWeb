package Rest;

import Entity.AuthoritiesPK;

public class RestTEST {
    public static void main(String[] args) {
//        RestAuthorities restAuthorities = new RestAuthorities();
//        AuthoritiesPK authoritiesPK = new AuthoritiesPK("admin","ROLE_PF");
//        System.out.println(restAuthorities.deleteAuthority(authoritiesPK));
//        System.out.println(restAuthorities.findAllAuthorities());

        RestUniversitario ru=new RestUniversitario();
        System.out.println(ru.findAll());
    }
}
