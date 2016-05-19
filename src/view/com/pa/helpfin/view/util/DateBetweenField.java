package com.pa.helpfin.view.util;

import java.sql.Date;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author artur
 */
public class DateBetweenField 
    extends 
        GridPane
{
    public DateBetweenField() 
    {
        initComponents();
    }
    
    
    
    public Date[] getValue()
    {
        if( validDates() == null )
            return new Date[]{ dateStart.getDate(), dateEnd.getDate() };
        
        return null;
    }
    
    
    
    public void setValue( Date[] dates )
    {
        if( dates != null && dates.length == 2 )
        {
            dateStart.setDate( dates[ 0 ]);
            dateEnd.setDate( dates[ 1 ]);
        }
    }
    
    
    
    public Date getEndDate()
    {
        return dateEnd.getDate();
    }
    
    
    
    public Date getStartDate()
    {
        return dateStart.getDate();
    }
    
    
    
    public String validDates()
    {
        if( dateEnd.getDate() == null )
            return "Data Fim: Não preenchida \n";
        
        if( dateStart.getDate() == null )
            return "Data Inicio: Não preenchida \n";
        
        if( dateStart.getDate().after( dateEnd.getDate() ) )
            return "Data Inicio maior que a data final\n";
        
        if( dateEnd.getDate().before( dateStart.getDate() ) )
            return "Data Final menor que a data inicial\n";
        
        
        return null;
    }
    
    private void initComponents()
    {
        ColumnConstraints colStart = new ColumnConstraints();
        colStart.setPercentWidth( 48 );
        colStart.setHalignment( HPos.CENTER );
    
        ColumnConstraints colName = new ColumnConstraints();
        colName.setPercentWidth( 4 );
        colName.setHalignment( HPos.LEFT );
     
        ColumnConstraints colEnd = new ColumnConstraints();
        colEnd.setPercentWidth( 48 );
        getColumnConstraints().addAll( colStart, colName, colEnd );
        
        dateStart.setPrefWidth( colStart.getMaxWidth() );
        dateEnd.setLayoutX( colEnd.getMaxWidth() );
        
        addColumn( 0, dateStart );
        addColumn( 1, new Label( " a " ) );
        addColumn( 2, dateEnd );
    }
    
    private DateField dateStart = new DateField();
    private DateField dateEnd   = new DateField();
}