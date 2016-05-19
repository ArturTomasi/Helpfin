package com.pa.helpfin.view.editor;

import com.pa.helpfin.model.data.Entity;
import com.pa.helpfin.view.selectors.StateSelector;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.util.LabelField;
import com.pa.helpfin.view.util.MaskTextField;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 * @author artur
 */
public class EntityEditor 
    extends 
        AbstractEditor<Entity>
{
    public EntityEditor( EditorCallback<Entity> callback ) 
    {
        super( callback );
        
        setTitle( "Editor de Entidades" );
        setHeaderText( "Editor de Entidades" );
        
        initComponents();
        
        setSource( source );
    }
    
    

    @Override
    protected void validadeInput( List<String> erros ) throws Exception 
    {
        if( ! nameField.isValid() )
        {
            erros.add( "Nome" );
        }
        
        if( stateSelector.getSelectedIndex() == -1 )
            erros.add( "Situação" );
        
        erros.addAll( com.pa.helpfin.model.ModuleContext.getInstance().getEntityManager().isUnique( source ) );
    }
    
    

    @Override
    protected void obtainInput()
    {
        source.setName( nameField.getValue() );
        source.setCompannyName( compannyNameField.getValue() );
        source.setMail( mailField.getValue() );
        source.setState( stateSelector.getSelectedIndex() );
        source.setCnpj( cnpjField.getValue() );
        source.setPhone( phoneField.getValue() );
    }
    
    

    @Override
    protected void resize() 
    {
        nameField.setPrefWidth( getWidth() - lbName.getWidth() - 20 );
        gridPane.setPrefWidth( getWidth() - 20 );
        getDialogPane().requestLayout();
    }
    
    

    @Override
    protected void setSource ( Entity source )
    {
        nameField.setText( source.getName() );
        compannyNameField.setText( source.getCompannyName() );
        cnpjField.setText( source.getCnpj() );
        phoneField.setText( source.getPhone() );
        mailField.setText( source.getMail() );
        stateSelector.setSelectedIndex( source.getState() );
    }
    
    
    
    private void initComponents()
    {
        gridPane.setVgap( 20 );
        gridPane.setHgap( 20 );
        gridPane.setStyle( "-fx-padding: 30;" );
        
        gridPane.add( lbName,            0, 0, 1, 1 );
        gridPane.add( nameField,         1, 0, 3, 1 );
        
        gridPane.add( lbComponnyName,    0, 1, 1, 1 );
        gridPane.add( compannyNameField, 1, 1, 3, 1 );
        
        gridPane.add( lbCnpj,            0, 2, 1, 1 );
        gridPane.add( cnpjField,         1, 2, 1, 1 );
        gridPane.add( lbPhone,           2, 2, 1, 1 );
        gridPane.add( phoneField,        3, 2, 1, 1 );
       
        gridPane.add( lbMail,            0, 3, 1, 1 );
        gridPane.add( mailField,         1, 3, 3, 1 );
        
        gridPane.add( lbState,           0, 4, 1, 1 );
        gridPane.add( stateSelector,     1, 4, 3, 1 );
        
        getDialogPane().setContent( gridPane );
        
        getDialogPane().setPrefWidth( 700 );
    }
    
    private GridPane gridPane = new GridPane();
    
    private LabelField lbName               = new LabelField( "Nome", true ); 
    private MaskTextField nameField         = new MaskTextField();
    
    private LabelField lbComponnyName       = new LabelField( "Razão Social" );
    private MaskTextField compannyNameField = new MaskTextField();
    
    private LabelField lbCnpj               = new LabelField( "CNPJ" );
    private MaskTextField cnpjField         = new MaskTextField( MaskTextField.MASK_CNPJ );
    
    private LabelField lbPhone              = new LabelField( "Telefone" );
    private MaskTextField phoneField        = new MaskTextField( MaskTextField.MASK_PHONE );
    
    private LabelField lbMail               = new LabelField( "Email" );
    private MaskTextField mailField         = new MaskTextField();
    
    private LabelField lbState              = new LabelField( "Situação", true );
    private StateSelector stateSelector     = new StateSelector();
}
