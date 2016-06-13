package com.pa.helpfin.view.editor.postingEditor;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.view.util.DateField;
import com.pa.helpfin.view.util.NumberTextField;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

/**
 * @author artur
 */
public class ValuesPane 
    extends 
        Tab
{
    private Posting posting;
    private HashMap<Integer, DateField> mapDates        = new HashMap();
    private HashMap<Integer, NumberTextField> mapValues = new HashMap();
    private Calendar calendar = Calendar.getInstance( ApplicationUtilities.getLocale() );
            
    public ValuesPane() 
    {
        initComponents();
    }
    
    public void resize( double width, double height )
    {
        //TO DO
    }
    
    
    public void validateInput( List<String> erros )
    {
        if( posting.isRepeat() && posting.getPortionTotal() - 1 != mapDates.size() )
        {
            erros.add( "Verifique todos os valores das parcelas" );
        }
        
        if( posting.isRepeat() )
        {
            mapDates.values().stream().forEach( (field) ->
            {
                if ( field.getDate() == null )
                {
                    erros.add( "Preencha todas as datas \"ABA VALORES\"" );
                    return;
                }

            } );

            mapValues.values().stream().forEach( (value) -> 
            {
              if( value.getDouble() <= 0.0 )
              {
                  erros.add( "Preencha todas os valores \"ABA VALORES\"" );
                  return;
              }
            } );
        }
    }
    
    
    
    public void obtainInput( Posting posting, Properties properties )
    {
        this.posting = posting;
        
        posting.setPortion( 1 );
        
        if( posting.isRepeat() )
        {
            List<Posting> postings = new ArrayList();
            
            for ( int i = 1; i < posting.getPortionTotal() ; i++ )
            {
                Posting p = posting.clone();
                
                p.setEstimateDate( mapDates.get( i - 1 ).getDate() );
                p.setEstimateValue( mapValues.get( i - 1 ).getDouble() );
                p.setPortion( i + 1 );
                p.setState( Posting.STATE_REGISTRED );
                p.setRealDate( null );
                p.setRealValue( 0d );
                
                postings.add( p );
            }
            
            if( ! postings.isEmpty() && posting.getState() != Posting.STATE_PROGRESS )
            {
                postings.get( 0 ).setState( Posting.STATE_PROGRESS );
            }
            
            properties.put( "Postings", postings );
        }
    }
    
    
    
    public void setSource( Posting posting )
    {
        this.posting = posting;
        
        updateGrid();
    }
        
    
    
    private void updateGrid()
    {
        if( posting != null && posting.isRepeat() )
        {
            gridPane.getChildren().clear();

            gridPane.addRow( 0, new Label( "Parcelas" ), new Label( "Data Estimada" ), new Label( "Valor Estimado" ) );

            calendar.setTime( posting.getEstimateDate() );
            
            for ( int i = 0; i < posting.getPortionTotal() - 1; i++ )
            {
                DateField dateField   = new DateField();
                NumberTextField numberField = new NumberTextField();
                
                if( mapDates.get( i ) != null && mapValues.get( i ) != null ) //Troca de abas
                {
                    calendar.setTime( mapDates.get( i ).getDate() );

                    dateField.setDate( new Date( calendar.getTimeInMillis() ) );
                    numberField.setDouble( mapValues.get( i ).getDouble() );
                }
                
                else 
                {
                    calendar.add( Calendar.MONTH, 1 );

                    dateField.setDate( new Date( calendar.getTimeInMillis() ) );
                    numberField.setDouble( posting.getEstimateValue() );
                }
                
                mapDates.put( i,  dateField );
                mapValues.put( i, numberField );
                
                gridPane.addRow( i + 1, 
                                new Label( calendar.getDisplayName( Calendar.MONTH, Calendar.LONG, ApplicationUtilities.getLocale() ) ), 
                                dateField, 
                                numberField );
            }
        }
    }
    
    
    
    private void initComponents()
    {
        gridPane.setVgap( 20 );
        gridPane.setHgap( 20 );
        gridPane.setStyle( "-fx-padding: 30;" );
        
        setClosable( false );
        setText( "Valores" );
        
        setContent( new ScrollPane( gridPane ) );
    }
    
    private GridPane gridPane = new GridPane();
}
