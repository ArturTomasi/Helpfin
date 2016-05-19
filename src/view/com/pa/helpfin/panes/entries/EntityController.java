package com.pa.helpfin.panes.entries;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Core;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.Entity;
import com.pa.helpfin.model.data.EntityFilter;
import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.view.editor.EntityEditor;
import com.pa.helpfin.view.editor.FilterEditor;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.tables.EntityTable;
import com.pa.helpfin.view.util.EditorCallback;
import javafx.event.Event;

/**
 * @author artur
 */
public class EntityController
    extends 
        EntrieController<Entity>
{
    private EntityFilter filter = new EntityFilter();

    public EntityController() 
    {
        filter.addCondition( EntityFilter.STATE, new Option( Core.STATE_ACTIVE, Core.STATES[ Core.STATE_ACTIVE ] ) );
    }

    @Override
    public void filterItem() throws Exception 
    {
        new FilterEditor( new EditorCallback<DefaultFilter>(filter ) 
        {
            @Override
            public void handle( Event t )
            {
                filter = (EntityFilter) source;
                
                refresh();
            }
        } ).open();
    }
    
    
            
    @Override
    public void addItem() throws Exception 
    {
        new EntityEditor( new EditorCallback<Entity>( new Entity() ) 
        {
            @Override
            public void handle( Event t ) 
            {
                try
                {
                    com.pa.helpfin.model.ModuleContext.getInstance()
                                                        .getEntityManager()
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
    public void editItem( Entity item ) throws Exception 
    {
        new EntityEditor( new EditorCallback<Entity>( item )
        {
            @Override
            public void handle( Event t )
            {
                try
                {
                    com.pa.helpfin.model.ModuleContext.getInstance()
                                                        .getEntityManager()
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
    public void deleteItem( Entity item ) throws Exception 
    {
        com.pa.helpfin.model.ModuleContext.getInstance()
                        .getEntityManager()
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
                                                    .getEntityManager()
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
        return "Entidade";
    }

    
    
    @Override
    public DefaultTable getTable()
    {
        return table;
    }
    
    
    private EntityTable table = new EntityTable();
}
