jQuery.fn.timepicker = function (options){
	var showing = false;
	
	var settings= {
		showminutes: true,		//construir hora com horas + minutos
		minutes: 10,				//separacion de los minutos
		altField: null, 		//field donde dejar la data
	};

	jQuery(this).click(function (evt){
		settings.altField = options.altField || settings.altField ;
		settings.minutes = options.minutes || settings.minutes;
		settings.showminutes = options.showminutes || settings.showminutes;
				
		if(this.showing) hide();
		
		//if(settings.altField != null){
		//	self = jQuery('#'+settings.altField)
		//}else{
			self = this;
		//}
		
		$body = jQuery('body');
		$hours = jQuery("<div class='ui-timepicker-div'/>").appendTo($body);
		
		fillHours($hours);
		
		if(settings.altField!=null)
			var rect = document.getElementById(settings.altField).getBoundingClientRect();
		else
			var rect = evt.target.getBoundingClientRect();	
		
		$hours.css("position","absolute")
			  .css("left",rect.left)
			  .css("top",rect.bottom)
			  .show();
	
		function fillHours(div){
			table = jQuery('<table class="ui-timepicker" border="0" cellspacing="0" cellpadding="0"/>');
			for(i=0;i<12;i++){
				row = jQuery('<tr/>');
				
				cell = jQuery('<td><a id="h_'+i+'" class="h">'+i+'</a></td>');
				cell.appendTo(row);
				cell = jQuery('<td><a id="h_'+(i+12)+'" class="h">'+(i+12)+'</a></td>');
				cell.appendTo(row);
				
				row.appendTo(table);
			}
			table.appendTo(div);
			
			if(settings.showminutes){
				jQuery('a.h').mouseover(function(evt){
					jQuery('#minutes').remove();
					
					var hour = evt.target.id.substring(2);
					self.value = (hour < 10 ? '0':'')+hour;
					var rect = evt.target.getBoundingClientRect();
				
					$minutes = jQuery("<div class='ui-timepicker-div' id='minutes'/>").appendTo($body);
					fillMinutes($minutes, hour);
					
					$minutes.css("position","absolute")
							.css("top", rect.top)
							.css("left",rect.right)
							.show();
							
					jQuery('#'+evt.target.id).unbind('mouseout');
				});
				
				jQuery('a.h').mouseout(function(evt){
					jQuery('#minutes').remove();
					jQuery(self).blur(function (evt){
						if(this.showing){
							hide();
						}
					})//devolver el blur del componente
				});
			}else{
				jQuery('a.h').click(function(evt){
					var hour = evt.target.id.substring(2);
					document.getElementById(settings.altField).value = (hour < 10 ? '0':'')+hour;
					hide();
				});
			}
		}
		
		function fillMinutes(div, hour){
			table = jQuery('<table class="ui-timepicker" border="0" cellspacing="0" cellpadding="0"/>');
			
			var cols = 60 / settings.minutes;
			hour = (hour < 10 ? '0':'')+hour;
			
			for(i=0;i<60;i+=settings.minutes){
				row = jQuery('<tr/>');
				
				min = (i<10?'0':'') + i;
				cell = jQuery('<td><a id="m_'+i+'" class="m">'+hour+":"+min+'</a></td>');
				cell.appendTo(row);
				
				row.appendTo(table);
			}
			table.appendTo(div);
			
			jQuery('a.m').click(function(evt){
				var min = evt.target.id.substring(2);
				document.getElementById(settings.altField).value = hour+':'+((min < 10 ? '0':'')+min);
				jQuery(self).blur(function (evt){
					if(this.showing){
						hide();
					}
				})//devolver el blur del componente
				hide();
			});
			
			jQuery('a.m').mouseover(function(evt){
				jQuery(self).unbind('blur');
			});
			
			jQuery('a.m').mouseout(function(evt){
				jQuery(self).blur(function (evt){
					if(this.showing){
						hide();
					}
				})//devolver el blur del componente
			});
		}

		function hide(){
			jQuery('div.ui-timepicker-div').remove();
		}

		return this.showing = !this.showing;
	});
	
	jQuery(this).blur(function (e){
		if(this.showing){
			jQuery('div.ui-timepicker-div').remove();
			return this.showing = !this.showing;
		}
	});//*/
	return this;
}