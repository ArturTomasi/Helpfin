package com.pa.helpfin.panes.entries;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Core;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingCategoryFilter;
import com.pa.helpfin.view.editor.FilterEditor;
import com.pa.helpfin.view.editor.PostingCategoryEditor;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.tables.PostingCategoryTable;
import com.pa.helpfin.view.util.EditorCallback;
import javafx.event.Event;

/**
 * @author artur
 */
public class PostingCategoryController 
    extends 
        EntrieController<PostingCategory>
{
    private PostingCategoryFilter filter = new PostingCategoryFilter();

    public PostingCategoryController() 
    {
        filter.addCondition( PostingCategoryFilter.STATE, new Option( Core.STATE_ACTIVE, Core.STATES[ Core.STATE_ACTIVE ] ) );
    }

    @Override
    public void filterItem() throws Exception 
    {
        new FilterEditor( new EditorCallback<DefaultFilter>( filter )
        {
            @Override
            public void handle( Event t )
            {
                filter = (PostingCategoryFilter) source;
                
                refresh();
            }
        } ).open();
    }
    
    
    
    
    @Override
    public void addItem() throws Exception 
    {
        new PostingCategoryEditor( new EditorCallback<PostingCategory>( new PostingCategory() ) 
        {
            @Override
            public void handle( Event t ) 
            {
                try
                {
                    com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getPostingCategoryManager()
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
    public void editItem( PostingCategory item ) throws Exception
    {
        new PostingCategoryEditor( new EditorCallback<PostingCategory>( item ) 
        {
            @Override
            public void handle( Event t ) 
            {
                try
                {
                    com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getPostingCategoryManager()
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
    public void deleteItem( PostingCategory item ) throws Exception 
    {
        com.pa.helpfin.model.ModuleContext
                                .getInstance()
                                .getPostingCategoryManager()
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
                                .getPostingCategoryManager()
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
        return "Categorias de Lançamento";
    }

    
    
    @Override
    public DefaultTable<PostingCategory> getTable() 
    {
        return table;
    }
    
    private PostingCategoryTable table = new PostingCategoryTable();
}
