package com.pa.helpfin.view.tables;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.PostingCategory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * @author artur
 */
public class PostingCategoryTable 
    extends 
        DefaultTable<PostingCategory>
{
    private Image activeUserImage = new Image( ResourceLocator.getInstance().getImageResource( "finish.png" ) ); 
    private Image deleteUserImage = new Image( ResourceLocator.getInstance().getImageResource( "delete.png" ) );
    
    public PostingCategoryTable() 
    {
        setColumns( new DefaultTable.ItemColumn( "#", "state", new Callback< TableColumn<PostingCategory, Integer>, TableCell<PostingCategory, Integer> >() 
        {
            @Override
            public TableCell<PostingCategory, Integer> call( TableColumn<PostingCategory, Integer> p ) 
            {
                return new TableCell<PostingCategory, Integer>() 
                {
                    @Override
                    protected void updateItem( Integer item, boolean empty )
                    {
                        super.updateItem( item, empty);

                        if ( empty || item == null )
                        {
                            setText(null);
                            setTextFill(null);
                            setGraphic( null );
                        }

                        else if( ! empty )
                        {
                            ImageView imageView =  new ImageView( item == PostingCategory.STATE_ACTIVE ? activeUserImage : deleteUserImage );

                            imageView.setFitHeight( 20 );
                            imageView.setFitWidth( 20 );

                            setGraphic( imageView );
                        }
                    }
                };
            }
        } ),
        new DefaultTable.ItemColumn( "Nome", "name" ),
        new DefaultTable.ItemColumn( "Tipo", "postingType", new Callback< TableColumn<PostingCategory, Integer>, TableCell<PostingCategory, Integer> >() 
        {
            @Override
            public TableCell<PostingCategory, Integer> call( TableColumn<PostingCategory, Integer> p ) 
            {
                return new TableCell<PostingCategory, Integer>() 
                {
                    @Override
                    protected void updateItem( Integer item, boolean empty )
                    {
                        try
                        {
                            super.updateItem( item, empty);

                            if ( empty || item == null )
                            {
                                setText(null);
                                setTextFill(null);
                                setGraphic( null );
                            }
                            
                            else if( ! empty )
                            {
                                setText( String.valueOf( com.pa.helpfin.model.ModuleContext
                                                                            .getInstance()
                                                                            .getPostingTypeManager()
                                                                            .getValue( item ) ) );
                            }
                        }
                        catch ( Exception e )
                        {
                            ApplicationUtilities.logException( e );
                        }
                    }
                };
            }
        } ) );
    }
}
