package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.ArrayList;
import java.util.List;

import Entity.*;
import Utils.*;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DualListModel;
import org.primefaces.util.ConsumerThree;

@ManagedBean
@SessionScoped
public class Validaciones_bean {
    private final static String MENSAJE_NO_PUEDE_ESTAR_VACIO = "No puede estar vacio ";
    private final static String MENSAJE_NO_PUEDE_TENER_NUMEROS="No puede tener números ";
    private final static String TIENEN_QUE_SER_SOLO_LETRAS="Tienen que ser solo letras ";
    private final static String MENSAJE_CANTIDAD_MAXIMA_DE_CARACTERES = "Supera la cantidad máxima de caracteres ";
    private final static String MENSAJE_NO_CARACTERES_ESTRANNOS = "No puede contener caracteres extraños ";
    private final static String MENSAJE_DEBE_CONTENER_LETRAS = "Debe de contener letras ";

    private final static String MENSAJE_NOMBRES_CON_MAYUSCULA = "Las palabras deben de comenzar en mayúscula";
    private final static String MENSAJE_NOMBRES_SOLO_PRIMERA_LETRA_MAYUSCULA = "Solo la primera letra de un nombre debe ser en mayúscula ";
    private final static String MENSAJE_CANTIDAD_MINIMA_DE_CARACTERES = "No supera la cantidad mínima de caracteres ";
    private final static String MENSAJE_CANTIDAD_CARACTERES_TELEFONO = "Un teléfono debe de contener 8 caracteres ";
    private final static String MENSAJE_TODOS_DEBEN_DE_SER_NUMEROS = "Todos los caracteres deben de ser números ";

    private final static String MENSAJE_DEBEN_TENER_NUMEROS = "Debe de contener números";
    private final static String MENSAJE_YA_EXISTE_USUARIO = "Ya existe este usuario ";
    private final static String MENSAJE_COINCIDIR_CONTRASENNAS="Tienen que coincidir las contraseñas ";
    private final static String MENSAJE_FECHA_INVALIDA = "La fecha es inválida.";
    private final static String MENSAJE_FECHAS_INVALIDA = "La fechas son erroneas.";
    private final static String MENSAJE_LA_FECHA_INCIAL_NO_ES_MENOR_QUE_LA_FINAL="La fecha de inicio tiene que ser anterior a la fecha de fin ";
    private final static String MENSAJE_EL_PROFESOR_QUE_ATIENDE_UNA_CAPASITACION_NO_PUEDE_SER_ESTUDIANTE="Hay un profesor que es estudiante seleccionado en ambas tablas ";
    private final static String MENSAJE_SELECCION_PROFESORES_ESTUDIANTES_INVALIDA="La selección de estudiantes y profesores es incorrecta ";


