	$.ajaxSetup({
		  error: function( jqXHR, textStatus, errorThrown ) {
		          if (jqXHR.status === 0) {
		            console.log('Not connect: Verify Network.');
		          } else if (jqXHR.status == 404) {
		            console.log('Requested page not found [404]');
		          } else if (jqXHR.status == 500) {
		            console.log('Internal Server Error [500].');
		          } else if (textStatus === 'parsererror') {
		            console.log('Requested JSON parse failed.');
		          } else if (textStatus === 'timeout') {
		            console.log('Time out error.');
		          } else if (textStatus === 'abort') {
		            console.log('Ajax request aborted.');
		          } else {
		            console.log('Uncaught Error: ' + jqXHR.responseText);
		          }
		        }
		});
/**
 * 
 */