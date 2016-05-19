package com.pa.helpfin.control.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.Entity;
import java.util.List;

/**
 * @author artur
 */
public class EntityReport 
    extends 
        AbstractReport
{
    private List<Entity> entities;
    
    public EntityReport() 
    {
         super( true );
    }
    
  
    public void setSource( List<Entity> entities )
    {
        this.entities = entities;
    }

    
    
    @Override
    protected void generateDocument( Document document ) throws Exception 
    {
        setTitle( "Relatório de Entidades" );
        setSubTitle( "Relatório de Gerencial das Entidades do HelpFin" );
        
        newLine();

        Table table = new Table( 0.03f, 0.2f, 0.2f, 0.3f, 0.1f, 0.15f );
        table.setWidthPercentage( 100f );
        
        table.setHeader(  "#", "Nome", "Razão Social", "Email", "Telefone", "CNPJ"  );

        for ( Entity entity : entities ) 
        {
            table.addRow( Image.getInstance( ResourceLocator.getInstance().getImageResource( entity.getState() == Entity.STATE_ACTIVE ? "finish.png" : "delete.png" ) ),
                          entity.getName(),
                          entity.getCompannyName() != null ? entity.getCompannyName() : "n/d",
                          entity.getMail()  != null ? entity.getMail() : "n/d",
                          entity.getPhone() != null ? entity.getPhone().replaceFirst( "([0-9]{2})([0-9]{4})([0-9]{4,5})$", "($1)$2-$3" ) : "n/d",
                          entity.getCnpj()  != null  ? entity.getCnpj().replaceAll( "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4" ) : "n/d" );
        }

        document.add( table );
    }
    
}
