
package cocheriazurdo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Fecha_Hora {
    
    String fechaSystem;

    public Fecha_Hora() {
    }  

    public String obtenerFechaSystem(){   
    
    Calendar c2 = new GregorianCalendar();
        
    Date fecha = new Date();
	SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
                
        int dia = c2.get(Calendar.DAY_OF_MONTH);
        int mes = c2.get(Calendar.MONTH);
        int anio = c2.get(Calendar.YEAR);
        
       fechaSystem = (dia+"/"+(mes+1)+"/"+anio);
    
    return fechaSystem;
    }
    
    public String getEdad(Date fechaNacimiento) {
    if (fechaNacimiento != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder result = new StringBuilder();
        if (fechaNacimiento != null) {
            Calendar c = new GregorianCalendar();
            c.setTime(fechaNacimiento);
            result.append(calcularEdad(c));
        }
        return result.toString();
    }
    return "";
    }
    
    private int calcularEdad(Calendar fechaNac) {
    Calendar today = Calendar.getInstance();
    int diffYear = today.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
    int diffMonth = today.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
    int diffDay = today.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
    // Si está en ese año pero todavía no los ha cumplido
    if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0)) {
        diffYear = diffYear - 1;
    }
    return diffYear;
}
    
    public String getFechaCobertura(Date fechaNac, Date fechaAlta){
        
        String a = getEdad(fechaNac);
        
        int edad = Integer.parseInt(a);

        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(fechaAlta); // Configuramos la fecha que se recibe
        
        if(edad>0 && edad<=5){            
        calendar.add(Calendar.MONTH,1);  // numero de meses a añadir
        
        int dia =calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);
        
        return (dia+"/"+(mes+1)+"/"+anio);
        }
        else{
            if(edad>5 && edad<=10){
                calendar.add(Calendar.MONTH,2);  // numero de meses a añadir
        
                int dia =calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int anio = calendar.get(Calendar.YEAR);
        
                return (dia+"/"+(mes+1)+"/"+anio);
            }
            else{
                if(edad>10 && edad<=15){
                calendar.add(Calendar.MONTH,3);  // numero de meses a añadir
        
                int dia =calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int anio = calendar.get(Calendar.YEAR);
        
                return (dia+"/"+(mes+1)+"/"+anio);
                }
                else{
                    if(edad>15 && edad<=20){
                        calendar.add(Calendar.MONTH,4);  // numero de meses a añadir

                        int dia =calendar.get(Calendar.DAY_OF_MONTH);
                        int mes = calendar.get(Calendar.MONTH);
                        int anio = calendar.get(Calendar.YEAR);

                        return (dia+"/"+(mes+1)+"/"+anio);
                    }
                    else{
                        if(edad>20 && edad<=25){
                            calendar.add(Calendar.MONTH,5);  // numero de meses a añadir

                            int dia =calendar.get(Calendar.DAY_OF_MONTH);
                            int mes = calendar.get(Calendar.MONTH);
                            int anio = calendar.get(Calendar.YEAR);

                            return (dia+"/"+(mes+1)+"/"+anio);
                        }
                        else{
                            if(edad>25 && edad<=30){
                                calendar.add(Calendar.MONTH,6);  // numero de meses a añadir
        
                                int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                int mes = calendar.get(Calendar.MONTH);
                                int anio = calendar.get(Calendar.YEAR);

                                return (dia+"/"+(mes+1)+"/"+anio);                          
                                }
                            else{
                                if(edad>30 && edad<=35){
                                    calendar.add(Calendar.MONTH,7);  // numero de meses a añadir

                                    int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                    int mes = calendar.get(Calendar.MONTH);
                                    int anio = calendar.get(Calendar.YEAR);

                                    return (dia+"/"+(mes+1)+"/"+anio);
                                }
                                else{
                                    if(edad>35 && edad<=40){
                                        calendar.add(Calendar.MONTH,9);  // numero de meses a añadir

                                        int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                        int mes = calendar.get(Calendar.MONTH);
                                        int anio = calendar.get(Calendar.YEAR);

                                        return (dia+"/"+(mes+1)+"/"+anio);
                                    }
                                    else{
                                        if(edad>40 && edad<=45){
                                            calendar.add(Calendar.MONTH,11);  // numero de meses a añadir

                                            int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                            int mes = calendar.get(Calendar.MONTH);
                                            int anio = calendar.get(Calendar.YEAR);

                                            return (dia+"/"+(mes+1)+"/"+anio);
                                        }
                                        else{
                                            if(edad>45 && edad<=50){
                                                calendar.add(Calendar.MONTH,13);  // numero de meses a añadir

                                                int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                                int mes = calendar.get(Calendar.MONTH);
                                                int anio = calendar.get(Calendar.YEAR);

                                                return (dia+"/"+(mes+1)+"/"+anio);
                                            }
                                            else{
                                                if(edad>50 && edad<=55){
                                                    calendar.add(Calendar.MONTH,15);  // numero de meses a añadir

                                                    int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                                    int mes = calendar.get(Calendar.MONTH);
                                                    int anio = calendar.get(Calendar.YEAR);

                                                    return (dia+"/"+(mes+1)+"/"+anio);
                                                }
                                                else{
                                                    if(edad>55 && edad<=60){
                                                        calendar.add(Calendar.MONTH,17);  // numero de meses a añadir

                                                        int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                                        int mes = calendar.get(Calendar.MONTH);
                                                        int anio = calendar.get(Calendar.YEAR);

                                                        return (dia+"/"+(mes+1)+"/"+anio);
                                                    }
                                                    else{
                                                        if(edad>60 && edad<=65){
                                                            calendar.add(Calendar.MONTH,19);  // numero de meses a añadir

                                                            int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                                            int mes = calendar.get(Calendar.MONTH);
                                                            int anio = calendar.get(Calendar.YEAR);

                                                            return (dia+"/"+(mes+1)+"/"+anio);
                                                        }
                                                        else{
                                                            if(edad>65 && edad<=70){
                                                                calendar.add(Calendar.MONTH,21);  // numero de meses a añadir

                                                                int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                                                int mes = calendar.get(Calendar.MONTH);
                                                                int anio = calendar.get(Calendar.YEAR);

                                                                return (dia+"/"+(mes+1)+"/"+anio);
                                                            }
                                                            else{
                                                                if(edad>70 && edad<=75){
                                                                    calendar.add(Calendar.MONTH,23);  // numero de meses a añadir

                                                                    int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                                                    int mes = calendar.get(Calendar.MONTH);
                                                                    int anio = calendar.get(Calendar.YEAR);

                                                                    return (dia+"/"+(mes+1)+"/"+anio);
                                                                }
                                                                else{
                                                                    if(edad>75 && edad<=80){
                                                                        calendar.add(Calendar.MONTH,25);  // numero de meses a añadir

                                                                        int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                                                        int mes = calendar.get(Calendar.MONTH);
                                                                        int anio = calendar.get(Calendar.YEAR);

                                                                        return (dia+"/"+(mes+1)+"/"+anio);
                                                                    }
                                                                    else{
                                                                        calendar.add(Calendar.MONTH,27);  // numero de meses a añadir

                                                                        int dia =calendar.get(Calendar.DAY_OF_MONTH);
                                                                        int mes = calendar.get(Calendar.MONTH);
                                                                        int anio = calendar.get(Calendar.YEAR);

                                                                        return (dia+"/"+(mes+1)+"/"+anio);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                        }
                                    }
                                    }
                            }
                        }
                    }
                }
            }
            
        }
    }
}