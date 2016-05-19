package com.pa.helpfin.view.tables;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.Entity;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.model.data.UserCache;
import com.pa.helpfin.view.inspectors.InspectView;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * @author artur
 */
public class PostingTable
    extends 
        DefaultTable<Posting>
{
    private DateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
    
    private Image newPosting       = new Image( ResourceLocator.getInstance().getImageResource( "new.png" )  ); 
    private Image playPosting      = new Image( ResourceLocator.getInstance().getImageResource( "play.png" ) ); 
    private Image finishedPosting  = new Image( ResourceLocator.getInstance().getImageResource( "finish.png" ) ); 
    private Image deletePosting    = new Image( ResourceLocator.getInstance().getImageResource( "delete.png" ) ); 
    
    public PostingTable() 
    {
        setColumns
        (
            new DefaultTable.ItemColumn( "#", "state", new Callback< TableColumn<Posting, Integer>, TableCell<Posting, Integer> >()
            {
                @Override
                public TableCell<Posting, Integer> call( TableColumn<Posting, Integer> param )
                {
                    return new TableCell<Posting, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty);

                            if( ! empty )
                            {
                                ImageView imageView =  new ImageView ();

                                imageView.setImage( item == Posting.STATE_REGISTRED ? newPosting :
                                                    item == Posting.STATE_PROGRESS  ? playPosting :
                                                    item == Posting.STATE_FINISHED  ? finishedPosting : deletePosting );

                                imageView.setFitHeight( 20 );
                                imageView.setFitWidth( 20 );

                                setGraphic( imageView );
                            }
                            else
                            {
                                setGraphic( null );
                            }
                        }
                    };
                }
            } ), 
            new DefaultTable.ItemColumn( "Nome", "name", 250d ),
            new DefaultTable.ItemColumn( "Responsável", "user", 250d, new Callback< TableColumn<Posting, Integer>, TableCell<Posting, Integer> >()
            {
                @Override
                public TableCell<Posting, Integer> call( TableColumn<Posting, Integer> param )
                {
                    return new TableCell<Posting, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty);

                            if( ! empty )
                            {
                                try
                                {
                                    User user =  UserCache.getUser( item );

                                    if( user != null )
                                    {
                                        setText( user.getName() );
                                    }
                                }

                                catch ( Exception e )
                                {
                                    ApplicationUtilities.logException( e );
                                }
                            }
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.ItemColumn( "Categoria", "postingCategory", 250d, new Callback< TableColumn<Posting, Integer>, TableCell<Posting, Integer> >()
            {
               @Override
               public TableCell<Posting, Integer> call( TableColumn<Posting, Integer> param )
               {
                    return new TableCell<Posting, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty);

                            if( ! empty )
                            {
                                try
                                {
                                    PostingCategory category = com.pa.helpfin.model.ModuleContext
                                                                        .getInstance()
                                                                        .getPostingCategoryManager()
                                                                        .getValue( item );

                                    setText( category != null ? category.getName() : "n/d" );
                                }

                                catch ( Exception e )
                                {
                                    ApplicationUtilities.logException( e );
                                }
                            }
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.ItemColumn( "Entidade", "entity", 250d, new Callback< TableColumn<Posting, Integer>, TableCell<Posting, Integer> >()
            {
               @Override
               public TableCell<Posting, Integer> call( TableColumn<Posting, Integer> param )
               {
                    return new TableCell<Posting, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty);

                            if( ! empty )
                            {
                                try
                                {
                                    Entity entity = com.pa.helpfin.model.ModuleContext
                                                                        .getInstance()
                                                                        .getEntityManager()
                                                                        .getValue( item );

                                    setText( entity != null ? entity.getName() : "n/d" );
                                }

                                catch ( Exception e )
                                {
                                    ApplicationUtilities.logException( e );
                                }
                            }
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.ItemColumn( "Data Estimada", "estimateDate", 250d, new Callback< TableColumn<Posting, Date>, TableCell<Posting, Date> >()
            {
               @Override
               public TableCell<Posting, Date> call( TableColumn<Posting, Date> param )
               {
                    return new TableCell<Posting, Date>() 
                    {
                        @Override
                        protected void updateItem( Date item, boolean empty )
                        {
                            super.updateItem( item, empty);

                            if( ! empty )
                            {
                                setText( item != null ? df.format( item ) : "n/d"  );
                            }
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ), 
            new DefaultTable.ItemColumn( "Data Real", "realDate", 250d, new Callback< TableColumn<Posting, Date>, TableCell<Posting, Date> >()
            {
               @Override
               public TableCell<Posting, Date> call( TableColumn<Posting, Date> param )
               {
                    return new TableCell<Posting, Date>() 
                    {
                        @Override
                        protected void updateItem( Date item, boolean empty )
                        {
                            super.updateItem( item, empty);

                            if( ! empty )
                            {
                                setText( item != null ? df.format( item ) : "n/d" );
                            }
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.ItemColumn( "Valor Estimado", "estimateValue", 250d, new Callback< TableColumn<Posting, Double>, TableCell<Posting, Double> >()
            {
               @Override
               public TableCell<Posting, Double> call( TableColumn<Posting, Double> param )
               {
                    return new TableCell<Posting, Double>() 
                    {
                        @Override
                        protected void updateItem( Double item, boolean empty )
                        {
                            super.updateItem( item, empty);

                            setText( ! empty ? "R$ " + item : null );
                        }
                    };
                }
            } ), 
            new DefaultTable.ItemColumn( "Valor Real", "realValue", 250d, new Callback< TableColumn<Posting, Double>, TableCell<Posting, Double> >()
            {
               @Override
               public TableCell<Posting, Double> call( TableColumn<Posting, Double> param )
               {
                    return new TableCell<Posting, Double>() 
                    {
                        @Override
                        protected void updateItem( Double item, boolean empty )
                        {
                            super.updateItem( item, empty);

                            setText( ! empty ? "R$ " + item : null );
                        }
                    };
                }
            } ) );
        
        setOnClick( new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle( MouseEvent t )
            {
                if( t.getButton().equals( MouseButton.PRIMARY ) && 
                        t.getClickCount() == 2  && getSelectedItem() != null )
                {
                    new InspectView( getSelectedItem() ).show();
                }
            }
        } );
    }
}
