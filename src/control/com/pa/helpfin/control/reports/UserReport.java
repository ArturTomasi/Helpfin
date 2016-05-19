package com.pa.helpfin.control.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.User;
import java.util.List;

/**
 * @author artur
 */
public class UserReport 
    extends 
        AbstractReport
{
    private List<User> users;

    public UserReport()
    {
        super( true );
    }
    
        

    public void setSource( List<User> users )
    {
        this.users = users;
    }

    
    
    @Override
    protected void generateDocument( Document document ) throws Exception 
    {
        setTitle( "Relatório de Usuários" );
        setSubTitle( "Relatório de Gerencial dos Usuários do HelpFin" );
        
        newLine();

        Table table = new Table( 0.04f, 0.3f, 0.3f, 0.15f, 0.15f, 0.15f, 0.1f, 0.15f );
        table.setWidthPercentage( 100f );
        
        table.setHeader(  "#", "Nome", "Email", "Telefone", "CPF", "RG", "Sexo", "Categoria" );

        for ( User user : users ) 
        {
            table.addRow( Image.getInstance( ResourceLocator.getInstance().getImageResource( user.getState() == User.STATE_ACTIVE ? "finish.png" : "delete.png" ) ),
                            user.getName(),
                            user.getMail(),
                            user.getPhone().replaceFirst( "([0-9]{2})([0-9]{4})([0-9]{4,5})$", "($1)$2-$3" ),
                            user.getCpf().replaceAll( "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4" ),
                            user.getRg(),
                            User.GENDER[ user.getGender() ],
                            User.ROLE[ user.getRole() ] );
        }

        document.add( table );
    }
    
}
