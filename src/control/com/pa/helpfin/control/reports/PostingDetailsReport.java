package com.pa.helpfin.control.reports;

import com.itextpdf.text.Document;
import com.pa.helpfin.model.data.CompletionType;
import com.pa.helpfin.model.data.Entity;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.UserCache;

/**
 * @author artur
 */
public class PostingDetailsReport 
    extends 
        AbstractReport
{
    private Posting posting;
    
    public PostingDetailsReport() 
    {
    }
    
    
    public void setSource( Posting posting )
    {
        this.posting = posting;
    }
    
    
    @Override
    protected void generateDocument( Document document ) throws Exception 
    {
        if( posting == null )
            throw new Exception( "Posting cannot be null" );
        
        setTitle( posting.getName(), "posting.png" );
        
        newLine();
        
        addHTML( posting.getInfo() );
        
        newLine();
        
        PostingCategory category = com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getPostingCategoryManager()
                                        .getValue( posting.getPostingCategory() );

        CompletionType type = com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getCompletionTypeManager()
                                    .getValue( posting.getCompletionType() );

        Entity entity = com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getEntityManager()
                                    .getValue( posting.getEntity() );
        
        Posting origin = com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getPostingManager()
                                    .getValue( posting.getPosting() );
        
        DetailsTable table = new DetailsTable( 35f,70f );
        
        table.addRow( "ID" ,                      String.valueOf( posting.getId() ) );
        table.addRow( "Responsável",              UserCache.getUser( posting.getUser() ).getName() );
        table.addRow( "Data Real",                posting.getRealDate()  != null ? df.format( posting.getRealDate() ) : "n/d" );
        table.addRow( "Data Estimada",            posting.getEstimateDate() != null ? df.format( posting.getEstimateDate() ) : "n/d" );
        table.addRow( "Valor Real",               nf.format( posting.getRealValue() ) );
        table.addRow( "Valor Estimado",           nf.format( posting.getEstimateValue() ) );
        table.addRow( "Finaliza Automáticamente", posting.isCompletionAuto() ? "Sim" : "Não" );
        table.addRow( "Parcela",                  String.valueOf( posting.getPortion() ) );
        table.addRow( "Total de Parcelas",        String.valueOf( posting.getPortionTotal() ) );
        table.addRow( "Situação",                 Posting.STATES[ posting.getState() ] );
        table.addRow( "Categoria do Lançamento",  category != null ? category.getName() : "n/d" );
        table.addRow( "Tipo de Finalização",      type != null ? type.getName() : "n/d" );
        table.addRow( "Entidade",                 entity != null ? entity.getName() : "n/d" );
        table.addRow( "Original",                 origin != null ? origin.getName() + "(" + origin.getId() + ")" : "n/d" );
        
        document.add( table ); 
       
    }
}
