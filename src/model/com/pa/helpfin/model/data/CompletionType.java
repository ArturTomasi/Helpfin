package com.pa.helpfin.model.data;

/**
 * @author artur
 */
public class CompletionType 
    extends 
        Core<CompletionType>
{
    public final static int IN_CASH      = 0;
    public final static int BILLET       = 1;
    public final static int CARD         = 2;
    public final static int BANK_ACCOUNT = 3;
    public final static int OTHER        = 4;
    
    public final static String [] TYPES = new String[]
    {
        "Á vista",
        "Boleto",
        "Cartão",
        "Conta Bancária",
        "Outros"
    };
    
    private String      name = "";
    private String      info = "";
    private int         type;

    public CompletionType( String name ) 
    {
        this.name = name;
    }

    public CompletionType() 
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo( String info ) 
    {
        this.info = info;
    }

    public int getType() 
    {
        return type;
    }

    public void setType( int type ) 
    {
        this.type = type;
    }

    @Override
    public String toString() 
    {
        return name;
    }
}