package com.pa.helpfin.view.editor;

import com.pa.helpfin.model.data.Encryption;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.view.util.DateField;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.selectors.RoleSelector;
import com.pa.helpfin.view.selectors.StateSelector;
import com.pa.helpfin.view.util.LabelField;
import com.pa.helpfin.view.util.MaskTextField;
import java.util.List;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * @author artur
 */
public class UserEditor 
    extends 
        AbstractEditor<User>
{

    public UserEditor( EditorCallback<User> callback )
    {
        super( callback );
        
        initComponents();
        
        setTitle( "Editor de Usuário" );
        setHeaderText( "Editor de Usuário" );
        
        setSource( source );
    }

    
    
    @Override
    protected void validadeInput( List<String> erros ) throws Exception
    {
        if( ! nameField.isValid() )
            erros.add( "Nome" );
        
        if( ! mailField.isValid() )
            erros.add( "Email" );
        
        if( ! phoneField.isValid() )
            erros.add( "Telefone" );
        
        if( ! cpfField.isValid() )
            erros.add( "CPF" );
        
        if( ! rgField.isValid() )
            erros.add( "RG" );
        
        if( ! loginField.isValid() )
            erros.add( "Login" );
        
        if( passwordField.getText() == null || passwordField.getText().isEmpty() )
            erros.add( "Senha" );
        
        if( birthDateFiled.getDate() == null )
            erros.add( "Data de Nascimento" );
        
        if( ! femaleGenderFied.isSelected() && ! maleGenderFied.isSelected() )
            erros.add( "Sexo" );
        
        if( roleField.getSelected() == null )
            erros.add( "Categoria" );
        
        if( stateField.getSelected() == null )
            erros.add( "Situação" );
        
        erros.addAll( com.pa.helpfin.model.ModuleContext.getInstance()
                                                    .getUserManager()
                                                    .isUnique( source ) );
        
    }

    
    
    @Override
    protected void obtainInput() 
    {
        source.setName( nameField.getText() );
        source.setMail( mailField.getText() );
        source.setPhone( phoneField.getValue() );
        source.setCpf( cpfField.getValue() );
        source.setRg( rgField.getText() );
        source.setLogin( loginField.getText() );
        source.setBirthDate( birthDateFiled.getDate() );
        source.setGender( femaleGenderFied.isSelected() ? User.FEMALE :
                          maleGenderFied.isSelected()   ? User.MALE   : - 1 );
        source.setRole( roleField.getSelectedIndex() );
        source.setState( stateField.getSelectedIndex() );
        
        if( source.getId() != 0  )
        {
            if( ! passwordField.getText().equals( source.getPassword() ) )
                source.setPassword( Encryption.hash( passwordField.getText() ) );
            
        }
        
        else
        {
            source.setPassword( Encryption.hash( passwordField.getText() ) );
        }
    }
    
    

    @Override
    protected void resize()
    {
        stateField.setPrefWidth( getWidth() );
        getDialogPane().requestLayout();
    }

    
    
    @Override
    protected void setSource( User source ) 
    {
        nameField.setText( source.getName() );
        cpfField.setText( source.getCpf() );
        rgField.setText( source.getRg() );
        passwordField.setText( source.getPassword() );
        loginField.setText( source.getLogin() );
        birthDateFiled.setDate( source.getBirthDate() );
        mailField.setText( source.getMail() );
        phoneField.setText( source.getPhone() );
        roleField.setSelectedIndex( source.getRole() );
        stateField.setSelectedIndex( source.getState() );
        
        if ( source.getGender() == User.FEMALE )
            femaleGenderFied.setSelected( source.getGender() == User.FEMALE );
        else
            maleGenderFied.setSelected( source.getGender() == User.MALE );
    }
    
    
    
    private void initComponents()
    {
        femaleGenderFied.setToggleGroup( groupGender );
        maleGenderFied.setToggleGroup( groupGender );
        
        gridPane.setVgap( 20 );
        gridPane.setHgap( 20 );
        gridPane.setStyle( "-fx-padding: 30;" );
        
        gridPane.add( lbName,           0, 0, 1, 1 );
        gridPane.add( nameField,        1, 0, 3, 1 );
        
        gridPane.add( lbMail,           0, 1, 1, 1 );
        gridPane.add( mailField,        1, 1, 1, 1 );
        gridPane.add( lbPhone,          2, 1, 1, 1 );
        gridPane.add( phoneField,       3, 1, 1, 1 );
        
        gridPane.add( lbCpf,            0, 2, 1, 1 );
        gridPane.add( cpfField,         1, 2, 1, 1 );
        gridPane.add( lbrg,             2, 2, 1, 1 );
        gridPane.add( rgField,          3, 2, 1, 1 );
        
        gridPane.add( lbLogin,          0, 3, 1, 1 );
        gridPane.add( loginField,       1, 3, 1, 1 );
        gridPane.add( lbPassword,       2, 3, 1, 1 );
        gridPane.add( passwordField,    3, 3, 1, 1 );
        
        HBox hBoxGender = new HBox( maleGenderFied, femaleGenderFied );
        hBoxGender.setSpacing( 5 );
        
        gridPane.add( lbBirthDate,      0, 4, 1, 1 );
        gridPane.add( birthDateFiled,   1, 4, 1, 1 );
        gridPane.add( lbGender,         2, 4, 1, 1 );
        gridPane.add( hBoxGender,       3, 4, 1, 1 );
        
        gridPane.add( lbRole,           0, 5, 1, 1 );
        gridPane.add( roleField,        1, 5, 3, 1 );
        
        gridPane.add( lbState,          0, 6, 1, 1 );
        gridPane.add( stateField,       1, 6, 3, 1 );
        
        getDialogPane().setContent( gridPane );
    }
    
    private GridPane gridPane            = new GridPane();
    
    private LabelField lbName            = new LabelField( "Nome", true );
    private MaskTextField nameField      = new MaskTextField();

    private LabelField lbCpf             = new LabelField( "CPF", true );
    private MaskTextField cpfField       = new MaskTextField( MaskTextField.MASK_CPF );
    
    private LabelField lbrg              = new LabelField( "RG", true );
    private MaskTextField rgField        = new MaskTextField( MaskTextField.MASK_RG );
    
    private LabelField lbMail            = new LabelField( "Email", true );
    private MaskTextField mailField      = new MaskTextField();
    
    private LabelField lbPhone           = new LabelField( "Telefone" );
    private MaskTextField phoneField     = new MaskTextField( MaskTextField.MASK_PHONE );
    
    private LabelField lbLogin           = new LabelField( "Login", true );
    private MaskTextField loginField     = new MaskTextField();
    
    private LabelField lbBirthDate       = new LabelField( "Ano Nascimento", true );
    private DateField birthDateFiled     = new DateField();
    
    private LabelField lbGender          = new LabelField( "Sexo", true );
    private RadioButton femaleGenderFied = new RadioButton( "Feminino" );
    private RadioButton maleGenderFied   = new RadioButton( "Masculino" );
    private ToggleGroup groupGender      = new ToggleGroup();
    
    private LabelField lbPassword        = new LabelField( "Senha",true );
    private PasswordField passwordField  = new PasswordField();
    
    private LabelField lbRole            = new LabelField( "Categoria", true );
    private RoleSelector roleField       = new RoleSelector();
    
    private LabelField lbState           = new LabelField( "Situação", true );
    private StateSelector stateField     = new StateSelector();
}