    private final static String ATRIBUTO_MSJ_VALIDACION="mensajeParaValidacion";
    public  static ConexionBD bd=new ConexionBD();
    private static String obtener_validacion_seguridad_cantrasenna(String s,String confirmar){
        s = s.trim();
        if (s.isEmpty()) {
            return MENSAJE_NO_PUEDE_ESTAR_VACIO;
        }
        if (s.length() > 50) {
            return MENSAJE_CANTIDAD_MAXIMA_DE_CARACTERES;
        }
        if (s.length() < 8) {
            return MENSAJE_CANTIDAD_MINIMA_DE_CARACTERES;
        }
        confirmar=confirmar.trim();
        if(!confirmar.equals(s)){
            return MENSAJE_COINCIDIR_CONTRASENNAS;
        }

        boolean contieneLetras = false;
        boolean tieneNumeros=false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isLetter(c)) {
                contieneLetras = true;
            }
            if(Character.isDigit(c)){
                tieneNumeros=true;
            }
            if(tieneNumeros&&contieneLetras){
                break;
            }
        }
        if (!contieneLetras) {
            return MENSAJE_DEBE_CONTENER_LETRAS;
        }
        if (!tieneNumeros) {
            return MENSAJE_DEBEN_TENER_NUMEROS;
        }
        return null;
    }

    private static String obtener_validacion_telefono_cubano(String s) {
        s = s.trim();
        if (s.isEmpty()) {
            return MENSAJE_NO_PUEDE_ESTAR_VACIO;
        }
        if (s.length() != 8) {
            return MENSAJE_CANTIDAD_CARACTERES_TELEFONO;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c)) {
                return MENSAJE_TODOS_DEBEN_DE_SER_NUMEROS;
            }
        }
        return null;
    }

    private static String obtener_validacion_con_letras(String s, int cantidad_minima_caracteres, int cantidad_maxima_caracteres) {
        s = s.trim();
        if (s.isEmpty()) {
            return MENSAJE_NO_PUEDE_ESTAR_VACIO;
        }
        if (s.length() > cantidad_maxima_caracteres) {
            return MENSAJE_CANTIDAD_MAXIMA_DE_CARACTERES;
        }
        if (s.length() < cantidad_minima_caracteres) {
            return MENSAJE_CANTIDAD_MINIMA_DE_CARACTERES;
        }
        boolean contieneLetras = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isLetter(c)) {
                contieneLetras = true;
            }
        }
        if (!contieneLetras) {
            return MENSAJE_DEBE_CONTENER_LETRAS;
        }
        return null;
    }

    private static String obtener_validacion_nombre(String s, int cantidad_minima_caracteres, int cantidad_maxima_caracteres) {
        s = s.trim();
        if (s.isEmpty()) {
            return MENSAJE_NO_PUEDE_ESTAR_VACIO;
        }
        if (s.length() > cantidad_maxima_caracteres) {
            return MENSAJE_CANTIDAD_MAXIMA_DE_CARACTERES;
        }
        if (s.length() < cantidad_minima_caracteres) {
            return MENSAJE_CANTIDAD_MINIMA_DE_CARACTERES;
        }
        boolean contieneLetras = false;
        boolean elAnteriorFueEspacio = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
//            if (!(Character.isLetter(c)  || Character.isWhitespace(c))) {
//                return MSG_NO_CARACTERES_ESTRANNOS;
//            }
            if (Character.isLetter(c)) {
                contieneLetras = true;

                if (i == 0 || elAnteriorFueEspacio) {
                    if (!Character.isUpperCase(c)) {
                        return MENSAJE_NOMBRES_CON_MAYUSCULA;
                    }
                } else {
                    if (Character.isUpperCase(c)) {
                        return MENSAJE_NOMBRES_SOLO_PRIMERA_LETRA_MAYUSCULA;
                    }
                }
                elAnteriorFueEspacio = false;
                continue;
            }
            if (Character.isWhitespace(c)) {
                elAnteriorFueEspacio = true;

                continue;
            }
            return TIENEN_QUE_SER_SOLO_LETRAS;
        }

        if (!contieneLetras) {
            return MENSAJE_DEBE_CONTENER_LETRAS;
        }
        return null;
    }

    private static String obtener_validacion_correo(String email) {

        int posicionarroba, posicionpunto, longitud;
        longitud = email.length();
        posicionarroba = email.indexOf("@");
        posicionpunto = email.indexOf(".");
        if (posicionarroba == -1) {
            System.out.println("No hay arroba en el mail");
            return "No hay arroba en el mail";
        } else if (posicionarroba == 0 || posicionarroba == longitud - 1) {
            System.out.println("La arroba no puede estar ni al principio ni al final");
            return "La arroba no puede estar ni al principio ni al final";
        } else if (posicionarroba != email.lastIndexOf("@")) {
            System.out.println("Existe mas de una @ dentro del mail");
            return "Existe mas de una @ dentro del mail";
        } else if (posicionpunto == -1) {
            System.out.println("No hay punto en el mail");
            return "No hay punto en el mail";
        } else if (posicionpunto == 0 || posicionpunto == longitud - 1) {
            System.out.println("El punto no puede estar ni al principio ni al final");
            return "El punto no puede estar ni al principio ni al final";
        } else if (email.indexOf("..") != -1) {
            System.out.println("No pueden existir dos puntos seguidos");
            return "No pueden existir dos puntos seguidos";
        } else if (email.indexOf("@.") != -1 || email.indexOf(".@") != -1) {
            System.out.println("El punto no puede ir seguido de la @");
            return "El punto no puede ir seguido de la @";
        } else if (email.indexOf(" ") != -1) {
            System.out.println("Un email no puede contenter espacios");
            return "Un email no puede contenter espacios";
        } else {
            String dominio;
            int ultimopunto;
            ultimopunto = email.lastIndexOf(".");
            dominio = email.substring(ultimopunto + 1);
            if (dominio.length() >= 2 && dominio.length() <= 4) {
                System.out.println("EMAIL CORRECTO");
                return null;
            } else {
                System.out.println("El dominio solo puede ser de 2 a 4 caracteres");
                return "El dominio solo puede ser de 2 a 4 caracteres";
            }
        }
    }



    /**
     * <ul>
     * <li>tiene que tener letras como minimo</li>
     * <li>no puede contener caracteres extraños </li>
     * </ul>
     *
     * @param s
     * @return  <ul>
     * <li><b>null</b> si es valido </li>
     * <li><b>String</b> con el mensaje correspondiente si no es valido  </li>
     * </ul>
     */
    private static String obtener_validacion_string_normal(String s, int cantidad_maxima_caracteres) {
        s = s.trim();
        if (s.isEmpty()) {
            return MENSAJE_NO_PUEDE_ESTAR_VACIO;
        }
        if (s.length() > cantidad_maxima_caracteres) {
            return MENSAJE_CANTIDAD_MAXIMA_DE_CARACTERES;
        }
        boolean contieneLetras = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(Character.isAlphabetic(c) || Character.isDigit(c) || Character.isWhitespace(c))) {
                return MENSAJE_NO_CARACTERES_ESTRANNOS;
            }
            if (Character.isLetter(c)) {
                contieneLetras = true;
            }
        }
        if (!contieneLetras) {
            return MENSAJE_DEBE_CONTENER_LETRAS;
        }
        return null;
    }
    private static String obtener_validacion_Fecha(String fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        formatoFecha.setLenient(false);
        try {
            Date fechaParseada = formatoFecha.parse(fecha);
            return null;
        } catch (ParseException ex) {
            return MENSAJE_FECHA_INVALIDA;
        }
    }
    private static String obtener_validacion_fechas_Capasitacion(Date inicial,Date fin){
        if(inicial.compareTo(fin)>=0){
            return MENSAJE_LA_FECHA_INCIAL_NO_ES_MENOR_QUE_LA_FINAL;
        }
        return null;
    }
    private static String obtener_validacion_Listas_ProfesoresEstudiantes(
            List<Profesor> profesores
            ,List<Estudiante> estudiantes
    ){
        try{
        List<Profesor> lp=profesores;
        List<Estudiante> le=estudiantes;

        for (Profesor p :
                lp) {

            if(bd.esEstudiante(p)){
                Universitario u=bd.obtenerUniversitario(p);
                for (Estudiante e :
                        le) {
                    if(bd.obtenerUniversitario(e).getId().equals(u.getId())){
                        return MENSAJE_EL_PROFESOR_QUE_ATIENDE_UNA_CAPASITACION_NO_PUEDE_SER_ESTUDIANTE;
                    }

                }
            }

        }
        }catch (Exception ex){
            responderException(ex);
        }
            return null;
    }
    private static boolean validar_Listas_ProfesoresEstudiantes( List<Profesor> profesores
            ,List<Estudiante> estudiantes){
        String validar_mensaje=obtener_validacion_Listas_ProfesoresEstudiantes(
                profesores,estudiantes);
        if (validar_mensaje!=null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , MENSAJE_SELECCION_PROFESORES_ESTUDIANTES_INVALIDA
                    , validar_mensaje);
            FacesContext.getCurrentInstance().addMessage(null,message);
            return false;
        }
        return true;
    }
    private static  boolean validarFechas_Capasitacion(Date inicial, Date fin){
        String validar_mensaje=obtener_validacion_fechas_Capasitacion(inicial,fin);
        if (validar_mensaje!=null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , MENSAJE_FECHAS_INVALIDA
                    , validar_mensaje);
            FacesContext.getCurrentInstance().addMessage(null,message);
            return false;
        }
        return true;
    }
    public static  boolean validar_Capasitacion(
            List<Profesor> profesores
            ,List<Estudiante> estudiantes
            ,Date inicial, Date fin
    ){
        return validarFechas_Capasitacion(inicial,fin)&&validar_Listas_ProfesoresEstudiantes(profesores,estudiantes);
    }
    public void validarNombre(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try{
        String nombre = (String) value;
        String validarNombre_mensaje=obtener_validacion_nombre(nombre,4,50);
        if (validarNombre_mensaje!=null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , component.getAttributes().get(ATRIBUTO_MSJ_VALIDACION).toString()
                    , validarNombre_mensaje);
            message.setSummary(component.getAttributes().get(ATRIBUTO_MSJ_VALIDACION).toString());
            message.setDetail(validarNombre_mensaje);

            throw new ValidatorException(message);
        }
        }catch (ValidatorException ex){
            throw ex;
        }catch (Exception ex){responderException(ex);}
    }

    public void validarConLetras(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try{
        String nombre = (String) value;
        String validarNombre_mensaje=obtener_validacion_con_letras(nombre,4,50);
        if (validarNombre_mensaje!=null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , component.getAttributes().get(ATRIBUTO_MSJ_VALIDACION).toString()
                    , validarNombre_mensaje);
            message.setSummary(component.getAttributes().get(ATRIBUTO_MSJ_VALIDACION).toString());
            message.setDetail(validarNombre_mensaje);

            throw new ValidatorException(message);
        }}catch (ValidatorException ex){
            throw ex;
        }catch (Exception ex){responderException(ex);}
    }
    private static void responderException(Exception ex){
        System.out.println("error en validacion");
        ex.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                , "Errores en el servidor", ex.getMessage()));
        PrimeFaces.current().ajax().update("form_template:messages_template");
    }

//    public void validarNumero(FacesContext context, UIComponent component, Object value) throws ValidatorException {
//        try{
//            String nombre = ( value.toString()).trim();
//
//            String validarNombre_mensaje=nombre.length()==0&&"12345".contains(nombre)?null:"no es un numero valido";
//            if (validarNombre_mensaje!=null) {
//                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR
//                        , component.getAttributes().get(ATRIBUTO_MSJ_VALIDACION).toString()
//                        , validarNombre_mensaje);
//                message.setSummary(component.getAttributes().get(ATRIBUTO_MSJ_VALIDACION).toString());
//                message.setDetail(validarNombre_mensaje);
//
//                throw new ValidatorException(message);
//            }}catch (ValidatorException ex){
//            throw ex;
//        }catch (Exception ex){responderException(ex);}
//    }
}
