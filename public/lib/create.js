/**
 * Created by Xavier on 21/04/15.
 */
$(document).ready(function() {
    var i = 2;
    $('#addplayer').click(function() {
        i++;
        console.log("ajout l'element"+i);
        if (i<= 8) {
            $('#form-wrapper').append('<div class="col-md-6 playerinput"><input type="text" name="player[]" placeholder="Name" class="form-control"></div>');
        }


    });
    $('#removebutton').click(function(e) {
        var inputsize = $('#form-wrapper .playerinput').length;
        e.preventDefault();
        if (inputsize > 2) {
            $('#form-wrapper .playerinput').last().remove();
            i--;
        }
    });
});
