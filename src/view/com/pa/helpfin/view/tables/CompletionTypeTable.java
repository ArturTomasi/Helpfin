package com.pa.helpfin.view.tables;

import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.CompletionType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * @author artur
 */
public class CompletionTypeTable 
    extends 
        DefaultTable<CompletionType>
{
    private Image activeUserImage = new Image( ResourceLocator.getInstance().getImageResource( "finish.png" ) ); 
    private Image deleteUserImage = new Image( ResourceLocator.getInstance().getImageResource( "delete.png" ) ); 
    
    public CompletionTypeTable() 
    {
        setColumns( new DefaultTable.ItemColumn( "#", "state", new Callback< TableColumn<CompletionType, Integer>, TableCell<CompletionType, Integer> >() 
        {
            @Override
            public TableCell<CompletionType, Integer> call( TableColumn<CompletionType, Integer> p ) 
            {
                return new TableCell<CompletionType, Integer>() 
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
                            ImageView imageView =  new ImageView( item == CompletionType.STATE_ACTIVE ? activeUserImage : deleteUserImage );

                            imageView.setFitHeight( 20 );
                            imageView.setFitWidth( 20 );

                            setGraphic( imageView );
                        }
                    }
                };
            }
        } ),
        new DefaultTable.ItemColumn( "Nome", "name" ),
        new DefaultTable.ItemColumn( "Tipo", "type", new Callback< TableColumn<CompletionType, Integer>, TableCell<CompletionType, Integer> >() 
        {
            @Override
            public TableCell<CompletionType, Integer> call( TableColumn<CompletionType, Integer> p ) 
            {
                return new TableCell<CompletionType, Integer>() 
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
                            setText( CompletionType.TYPES[ item ] );
                        }
                    }
                };
            }
        } ) );
    }
}
