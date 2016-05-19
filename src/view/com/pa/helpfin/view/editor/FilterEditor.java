package com.pa.helpfin.view.editor;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.DefaultFilter.FilterItem;
import com.pa.helpfin.panes.ActionPane;
import com.pa.helpfin.view.selectors.ListPicker;
import com.pa.helpfin.view.util.ActionButton;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.util.FilterItemField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

/**
 * @author artur
 */
public class FilterEditor 
    extends 
        AbstractEditor<DefaultFilter>
{
    private List<FilterItemField> filterItemFields = new ArrayList();

    public FilterEditor( EditorCallback<DefaultFilter> callback )
    {
        super( callback );
        
        initComponents();
        
        setSource( source );
    }

    @Override
    protected void validadeInput( List<String> erros ) throws Exception 
    {
        filterItemFields.forEach( item -> 
        {
            item.validadeField( erros );
        } );
    }

    @Override
    protected void obtainInput() 
    {
        source.clearConditions();
        
        filterItemFields.forEach( item -> 
        {
            if( item.getValue() != null && item.getFilterId() != -1  )
                source.addCondition( item.getFilterId(), item.getValue() );
        } );
    }

    @Override
    protected void resize() 
    {
        tilePane.setPrefColumns( 1 );
        tilePane.setPrefWidth( getWidth() - actionPane.getWidth() - 25 );
        getDialogPane().requestLayout();
    }

    @Override
    protected void setSource( DefaultFilter source )
    {
        filters.setItems( source.getComponents() );
        
        source.getConditions().forEach( ( key, values ) ->
        {
            FilterItem it = source.getComponents().get( key );
            
            values.forEach( value -> 
            {
                FilterItemField fi = new FilterItemField( it );
                fi.setPrefWidth( 670 );
                fi.setValue( value );
                filterItemFields.add( fi ); 
            } );
        } );
        
        updatePane();
    }
    
    private void choice()
    {
        try
        {
            filters.open( "Escolha seu animal" );

            DefaultFilter.FilterItem c  = filters.getSelected();

            if( c != null )
            {
                FilterItemField filterItemField =  new FilterItemField( c );
                
                filterItemField.setPrefWidth( tilePane.getWidth() );
                
                filterItemFields.add( filterItemField );
            }
            
            updatePane();
        }
        
        catch ( Exception e) 
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    private void updatePane()
    {
        tilePane.getChildren().clear();
        
        filterItemFields.forEach( (filterItem) -> tilePane.getChildren().add( filterItem ) );
        
        getDialogPane().requestLayout();
    }
    
    
    private void delete()
    {
        filterItemFields.removeIf( (item) -> item.isSelected() );
        updatePane();
    }
    
    
    private void initComponents()
    {
        getDialogPane().setPrefSize( 800, 600 );
        
        actionPane.setActions( Arrays.asList( actionNew, actionDelete ) );
        
        borderPane.setCenter( scrollPane );
        borderPane.setRight( actionPane );
    
        getDialogPane().setContent( borderPane );
    }
    
    private TilePane tilePane      = new TilePane();
    private ScrollPane scrollPane = new ScrollPane( tilePane );
    private ActionPane actionPane = new ActionPane();
    private BorderPane borderPane = new BorderPane();
    
    private ListPicker<DefaultFilter.FilterItem> filters = new ListPicker();
    
    private ActionButton actionNew = new ActionButton( "Novo", "new.png", new EventHandler() 
    {
        @Override
        public void handle(Event t) 
        {
            choice();
        }
    } );
    private ActionButton actionDelete = new ActionButton( "Excluir", "delete.png", new EventHandler() 
    {
        @Override
        public void handle(Event t) 
        {
            delete();
        }
    } );
}
