package com.pa.helpfin.view.util;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * @author artur
 */
public class MaskTextField
    extends 
        TextField
{
    public static final int MASK_EMPTY = -1;
    public static final int MASK_CPF   = 0;
    public static final int MASK_CNPJ  = 1;
    public static final int MASK_PHONE = 2;
    public static final int MASK_RG    = 3;
    
    private int type = MASK_EMPTY;

    public MaskTextField() 
    {
        this( MASK_EMPTY );
    }
    
    public MaskTextField( int mask ) 
    {
        type = mask;
        
        maxField( type == MASK_CPF   ? 14 :
                  type == MASK_PHONE ? 14 :
                  type == MASK_RG    ? 10 :
                  type == MASK_CNPJ  ? 18 : 200 );
        
        lengthProperty().addListener( new ChangeListener<Number>() 
        {
            @Override
            public void changed( ObservableValue<? extends Number> ov, Number oldLenght, Number newLenght )
            {
                String value = getText();
            
                if ( type == MASK_CPF )
                {
                    value = value.replaceAll( "[^0-9]", "" );
                    
                    value = value.replaceAll( "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4" );
                }

                else if ( type == MASK_CNPJ ) 
                {
                    value = value.replaceAll( "[^0-9]", "" );
                    value = value.replaceFirst( "([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})$", "$1.$2.$3/$4-$5" );
                }

                else if ( type == MASK_PHONE ) 
                {
                    value = value.replaceAll( "[^0-9]", "" );
                    value = value.replaceFirst( "([0-9]{2})([0-9]{4})([0-9]{4,5})$", "($1)$2-$3" );
                }
                
                setText( value );
                
                positionCaret( newLenght.intValue() );
            }
        } );
        
    }
    
    
    
    private void maxField( final Integer length )
    {
        textProperty().addListener( new ChangeListener<String>() 
        {
            @Override
            public void changed( ObservableValue<? extends String> observableValue, String oldValue, String newValue )
            {
                if ( newValue == null || newValue.length() > length ) 
                    setText( oldValue );
            }
        } );
    }
    
    
    
    public String getValue()
    {
        String value = super.getText();
        
        if( value == null || value.isEmpty() )
            return null;
        
        if( type == MASK_CPF )
        {
            return value.replaceAll( "[.-]" , "" );
        }
        
        else if( type == MASK_PHONE )
        {
            return value.replaceAll( "[()-]" , "" );
        }
        
        else if( type == MASK_CNPJ )
        {
            return value.replaceAll( "[./-]" , "" );
        }
        
        return value;
    }
    
    
    public boolean isValid()
    {
        String value = super.getText();
     
        return  value != null && ! value.isEmpty() && 
                ( type == MASK_CPF ? ( value.length() == 14 && isValidCPF( getValue() ) ) :
                type == MASK_PHONE ? value.length() >= 13 :
                type == MASK_RG    ? value.length() == 10 :
                type == MASK_CNPJ  ? value.length() == 18 : value.length() <= 200 );
        
    }

    
    
    public boolean isValidCPF( String cpf )
    {
        if ( ( cpf == null ) || ( cpf.length() != 11 ) )
        {
            return false;
        }

        Integer firstDigit = calcDigit( cpf.substring( 0, 9 ) );
        Integer lastDigit  = calcDigit( cpf.substring( 0, 9 ) + firstDigit );
        
        return cpf.equals( cpf.substring( 0, 9 ) + firstDigit.toString() + lastDigit.toString() );
    }
    
    
    
    private int calcDigit( String str )
    {
        int sum = 0;
        
        int weight [] = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        for ( int i = str.length() - 1, digito; i >= 0; i-- )
        {
            digito = Integer.parseInt( str.substring( i, i + 1 ) );
        
            sum += digito * weight[ weight.length - str.length() + i ];
        }

        sum = 11 - sum % 11;

        return sum > 9 ? 0 : sum;
    }

}
