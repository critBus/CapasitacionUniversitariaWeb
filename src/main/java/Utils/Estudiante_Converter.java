package Utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;


import Entity.*;
@Named
@FacesConverter(value = "estudianteConverter")
public class Estudiante_Converter implements Converter{
    public Estudiante_Converter() {
    }

    public static ConexionBD bd=new ConexionBD();
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                //System.out.println("value estudiante converter:"+value);
                return bd.obtenerEstudiante(Integer.parseInt(value.toString()));
            } catch(Exception e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , "Conversion Errornea de Estudiante",
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
            return String.valueOf(((Estudiante) object).getId());
        }
        else {
            return null;
        }
    }
}
