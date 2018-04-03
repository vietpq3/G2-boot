$(document).ready(function(){
    init();
    commonInit();
});

function init(){
    $('#btnFight').click(function(){
        $('#form').attr("action","../fight/index");
        $('#form').submit();
    });
    
    $('#btnHighScore').click(function(){
    	$('#formRedirect').attr("action", "highscore");
        $('#formRedirect').submit();
    });
}