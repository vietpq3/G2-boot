window.onload = function(){
    checkLoseFlag();
};

$(document).ready(function() {
    init();
    commonInit();
});

function init() {

    $('#btnPicture').click(function() {
        $('#formActivities').attr("action", "fight");
        $('#gameMode').attr("value", "Picture");
        $('#formActivities').submit();
    });
    
    $('.picture').click(function(){
        $('#answer').attr("value", $(this).attr("answer"));
        $('#formActivities').attr("action", "fighting");
        $('#formActivities').submit();
    });
    
}

function checkLoseFlag(){
    if($('#loseFlag').val() == "true"){
        alert("You losed");
        window.location = "../home/index";
    }
}