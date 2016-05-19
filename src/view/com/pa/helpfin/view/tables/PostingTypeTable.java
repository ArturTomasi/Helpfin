package com.pa.helpfin.view.tables;

import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.PostingType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * @author artur
 */
public class PostingTypeTable 
    extends 
        DefaultTable<PostingType>
{
    private Image activeUserImage = new Image( ResourceLocator.getInstance().getImageResource( "finish.png" ) ); 
    private Image deleteUserImage = new Image( ResourceLocator.getInstance().getImageResource( "delete.png" ) );
    
    public PostingTypeTable() 
    {
        setColumns( new DefaultTable.ItemColumn( "#", "state", new Callback< TableColumn<PostingType, Integer>, TableCell<PostingType, Integer> >() 
        {
            @Override
            public TableCell<PostingType, Integer> call( TableColumn<PostingType, Integer> p ) 
            {
                return new TableCell<PostingType, Integer>() 
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
                            ImageView imageView =  new ImageView( item == PostingType.STATE_ACTIVE ? activeUserImage : deleteUserImage );

                            imageView.setFitHeight( 20 );
                            imageView.setFitWidth( 20 );

                            setGraphic( imageView );
                        }
                    }
                };
            }
        } ),
        new DefaultTable.ItemColumn( "Nome", "name" ) );
    }
}
