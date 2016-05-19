package com.pa.helpfin.view.tables;

import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.User;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * @author artur
 */
public class UserTable
    extends 
        DefaultTable<User>
{
    private Image activeUserImage = new Image( ResourceLocator.getInstance().getImageResource( "finish.png" ) ); 
    private Image deleteUserImage = new Image( ResourceLocator.getInstance().getImageResource( "delete.png" ) ); 
    
    public UserTable() 
    {
        setColumns( new DefaultTable.ItemColumn( "#", "state", new Callback< TableColumn<User, Integer>, TableCell<User, Integer> >() 
        {
            @Override
            public TableCell<User, Integer> call( TableColumn<User, Integer> p ) 
            {
                return new TableCell<User, Integer>() 
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
                            ImageView imageView =  new ImageView( item == User.STATE_ACTIVE ? activeUserImage : deleteUserImage );

                            imageView.setFitHeight( 20 );
                            imageView.setFitWidth( 20 );

                            setGraphic( imageView );
                        }
                    }
                };
            }
        } ),
        new DefaultTable.ItemColumn( "Nome", "name" ),
        new DefaultTable.ItemColumn( "Email", "mail" ),
        new DefaultTable.ItemColumn( "Telefone", "phone", new Callback< TableColumn<User, String>, TableCell<User, String> >() 
        {
            @Override
            public TableCell<User, String> call( TableColumn<User, String> p ) 
            {
                return new TableCell<User, String>() 
                {
                    @Override
                    protected void updateItem( String item, boolean empty )
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
                            setText( item.replaceFirst( "([0-9]{2})([0-9]{4})([0-9]{4,5})$", "($1)$2-$3" ) );
                        }
                    }
                };
            }
        } ),
        new DefaultTable.ItemColumn( "CPF", "cpf",  new Callback< TableColumn<User, String>, TableCell<User, String> >() 
        {
            @Override
            public TableCell<User, String> call( TableColumn<User, String> p ) 
            {
                return new TableCell<User, String>() 
                {
                    @Override
                    protected void updateItem( String item, boolean empty )
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
                            setText( item.replaceAll( "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4" ) );
                        }
                    }
                };
            }
        } ),
        new DefaultTable.ItemColumn( "RG", "rg" ),
        new DefaultTable.ItemColumn( "Sexo", "gender", new Callback< TableColumn<User, Integer>, TableCell<User, Integer> >() 
        {
            @Override
            public TableCell<User, Integer> call( TableColumn<User, Integer> p ) 
            {
                return new TableCell<User, Integer>() 
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
                            setText( User.GENDER[ item ] );
                        }
                    }
                };
            }
        } ),
        new DefaultTable.ItemColumn( "Categoria", "role", new Callback< TableColumn<User, Integer>, TableCell<User, Integer> >() 
        {
            @Override
            public TableCell<User, Integer> call( TableColumn<User, Integer> p ) 
            {
                return new TableCell<User, Integer>() 
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
                            setText( User.ROLE[ item ] );
                        }
                    }
                };
            }
        } ) );
    }
}
