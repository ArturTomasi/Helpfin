package com.pa.helpfin.panes.entries;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.CompletionType;
import com.pa.helpfin.model.data.CompletionTypeFilter;
import com.pa.helpfin.model.data.Core;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.view.editor.CompletionTypeEditor;
import com.pa.helpfin.view.editor.FilterEditor;
import com.pa.helpfin.view.tables.CompletionTypeTable;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.util.EditorCallback;
import javafx.event.Event;

/**
 * @author artur
 */
public class CompletionTypeController
    extends
        EntrieController<CompletionType>
{

    private CompletionTypeFilter filter = new CompletionTypeFilter();
    
    public CompletionTypeController() 
    {
        filter.addCondition( CompletionTypeFilter.STATE, new Option( Core.STATE_ACTIVE, Core.STATES[ Core.STATE_ACTIVE ] ) );
    }

    
    @Override
    public void filterItem() throws Exception 
    {
        new FilterEditor( new EditorCallback<DefaultFilter>( filter ) 
        {
            @Override
            public void handle( Event t )
            {
                filter = (CompletionTypeFilter) source;
                
                refresh();
            }
        } ).open();
    }
    
    
    
    @Override
    public void addItem() throws Exception
    {
        new CompletionTypeEditor( new EditorCallback<CompletionType>( new CompletionType() ) 
        {
            @Override
            public void handle( Event t ) 
            {
                try
                { 
                    com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getCompletionTypeManager()
                                        .addValue( source );
                    refresh();
                }
                
                catch( Exception e )
                {
                    ApplicationUtilities.logException( e );
                }
            }
        } ).open();
    }

    
    
    @Override
    public void editItem( CompletionType item ) throws Exception 
    {
        new CompletionTypeEditor( new EditorCallback<CompletionType>( item ) 
        {
            @Override
            public void handle( Event t ) 
            {
                try
                { 
                    com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getCompletionTypeManager()
                                    .updateValue( source );
                    refresh();
                }
                
                catch( Exception e )
                {
                    ApplicationUtilities.logException( e );
                }
            }
        } ).open();
    }

    
    
    @Override
    public void deleteItem( CompletionType item ) throws Exception 
    {
        com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getCompletionTypeManager()
                                    .deleteValue( item );
        refresh();
    }

    
    
    @Override
    public void refresh()
    {
        try
        {
            table.setItems( com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getCompletionTypeManager()
                                    .getValues( filter ) );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }

    
    
    @Override
    public String getEntrieName() 
    {
        return "Tipo de Finalização";
    }
    
    

    @Override
    public DefaultTable<CompletionType> getTable() 
    {
        return table;
    }
    
    private CompletionTypeTable  table = new CompletionTypeTable();
}
