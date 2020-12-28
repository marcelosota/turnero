/**
 * 
 */
/*
 * $(document).ready(function(){ alert("alguna cosa"); });
 */

/*
 * function deshabFest(date) { var disabledDays =
 * document.getElementById("txtFechasOcultas").value; alert(disableDays);
 * 
 * var string = jQuery.datepicker.formatDate('yyyy-mm-dd', date); return [
 * disabledDays.indexOf(string) == -1, '' ] }
 */

function noSundaysOrHolidays(date) {
	var holidays = $("#formRegistroCiudadano\\:txtFechasOcultas").val().split(
			",");

	var day = date.getDay();
	if (day != 0) {
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		// Deshabilitar
		for (i = 0; i < holidays.length; i++) {
			if ($.inArray((y) + '-' + (m + 1) + '-' + d, holidays) != -1) {
				return [ false ];
			}
		}
		return [ true ];
	} else {
		return [ day != 0, '' ];
	}
}

function suspensionRecuperacion(date) {
	let cadena = $("#formRegistroCiudadano\\:txtFechasOcultas").val();
	let dias = cadena.split(";")
	//alert(dias);
	let holidays = dias[0].split(",");
	//alert(holidays);
	let recupera = dias[1].split(",");
	//alert(recupera);
	
    var day = date.getDay();    
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();      
      if($.inArray((y) + '-' + (m+1) + '-' + d, holidays) != -1) {
        return [false];
      }else{
        if(day==0 || day ==6){
          if($.inArray((y) + '-' + (m+1) + '-' + d, recupera) != -1) {
            return[true]
          }else{
            return [false];
          }          
          
        }else{
          return [true]
        }          
      }      
    
  
}