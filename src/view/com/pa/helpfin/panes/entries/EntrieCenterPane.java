package com.pa.helpfin.panes.entries;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Core;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.panes.LegendPane;
import com.pa.helpfin.panes.modules.AbstractModulesPane;
import com.pa.helpfin.view.util.ActionButton;
import com.pa.helpfin.view.util.Prompts;
import java.util.Arrays;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import static com.pa.helpfin.panes.modules.EntriePane.*;

/**
 * @author artur
 * @param <T>
 */
public class EntrieCenterPane <T extends Core>
    extends 
        AbstractModulesPane
{
    public EntrieCenterPane() 
    {
        initComponents();
    }
    
    
    public void setSelectedEntrie( int index )
    {
        switch( index )
        {
            case VIEW_USERS:
            {
                controller = userController;
            }
            break;
                
            case VIEW_ENTITY:
            {
                controller = entityController;
            }
            break;
                
            case VIEW_COMPLETION:
            {
                controller = completionController;
            }
            break;
                
            case VIEW_CATEGORY:
            {
                controller = postingCategoryController;
            }
            break;
                
            case VIEW_TYPE:
            {
                controller = postingTypeController;
            }
            break;
        }
    }
    
    
    @Override
    public List<Button> getActions() 
    {
        filterItem.setDisable( ! ApplicationUtilities.getInstance().hasPermission() || controller == postingTypeController );
        
        return Arrays.asList( addItem, editItem, deleteItem, filterItem );
    }

    
    
    @Override
    public void refreshContent() 
    {
        try
        {
            controller.refresh();
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }

    
    
    @Override
    public void resizeComponents( double height, double width )
    {
        getChildren().clear();
        getChildren().addAll( controller.getTable(), legendPane );
        
        legendPane.setPrefWidth( width );
        legendPane.setLayoutY( height - legendPane.getHeight() );
        
        controller.getTable().setPrefSize( width, height - legendPane.getHeight() );
        
        requestLayout();
    }
    
    
    
    private void deleteItem()
    {
        try
        {
            Core item = controller.getSelectedItem();

            if( item != null && item.getState() == Core.STATE_INACTIVE )
            {
                Prompts.info( controller.getEntrieName() + " já encontra-se excluido!" );
            }
           
            else if( item == null )
            {
                Prompts.alert( "Selecione um " + controller.getEntrieName() + " para excluir" );
            }
            
            else
            {
                if( Prompts.confirm( "Você tem certeza que deseja excluir o " + controller.getEntrieName() + "\n" + item ) )
                    controller.deleteItem( item );
            }

        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    private void editItem()
    {
        try
        {
            Core item = controller.getSelectedItem();

            if( item != null )
            {
                controller.editItem( item );
            }

            else
            {
                Prompts.alert( "Selecione um " + controller.getEntrieName() + " para editar" );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    private void addItem()
    {
        try
        {
            controller.addItem();
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    private void filterItem()
    {
        try
        {
            controller.filterItem();
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    private void initComponents()
    {
        legendPane.addItems( new LegendPane.LegendItem( User.STATES[ User.STATE_ACTIVE ],   "finish.png" ),
                             new LegendPane.LegendItem( User.STATES[ User.STATE_INACTIVE ], "delete.png" ) );
        
        getChildren().addAll( controller.getTable(), legendPane );
    }
    
    
    
    private ActionButton addItem = new ActionButton( "Novo", "new.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            addItem();
        }
    } );
    
    private ActionButton editItem = new ActionButton( "Editar", "edit.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            editItem();
        }
    } );
    
    private ActionButton deleteItem = new ActionButton( "Excluir", "delete.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            deleteItem();
        }
    } );
    
    private ActionButton filterItem = new ActionButton( "Filtro", "search.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            filterItem();
        }
    } );
    
    private LegendPane legendPane =  new LegendPane();
    
    private UserController   userController       = new UserController();
    private EntityController entityController     = new EntityController();
    private CompletionTypeController completionController = new CompletionTypeController();
    private PostingCategoryController postingCategoryController = new PostingCategoryController();
    private PostingTypeController postingTypeController = new PostingTypeController();
    
    private EntrieController controller = userController;
}
