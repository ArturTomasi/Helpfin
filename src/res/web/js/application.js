$(document).ready(function()
{
    $('#posting').click( function()
    {
        if( $('#balance').css('display') === 'none' )
        {
            $('#balance').slideDown( 250 );
            $('#posting').attr( 'class', 'fa fa-chevron-up' );
        }
        else
        {
            $('#balance').slideUp( 250 );
            $('#posting').attr( 'class', 'fa fa-chevron-down' );
        }
    } );
} );


// Graphic of the Home

var revenueData, costData, categories;
var months = new Array( 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro','Outubro', 'Novmbro', 'Dezembro' );


function setRevenueData( data )
{
    revenueData = data;
};

function setCostData( data )
{
    costData = data;
};

function setCategories ( cat )
{
    categories = cat;
};


function refreshGraphic()
{
    $( function () 
    {
        $( '#container' ).highcharts(
        {
            chart:    { backgroundColor: '#ECEFF1' },
            title:    { text: 'Balanço Mensal - ' + months[ new Date().getMonth() ], style: { fontWeight: 'bold', color: "#607D8B" } },
            subtitle: { text: 'Acumulado dos lançamentos finalizados do mês de ' + months[ new Date().getMonth() ] + " (por dia)", style: { color: "#607D8B" } },
            tooltip:  { shared: true, pointFormat: '{series.name}: <b>R$ {point.y:,.2f}</b><br/>' },
            legend:   { layout: 'horizontal', align: 'center', verticalAlign: 'bottom', floating: false, backgroundColor: 'transparent' },
            
            xAxis: [ { categories: categories, crosshair: true } ],
            yAxis: [
            { 
                labels: { format: '{value}', enabled: false, style: { color: 'red' } },
                title:  { text: 'Despesas',  enabled: false, style: { color: 'red' } }
            },
            {  
                labels: { format: '{value}', enabled: false, style: { color: 'green' } },
                title:  { text: 'Receitas',  enabled: false, style: { color: 'green' } }
            } ],
            
            series: [
            {
                name: 'Despesas', type: 'column', color: '#e60000', data: costData
            }, 
            {
                name: 'Receitas', type: 'spline', color: '#009933', data: revenueData
            } ],
            credits: [ { enabled: false } ]
        } );
    } );
};

// Inspect View

function setPosting( posting )
{
    var str = posting[ 'posting_info' ].toString().replace( /\"/g, "'");
    
    $( '#posting_id' ).text( posting[ 'posting_id' ] );
    $( '#posting_name' ).text( posting[ 'posting_name' ] );
    $( '#posting_info' ).append( $( str ) );
    $( '#real_date' ).text( posting[ 'real_date' ] );
    $( '#estimate_date' ).text( posting[ 'estimate_date' ] );
    $( '#real_value' ).text( posting[ 'real_value' ] );
    $( '#estimate_value' ).text( posting[ 'estimate_value' ] );
    $( '#fl_completion_auto' ).text( posting[ 'fl_completion_auto' ] );
    $( '#portion' ).text( posting[ 'portion' ] );
    $( '#portion_total' ).text( posting[ 'portion_total' ] );
    $( '#state' ).text( posting[ 'state' ] );
    $( '#ref_posting_category' ).text( posting[ 'ref_posting_category' ] );
    $( '#ref_user' ).text( posting[ 'ref_user' ] );
    $( '#ref_completion_type' ).text( posting[ 'ref_completion_type' ] );
    $( '#ref_entity' ).text( posting[ 'ref_entity' ] );
    $( '#ref_posting' ).text( posting[ 'ref_posting' ] );
};


// Inspect Category View

function setCategory( category )
{
    var str = category[ 'category_info' ].toString().replace( /\"/g, "'");
    
    $( '#category_info' ).children().remove();
    
    $( '#category_id' ).text( category[ 'category_id' ] );
    $( '#category_name' ).text( category[ 'category_name' ] );
    $( '#category_info' ).append( $( str ) );
    $( '#count_finished' ).text( category[ 'count_finished' ] );
    $( '#count_deleted' ).text( category[ 'count_deleted' ] );
    $( '#count_registred' ).text( category[ 'count_registred' ] );
    $( '#count_progress' ).text( category[ 'count_progress' ] );
    $( '#sum_count' ).text( category[ 'sum_count' ] );
    $( '#category_type' ).text( category[ 'category_type' ] );
    
    $(function () 
    {
        Highcharts.setOptions( { lang: { drillUpText: '◁ Voltar para {series.name}' } } );
        
        $('#chart').highcharts( 
        {
            chart: { type: 'column', backgroundColor: '#ECEFF1' },
            title: { text: 'Lançamentos da Categoria - ' + category[ 'category_name' ], style: { fontWeight: 'bold', color: "#607D8B" } },
            subtitle: { text: 'Quantidade de lançamentos por Situação', style: { color: "#607D8B" }  },
            xAxis: [ { type: 'category' } ],
            yAxis: [ {
                title: { text: 'Quantidade', style: { fontWeight: 'bold', color: "#607D8B" } } } ],
            legend: { enabled: false },
            plotOptions: {
                series: { borderWidth: 0, 
                    dataLabels: { enabled: true, format: '{point.y:.0f}' } } },

            tooltip: { headerFormat: '<span style="font-size:11px">{series.name}</span><br>', 
                       pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f} de '+ category['sum_count'] +'</b><br/>' },

            series: [{ name: 'Situação', colorByPoint: true,
                data: [
                    { name: 'Cadastrados',  y: parseInt( category[ 'count_registred' ] ),color: '#3364c8' }, 
                    { name: 'Em Andamento', y: parseInt( category[ 'count_progress' ] ), color: '#ded604' },
                    { name: 'Finalizados',  y: parseInt( category[ 'count_finished' ] ), color: '#408c1b' },
                    { name: 'Excluidos',    y: parseInt( category[ 'count_deleted' ] ),  color: '#d82027' }]
            }],
            credits: [ { enabled: false } ]
        });
    });
}

// drilldown inspect view

var serie, drilldownSeries;

function setSerie( value )
{
    serie = value;
}

function setDrilldown( value )
{
    drilldownSeries = value;
}

function drilldown()
{
    $(function () 
    {
        Highcharts.setOptions( { lang: { drillUpText: '◁ Voltar para {series.name}' } } );
        
        $('#drilldown').highcharts(
        {
            chart: { type: 'column', backgroundColor: '#ECEFF1' },

            title: { text: 'Lançamentos da Categoria' , style: { fontWeight: 'bold', color: "#607D8B" } },

            subtitle: { text: 'Quantidade de lançamentos por Situação', style: { color: "#607D8B" }  },

            xAxis: [ { type: 'category' } ],

            yAxis: [ { title: { text: 'Quantidade', style: { fontWeight: 'bold', color: "#607D8B" } } } ],

            legend: { enabled: false },

            plotOptions: { series: { borderWidth: 0, dataLabels: { enabled: true, format: '{point.y:.0f}' } } },

            tooltip: { headerFormat: '<span style="font-size:11px">{series.name}</span><br>', 
                       pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}' +'</b><br/>' },

            series: [{ 
                    name: 'Lançamentos', colorByPoint: true,
                    data: serie 
                } ],
            drilldown: {
            series: drilldownSeries
            },
            credits: [ { enabled: false } ]
        } );
    });
}

