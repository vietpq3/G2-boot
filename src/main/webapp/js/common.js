function commonInit(){
    
    $('#btnLogout').click(function(event){
		if(confirm('Do you want to logout?')){
        	$('#formRedirect').attr("action", "../logout/");
            $('#formRedirect').submit();
        } else{
        	event.preventDefault();
        }
    });
    
    $('#btnHome').click(function(event) {
        if(confirm('Do you want to go to Home?')){
        	$('#formRedirect').attr("action", "../home/index");
            $('#formRedirect').submit();
        } else{
        	event.preventDefault();
        }
    });
    
}