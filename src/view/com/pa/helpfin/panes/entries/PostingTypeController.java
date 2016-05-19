package com.pa.helpfin.panes.entries;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.PostingType;
import com.pa.helpfin.view.editor.PostingTypeEditor;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.tables.PostingTypeTable;
import com.pa.helpfin.view.util.EditorCallback;
import javafx.event.Event;

/**
 *
 * @author artur
 */
public class PostingTypeController 
    extends 
        EntrieController<PostingType>
{

    @Override
    public void filterItem() throws Exception 
    {
    }
    
    
    
    @Override
    public void addItem() throws Exception 
    {
          new PostingTypeEditor( new EditorCallback<PostingType>( new PostingType() ) 
        {
            @Override
            public void handle( Event t ) 
            {
                try
                {
                    com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getPostingTypeManager()
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
    public void editItem( PostingType item ) throws Exception
    {
        new PostingTypeEditor( new EditorCallback<PostingType>( item ) 
        {
            @Override
            public void handle( Event t ) 
            {
                try
                {
                    com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getPostingTypeManager()
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
    public void deleteItem( PostingType item ) throws Exception 
    {
        com.pa.helpfin.model.ModuleContext
                                .getInstance()
                                .getPostingTypeManager()
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
                                .getPostingTypeManager()
                                .getValues( true ) );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    

    @Override
    public String getEntrieName() 
    {
        return "Tipos de Lançamento";
    }

    
    
    @Override
    public DefaultTable<PostingType> getTable() 
    {
        return table;
    }
    
    private PostingTypeTable table = new PostingTypeTable();
}
