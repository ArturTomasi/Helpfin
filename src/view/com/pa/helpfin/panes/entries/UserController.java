package com.pa.helpfin.panes.entries;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Core;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.model.data.UserFilter;
import com.pa.helpfin.view.editor.FilterEditor;
import com.pa.helpfin.view.editor.UserEditor;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.tables.UserTable;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.util.Prompts;
import javafx.event.Event;

/**
 *
 * @author artur
 */
public class UserController 
    extends 
        EntrieController<User>
{
    private UserFilter filter = new UserFilter();

    public UserController() 
    {
        filter.addCondition( UserFilter.STATE, new Option( Core.STATE_ACTIVE, Core.STATES[ Core.STATE_ACTIVE ] ) );
    }


    @Override
    public void filterItem() throws Exception 
    {
        new FilterEditor( new EditorCallback<DefaultFilter>( filter )
        {
            @Override
            public void handle( Event t ) 
            {
                filter = (UserFilter) source;
                
                refresh();
            }
        } ).open();
    }
    
    
    
    @Override
    public void addItem() throws Exception 
    {
        new UserEditor( new EditorCallback<User>( new User() ) 
        {
            @Override
            public void handle( Event t ) 
            {
                try
                {
                    com.pa.helpfin.model.ModuleContext.getInstance()
                                                        .getUserManager()
                                                        .addValue( source );
                    
                    table.setSelectedItem( source );
                    
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
    public void editItem( User item ) throws Exception 
    {
        new UserEditor( new EditorCallback<User>( item )
        {
            @Override
            public void handle( Event t )
            {
                try
                {
                    com.pa.helpfin.model.ModuleContext.getInstance()
                                                        .getUserManager()
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
    public void deleteItem( User item ) throws Exception 
    {
        if( item.equals( ApplicationUtilities.getInstance().getActiveUser() ) )
        {
            Prompts.info( "Você não pode excluir um usuário logado no sistema !" );
            
            return;
        }
        
        com.pa.helpfin.model.ModuleContext.getInstance()
                        .getUserManager()
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
                                                    .getUserManager()
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
        return "usuário";
    }

    
    
    @Override
    public DefaultTable getTable()
    {
        return table;
    }
    
    
    private UserTable table = new UserTable();
}
