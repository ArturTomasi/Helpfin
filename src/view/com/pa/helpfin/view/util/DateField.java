package com.pa.helpfin.view.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

/**
 * @author artur
 */
public class DateField 
    extends 
        DatePicker
{
    public DateField() 
    {
        setConverter( new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );

            @Override
            public String toString( LocalDate localDate )
            {
                if( localDate == null )
                    return "";
                
                return dateTimeFormatter.format( localDate );
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString == null || dateString.trim().isEmpty() )
                {
                    return null;
                }
                
                return LocalDate.parse( dateString,dateTimeFormatter );
            }
        } );
    }
    
    public Date getDate()
    {
        LocalDate date = getValue();
        
        if( date != null )
        {
            return Date.valueOf( date );
        }
        
        return null;
    }
   
    public void setDate( Date date )
    {
        if( date != null )
        {
            setValue( date.toLocalDate() );
        }
    }
}
