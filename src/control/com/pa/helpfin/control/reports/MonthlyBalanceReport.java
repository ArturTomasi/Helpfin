package com.pa.helpfin.control.reports;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingType;
import com.pa.helpfin.model.data.UserCache;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * @author artur
 */
public class MonthlyBalanceReport 
    extends 
        AbstractReport
{
    public MonthlyBalanceReport()
    {
        super( true );
    }

    @Override
    protected void generateDocument( Document document ) throws Exception 
    {
        Calendar calendar = GregorianCalendar.getInstance();
        
        String month = calendar.getDisplayName( Calendar.MONTH, Calendar.LONG, ApplicationUtilities.getLocale() );
        
        setTitle( "Balanço dos Lançamentos " + month + "/" + calendar.get( Calendar.YEAR ) );
        setSubTitle( "Balanço dos lançamentos é um relatório para informar o usuário sobre o seu balanço mensal."  );
        
        newLine();
        
        List<Posting> postings = com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().getMonthlyBalance();

        Double sumValues [] = new Double[]{ 0d, 0d, 0d, 0d };
        
        HashMap<Integer, Double[]> statesMap = new HashMap();
        
        //make main table
        Table table = new Table( 2f, 20f, 10f, 10f, 8f, 8f, 8f, 8f );
        
        table.setHeader( "#", "Nome", "Responsável", "Categoria", "Data Estimada", "Valor Estimado", "Data Real", "Valor Real" );
            
        postings.forEach( (posting) -> 
        {
            try
            {
                Image iconState = Image.getInstance( ResourceLocator.getInstance().getImageResource( posting.getState() == Posting.STATE_FINISHED  ? "finish.png" : 
                                                                                                 posting.getState() == Posting.STATE_PROGRESS  ? "play.png"   :
                                                                                                 posting.getState() == Posting.STATE_REGISTRED ? "new.png" : "delete.png" ) );

                PostingCategory category = com.pa.helpfin.model.ModuleContext.getInstance().getPostingCategoryManager().getValue( posting.getPostingCategory() );

                table.addRow( iconState, 
                              posting.getName(), 
                              UserCache.getUser( posting.getUser() ),
                              category.getName(),
                              posting.getEstimateDate() != null ? df.format( posting.getEstimateDate() ) : "n/d",
                              nf.format( posting.getEstimateValue() ),
                              posting.getRealDate() != null ? df.format( posting.getRealDate() ) : "n/d",
                              nf.format( posting.getRealValue() ) );
                
                int state = posting.getState();
                
                Double[] value = statesMap.get( state );
                
                if( value == null )
                {
                    value = new Double[]{0d,0d};
                }
                
                Double costValue    = value[0];
                Double revenueValue = value[1];
                
                if( category.getPostingType() == PostingType.TYPE_COST )
                {
                    sumValues[0] += posting.getEstimateValue();
                    sumValues[1] += posting.getRealValue();
                    statesMap.put( state, new Double[]{ costValue += state == Posting.STATE_FINISHED ? posting.getRealValue() : posting.getEstimateValue(), revenueValue }  );
                }
                
                else if( category.getPostingType() == PostingType.TYPE_REVENUE )
                {
                    sumValues[2] += posting.getEstimateValue();
                    sumValues[3] += posting.getRealValue();
                    statesMap.put( state, new Double[]{ costValue, revenueValue += state == Posting.STATE_FINISHED ? posting.getRealValue() : posting.getEstimateValue() }  );
                }
            }

            catch( Exception e )
            {
                ApplicationUtilities.logException( e );
            }
        } );
        
        document.add( new Paragraph("Lançamentos", fontSubTitle ) );
        
        separator();
        
        document.add( table );
        
        newPage();
        
        document.add( new Paragraph( "Total de Valores por Situação", fontSubTitle ) );
        
        separator();

        // make sum states table
        DetailsTable statesTable = new DetailsTable( 5f, 33f, 33f, 33f, 33f );
        
        statesTable.setFormatCelll( false );
        statesTable.setBackground( BaseColor.WHITE );
        statesTable.setHeader( "#",  "Situação", "Valor Despesa", "Valor Receita", "Valor Total" );

        Double stateValues[] = statesMap.get( Posting.STATE_REGISTRED );
        
        if( stateValues != null )
        {
            statesTable.addRow( Image.getInstance( ResourceLocator.getInstance().getImageResource( "new.png" ) ),
                                Posting.STATES[ Posting.STATE_REGISTRED ],
                                nf.format( stateValues[0] ),
                                nf.format( stateValues[1] ),
                                nf.format( stateValues[0] + stateValues[1] ) );
        }
        
        stateValues = statesMap.get( Posting.STATE_PROGRESS );
        
        if(stateValues != null )
        {
            statesTable.addRow( Image.getInstance( ResourceLocator.getInstance().getImageResource( "play.png" ) ),
                                Posting.STATES[ Posting.STATE_PROGRESS ] ,
                                nf.format( stateValues[0] ) ,
                                nf.format( stateValues[1] ) ,
                                nf.format( stateValues[0] + stateValues[1] ) );
        }
        
        stateValues = statesMap.get( Posting.STATE_FINISHED );
        
        if(stateValues != null )
        {
            statesTable.addRow( Image.getInstance( ResourceLocator.getInstance().getImageResource( "finish.png" ) ),
                                Posting.STATES[ Posting.STATE_FINISHED ] ,
                                nf.format( stateValues[0] ) ,
                                nf.format( stateValues[1] ) ,
                                nf.format( stateValues[0] + stateValues[1] ) );
        }
        
        document.add( statesTable );
        
        newLine();
        newLine();
        
        document.add( new Paragraph("Total de Despesas/Receitas", fontSubTitle ) );
        
        separator();

        // make sum
        DetailsTable sumTable = new DetailsTable( 33f, 33f, 33f );
        
        sumTable.setBackground( BaseColor.WHITE );
        sumTable.setFormatCelll( false );
        
        sumTable.setHeader( "Tipo", "Valor Estimado", "Valor Real" );
        
        sumTable.addRow( "Despesa", nf.format( sumValues[0] ), nf.format( sumValues[1] ) );
        
        sumTable.addRow(  "Receita", nf.format( sumValues[2] ), nf.format( sumValues[3] ) );
        
        sumTable.addRow( "Total", nf.format( sumValues[2] - sumValues[0] ), nf.format( sumValues[3] - sumValues[1] ) );
        
        document.add( sumTable );
    }
}
