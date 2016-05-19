package com.pa.helpfin.view.editor.postingEditor;

import com.pa.helpfin.control.PostingController;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.User;
import static com.pa.helpfin.view.editor.PostingEditor.MODE_NEW;
import static com.pa.helpfin.view.editor.PostingEditor.MODE_FINISH;
import com.pa.helpfin.view.selectors.CompletionTypeSelector;
import com.pa.helpfin.view.selectors.EntitySelector;
import com.pa.helpfin.view.util.DateField;
import com.pa.helpfin.view.selectors.PostingCategorySelector;
import com.pa.helpfin.view.selectors.UserSelector;
import com.pa.helpfin.view.util.LabelField;
import com.pa.helpfin.view.util.NumberTextField;
import com.pa.helpfin.view.util.Prompts;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * @author artur
 */
public class GeneralPane 
    extends 
        Tab
{
    public static class Events
    {
        public static final EventType ON_CHANGE_VALUES = new EventType( "onChangeValues" );
    }
        
    private Posting source;
    private int mode;
    
    public GeneralPane() 
    {
        setText( "Geral" );
        setClosable( false );
        initComponents();
    }
    
    
    
    public void setSource( Posting posting )
    {
        try
        {
            source = posting;

            ckRepeat.setSelected( posting.isRepeat() );
            ckCompletionAuto.setSelected( posting.isCompletionAuto() );
            nameField.setText( posting.getName() );
            estimateDateField.setDate( posting.getEstimateDate() );
            realDateFiled.setDate( posting.getRealDate() );
            estimateValueField.setDouble( posting.getEstimateValue() );
            realValueField.setDouble( posting.getRealValue() );
            portionTotalFiled.increment( posting.getPortionTotal() != 0 ? posting.getPortionTotal() - 2 : 0 );
            
            completionTypeCombo.setSelected( com.pa.helpfin.model.ModuleContext
                                                                    .getInstance()
                                                                    .getCompletionTypeManager()
                                                                    .getValue( posting.getCompletionType() ) );
            
            entityCombo.setSelected( com.pa.helpfin.model.ModuleContext
                                                                    .getInstance()
                                                                    .getEntityManager()
                                                                    .getValue( posting.getEntity() ) );
            
            postingCategoryCombo.setSelected( com.pa.helpfin.model.ModuleContext
                                                                    .getInstance()
                                                                    .getPostingCategoryManager()
                                                                     .getValue( posting.getPostingCategory() ) );
           
            userCombo.setSelected( posting.getUser() != 0 ? com.pa.helpfin.model.ModuleContext
                                                                    .getInstance()
                                                                    .getUserManager()
                                                                    .getValue( posting.getUser() ) : ApplicationUtilities.getInstance().getActiveUser() );
            
            if( mode == MODE_FINISH )
            {
                realDateFiled.setDate( posting.getEstimateDate() );
                realValueField.setDouble( posting.getEstimateValue() );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    public void resize( double width, double height )
    {
        nameField.setPrefWidth( width - lbName.getWidth() - 10 ); 
        grid.requestLayout();
    }
    
    
    
    public void obtainInput( Posting source )
    {
        source.setRepeat( ckRepeat.isSelected() );
        source.setPortionTotal( ckRepeat.isSelected() ? portionTotalFiled.getValue() : 1 );
        source.setName( nameField.getText() );
        source.setEstimateDate( estimateDateField.getDate() );
        source.setRealDate( realDateFiled.getDate() );
        source.setRealValue( realValueField.getDouble() );
        source.setEstimateValue( estimateValueField.getDouble() );
        source.setEntity( entityCombo.getSelected() != null ? entityCombo.getSelected().getId() : 0 );
        source.setUser( userCombo.getSelected() != null ? userCombo.getSelected().getId() : 0 );
        source.setPostingCategory( postingCategoryCombo.getSelected() != null ? postingCategoryCombo.getSelected().getId() : 0 );
        source.setCompletionAuto( ckCompletionAuto.isSelected() );
        source.setCompletionType( completionTypeCombo.getSelected() != null ? completionTypeCombo.getSelected().getId() : 0 );
        
        source.setState( PostingController.getInstance().makeState( source ) );
    }
    
    
    
    public void validateInput( List<String> erros )
    {
        boolean notFinish = mode != MODE_FINISH;
        
        if( notFinish && estimateDateField.getDate() == null )
            erros.add( "A data estimada não pode estar vazia !" );
      
        if( notFinish && ! estimateValueField.isValid() )
            erros.add( "O valor estimado está inválido !" );
        
        if( nameField.getText() == null || nameField.getText().isEmpty() )
            erros.add( "O Nome não pode estar vazio!" );
        
        if( ( ckCompletionAuto.isSelected() || ! notFinish ) && completionTypeCombo.getSelected() == null)
            erros.add( "O tipo de finalização não pode estar vazio" );
        
        if( realDateFiled.getDate() != null && completionTypeCombo.getSelected() == null )
            erros.add( "O tipo de finalização não pode estar vazio" );
        
        if( notFinish && entityCombo.getSelected() == null )
            erros.add( "A empresa do lançamento não pode estar vazio" );
        
        if( notFinish && userCombo.getSelected() == null )
            erros.add( "A responsável do lançamento não de estar vazio" );
        
        if( notFinish && postingCategoryCombo.getSelected() == null )
            erros.add( "A categoria do lançamento não pode estar vazio" );
        
        if( ! notFinish && ! realValueField.isValid()  )
            erros.add( "O valor real não pode estar vazia !" );
        
        if( ! notFinish &&  realDateFiled.getDate() == null )
            erros.add( "A data real não pode estar vazia !" );
        
        if( notFinish && realDateFiled.getDate() != null && ! realValueField.isValid() )
            erros.add( "A valor real não pode ser 0!" );

        if( notFinish && realValueField.isValid() &&  realDateFiled.getDate() == null )
            erros.add( "A data real não pode estar vazia !" );
    }
    
    
    
    public void loadField( int mode )
    {
        this.mode = mode;
        
        ckRepeat.setDisable( mode != MODE_NEW );
        portionTotalFiled.setDisable( ( source == null || ! source.isRepeat() ) && mode == MODE_NEW );
        
        completionTypeCombo.setVisible( mode == MODE_FINISH );
        lbCompletion.setVisible( mode == MODE_FINISH );
        
        userCombo.setDisable( ApplicationUtilities.getInstance().getActiveUser().getRole() == User.ROLE_OPERATOR );
        
        if( mode == MODE_FINISH )
        {
            grid.getChildren().clear();
            
            nameField.setEditable( false );
            
            grid.add( lbName,               0, 0, 1, 1 );
            grid.add( nameField,            1, 0, 3, 1 );
          
            grid.add( lbRealDate,           0, 1, 1, 1 );
            grid.add( realDateFiled,        1, 1, 1, 1 );
            grid.add( lbRealValue,          2, 1, 1, 1 );
            grid.add( realValueField,       3, 1, 1, 1 );
            
            grid.add( completionTypeCombo,  0, 2, 4, 1 );
            
            setSource( source ); //refresh postings
        }
        
        else if ( mode != MODE_FINISH && mode != MODE_NEW )
        {
            ckRepeat.setDisable( true );
            lbPortion.setText( "Parcela" );
            portionTotalFiled.setVisible( false ); 
            
            grid.add( new Label( source.getPortion() + " de " + source.getPortionTotal() ),    3, 6, 1, 1 );
        }
    }
    
    
    
    private void fireEvent()
    {
        obtainInput( source );
        
        if( source.getEstimateDate() != null )
        {
            grid.getProperties().put( "posting", source );

            getContent().fireEvent( new Event( Events.ON_CHANGE_VALUES ) );
        }
        
        else
        {
            Prompts.info( "Para selecionar está opção é necessário preencher uma data estimada" );
            ckRepeat.setSelected( false );
        }
    }
    
    
    private void showCompletionType()
    {
        boolean visible =ckCompletionAuto.isSelected() || realDateFiled.getDate() != null || realValueField.isValid();
        
        completionTypeCombo.setVisible( visible );
        lbCompletion.setVisible( visible );
    }
    
    
    private void initComponents()
    {
        grid.setVgap( 20 );
        grid.setHgap( 20 );
        grid.setStyle( "-fx-padding: 30;" );

        // page.add(Node, colIndex, rowIndex, colSpan, rowSpan):   
        grid.autosize();
        grid.add( lbName,               0, 0, 1, 1 );
        grid.add( nameField,            1, 0, 3, 1 );
        
        grid.add( lbEstimateDate,       0, 1, 1, 1 );
        grid.add( estimateDateField,    1, 1, 1, 1 );
        grid.add( lbRealDate,           2, 1, 1, 1 );
        grid.add( realDateFiled,        3, 1, 1, 1 ); 
        
        grid.add( lbEstimateValue,      0, 2, 1, 1 ); 
        grid.add( estimateValueField,   1, 2, 1, 1 ); 
        grid.add( lbRealValue,          2, 2, 1, 1 ); 
        grid.add( realValueField,       3, 2, 1, 1 ); 
        
        grid.add( lbEntity,             0, 3, 1, 1 );
        grid.add( entityCombo,          1, 3, 3, 1 );
        
        grid.add( lbCategory,           0, 4, 1, 1 );
        grid.add( postingCategoryCombo, 1, 4, 3, 1 );
        
        grid.add( lbUser,               0, 5, 1, 1 );
        grid.add( userCombo,            1, 5, 3, 1 );
        
        grid.add( ckRepeat,             0, 6, 1, 1 );
        grid.add( ckCompletionAuto,     1, 6, 1, 1 );
        grid.add( lbPortion,            2, 6, 1, 1 );
        grid.add( portionTotalFiled,    3, 6, 1, 1 );
        
        grid.add( lbCompletion,         0, 7, 1, 1 );
        grid.add( completionTypeCombo,  1, 7, 3, 1 );
        
        ckRepeat.setAlignment( Pos.CENTER_RIGHT );
        
        setContent( grid );
        
        ckRepeat.setOnAction( new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle( ActionEvent t )
            {
                portionTotalFiled.setDisable( ! ckRepeat.isSelected() );
                fireEvent();
            }
        } );
        
        ckCompletionAuto.setOnAction( new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle( ActionEvent t )
            {
                showCompletionType();
            }
        } );
        
        realDateFiled.focusedProperty().addListener( new ChangeListener<Boolean>() 
        {
            @Override
            public void changed( ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue )
            {
                showCompletionType();
            }
        } );
       
        realValueField.focusedProperty().addListener( new ChangeListener<Boolean>() 
        {
            @Override
            public void changed( ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue )
            {
                showCompletionType();
            }
        } );
   }
    
    private GridPane grid                                = new GridPane();
    
    private TextField nameField                          = new TextField();
    private LabelField lbName                            = new LabelField( "Nome", true );
    
    private LabelField  lbRealDate                       = new LabelField( "Data Real" );
    private DateField realDateFiled                      = new DateField();
    
    private LabelField  lbEstimateDate                   = new LabelField( "Data Estimada", true );
    private DateField estimateDateField                  = new DateField();
    
    private LabelField lbRealValue                       = new LabelField( "Valor Real" );
    private NumberTextField realValueField               = new NumberTextField();
    
    private LabelField lbEstimateValue                   = new LabelField( "Valor Estimado", true );
    private NumberTextField estimateValueField           = new NumberTextField();
    
    private CheckBox ckCompletionAuto                    = new CheckBox( "Finaliza Automaticamente" );
    private CheckBox ckRepeat                            = new CheckBox( "Repete" ); 
    
    private LabelField lbPortion                         = new LabelField( "Parcelas" );
    private Spinner<Integer> portionTotalFiled           = new Spinner( 2, 12, 2 );
    
    private LabelField lbCategory                        = new LabelField( "Categoria", true );
    private PostingCategorySelector postingCategoryCombo = new PostingCategorySelector();

    private LabelField lbCompletion                      = new LabelField( "Tipo de Finalização", true );
    private CompletionTypeSelector completionTypeCombo   = new CompletionTypeSelector();
    
    private LabelField lbEntity                          = new LabelField( "Entidade", true );
    private EntitySelector entityCombo                   = new EntitySelector();
    
    private LabelField lbUser                            = new LabelField( "Responsável", true );
    private UserSelector userCombo                       = new UserSelector();
}
