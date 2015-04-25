/**
 * Created by Xavier on 20/04/15.
 */
$(document).ready(function(){
    $("#ajoutresa").click(function(){
        $("#divbg").fadeIn(1000);
        $("#divcontent").fadeIn(1000);
    });
    $("#resa").click(function(){
        var piste = $('#piste').find(':selected').val();
        console.log(piste);
        var jour = $('#jour').find(':selected').val();
        console.log(jour);
        var horaire = $('#horaire').find(':selected').val();
        console.log(horaire);
        $('#'+jour+'-'+horaire+'-'+piste).addClass("redsquare");


        $("#divbg").fadeOut(1000);
        $("#divcontent").fadeOut(1000);
    });
});

