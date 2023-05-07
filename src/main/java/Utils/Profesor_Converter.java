package Utils;

import Entity.Profesor;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import javax.faces.convert.FacesConverter;

@Named
@FacesConverter(value = "profesorConverter")
public class Profesor_Converter implements Converter{
    public Profesor_Converter() {
    }

    public static ConexionBD bd=new ConexionBD();
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                //System.out.println("value Profesor converter:"+value);
                return bd.obtenerProfesor(Integer.parseInt(value.toString()));
            } catch(Exception e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , "Conversion Errornea de Profesor",
                        "No es un id valido."
                ));
            }
        }
        else {
            return null;
        }
    }
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Profesor) object).getId());
        }
        else {
            return null;
        }
    }
}
