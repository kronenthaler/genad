/**
 *	AJAX connect function
 *	@param url 		the target url
 *	@param _text	the mode of the response, true for text, false for XML dom object
 *	@param handler	the pointer to function to handler the answer, the fuction must recieve a parameter (the answer itself)
 */
function connect(url,_text,handler){
	var request = null;	
	if(window.XMLHttpRequest) { // Mozilla, Safari, ...
		request = new XMLHttpRequest();
	}else if(window.ActiveXObject) { // IE
		try{
			request = new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			try{
				request = new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){}
		}
	}

	if(!request){
		alertMsg("The XMLHttpRequest cannot be created");
		return;
	}

	request.onreadystatechange = function(){
	    if(request.readyState == 4) { //ready, process answer
			if(request.status == 200){ //answer complete correctly
				if(_text) handler(request.responseText);
				else 	  handler(request.responseXML);
			}else{ //answer with error, 404, 403, 500, or another
				alertMsg("MSG_ERROR_GENERIC "+request.status);
			}
		}else{ // not ready yet, show some progress text 
			
		}
	};
	request.open('GET', url, true);
	request.send(null);
}

/**
 *	Retrieve and xml file, tranform with xsl file and write in the document
 *	@param	targetPage	The page with the target xml file.
 */
function goTo(targetPage){
	var base = document.getElementById('base');
	document.location.href=(base != null ? base.href:'')+targetPage;
}

/**	
 *	show/hide the row next to this component container row.
 */
function showNextRow(comp){	
	var tr=comp.parentNode.parentNode.nextSibling;
	while(tr.nodeName != 'tr' && tr.nodeName != 'TR' ) 
		tr=tr.nextSibling;
		
	tr.style.display=tr.style.display=='none'?'table-row':'none';
	comp.src=tr.style.display=='none'?"images/close.gif":"images/open.gif";
	
	myself=comp.parentNode.parentNode;
	for(i=0;i<myself.childNodes.length;i++)
		if(myself.childNodes[i].nodeName=='TD' || myself.childNodes[i].nodeName=='td')
			myself.childNodes[i].className=tr.style.display=='none'?'part1':'';
}

/**
 *	Send the form to the destination address and transform the answer.
 *	@param	XMLDoc	An XML DOM object to transform
 *	@param	targetStyle	The name of the xsl file to use in the transformation
 *	@param	targetDiv	The ID of the container component to render the output.
 */
function transform(XMLdoc,targetStyle,targetDiv){
 	if(window.navigator.appName != 'Microsoft Internet Explorer') {
		var xsltProcessor = new XSLTProcessor();
		var request = new XMLHttpRequest();
		request.open("GET",targetStyle , false);
		request.send(null);
		//load the xsl file
		xsltProcessor.importStylesheet(request.responseXML)
				
		fragment = xsltProcessor.transformToFragment(XMLdoc,document);
		
		//remove all the child nodes of the target div, and append the new processed root.
		targetDiv=document.getElementById(targetDiv);
		while(targetDiv.childNodes.length!=0)
			targetDiv.removeChild(targetDiv.childNodes[0]);

	
		targetDiv.appendChild(fragment);
	}else{
		// Load the XSL
		/*
		//var xsl = new ActiveXObject("Microsoft.XMLDOM");
		//var xsl = new ActiveXObject("Msxml2.FreeThreadedDOMDocument.3.0");
		xsl.async = "false";
		xsl.load(targetStyle);
		fragment=XMLdoc.transformNode(xsl);//*/
		
		/*targetDiv=document.getElementById(targetDiv);
		while(targetDiv.childNodes.length!=0)
			targetDiv.removeChild(targetDiv.childNodes[0]);
	
		targetDiv.appendChild(fragment);*/
		
	}
}
 
/** 
 *	Validation functions, is neccessary add function to validate numbers (integers and decimals), 
 *	email, alphanumeric. include the type range?
 *	@param	input	The input object to validate
 *	@param	label	The label text for this input in the form.
 *	@return	true iif the input is not empty, false otherwise. 
 */
function isRequired(input, label){
 	if(input.value == ''){
 		alertMsg('MSG_IS_REQUIRED'.replace("%1", label));
		//alertMsg('The "'+text+'" field is required. Cannot be left in blank.');
		if(input.type != 'hidden')
			input.focus();
 		input.style.background='#ff8080';
 		//poner el icono de warning
 	}else 
		input.style.background='#ffffff';
 	
 	return input.value != '';
}
 
function isValidPassword(input, confirm, label,required){
 	if(required && !isRequired(input,label)) return false;
 	
 	if(!isRequired(confirm, label)) return false;
	if(input.value!=confirm.value){
		alertMsg('MSG_INVALID_PASSWORD'.replace("%1",label));
		return false;
	}
	return true;
}
 
function isValidEmail(input, label, required){
 	if(required && !isRequired(input,label)) return false;
 	
 	if(input.value.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1) {
		alertMsg('MSG_INVALID_EMAIL'.replace("%1",label));
		return false;
	}
 	return true;
}
 
function isValidInteger(input, label, required){
 	if(required && !isRequired(input,label)) return false;
 	
 	if(input.value.search(/^[-]?[0-9]+$/)==-1){
		alertMsg('MSG_INVALID_INTEGER'.replace("%1",label));
		return false;
	}
 	return true;
}
 
function isValidDecimal(input, label, required){
 	if(required && !isRequired(input,label)) return false;
 	
 	input.value=input.value.replace(/,/,'.');	
 	if(input.value.search(/^[-]?([1-9]{1}[0-9]{0,}((\.)[0-9]+)?|0(\.[0-9]+)?|\.[0-9]{1,})$/)==-1){
		alertMsg('MSG_INVALID_DECIMAL'.replace("%1",label));
		return false;
	}
	return true;
}

function isValidRTE(input, name, label, required){
	var oEditor = FCKeditorAPI.GetInstance(name);
	input.value = oEditor.GetHTML();
	if(required && !isRequired(input, label)) return false;
	return true;
}

function showHide(id){	
	var elem= document.getElementById(id);
	if(elem.style.display == 'none')
		elem.style.display = 'block';
	else
		elem.style.display = 'none';
}

function reload(doc){
	document.location.reload();
}

/** move elements of a list using jquery */
function move(dir, id/*[, target, img]*/){
	var $curr = $('option:selected','#'+id);
	
	for(i=0;i<$curr.length;i++)
		if(dir == 'down')
			$($curr.get(i)).before($($curr.get(i)).next());
		else	
			$($curr.get(i)).after($($curr.get(i)).prev());
}

function selectAll(id){
	//iterate & mark items as selected
	var list=document.getElementById(id);
	for(i=0;i<list.length;i++)
		list[i].selected = true;
}

function convertDate(src, fromFormat, toFormat){
	var yearIndex = fromFormat.indexOf('y');
	var monthIndex = fromFormat.indexOf('M');
	var dayIndex = fromFormat.indexOf('d');
	var hourIndex = fromFormat.indexOf('h');
	var minutesIndex = fromFormat.indexOf('m');
	
	year = src.substring(yearIndex, yearIndex+4);
	month = src.substring(monthIndex, monthIndex+2);
	day = src.substring(dayIndex, dayIndex+2);
	hour = src.substring(hourIndex, hourIndex+2);
	minutes = src.substring(minutesIndex, minutesIndex+2);
	
	return toFormat.replace('y',year)
					.replace('M',month)
					.replace('d',day)
					.replace('h',hour)
					.replace('m',minutes);
}

function markSelected(obj){
	$("li.ui-state-active").toggleClass("ui-state-active");
	$(obj).addClass("ui-state-active");
}


function fillTimePicker(obj, suffix, value){
	var hours='00',mins='00',seconds='00';
	if(value != ''){
		hours = value.substring(0,2);
		mins = value.substring(3,5);
	}

	var html = '';
	for(i=0;i<24;i++){
		j = (i<10?'0':'')+i;
		html+='<option '+(j==hours?'selected="selected"':'')+'>'+j+'</option>';
	}
	$('#hour_'+suffix).html(html);
	
	html='';
	for(i=0;i<60;i+=5){
		j = (i<10?'0':'')+i;
		html+='<option '+(j==mins?'selected="selected"':'')+'>'+j+'</option>';
	}
	$('#min_'+suffix).html(html);

	$('#'+obj).val(hours+":"+mins+":"+seconds);
}

function updateTimeHidden(obj,hidden,hours){
	var h = '00',m='00',s='00';
	var time = $('#'+hidden).val();
	if(time=='') time = h+':'+m+':'+s;
	if(hours){
		h = $(obj).val();
		m = time.substring(3,5);
	}else{
		h = time.substring(0,2);
		m = $(obj).val();
	}
	$('#'+hidden).val(h+':'+m+':'+s);
}

function alertMsg(msg){
	var div = document.createElement("div");
	div.innerHTML = msg;
	$(div).dialog({
			title: 'MSG_ERROR_GENERIC',
			bgiframe: true,
			modal: true,
			buttons:{
				"OK":
					function(){
						$(this).dialog("close")
					}
				}
			});
}

function confirmMsg(msg, mycallback){
	//var p = $.prompt(msg,{opacity: 0.3, buttons:{OK: true}, callback: mycallback});
	//p.children('#jqi').corner("5px");
	//alert('confirm');
	var div = document.createElement("div");
	div.innerHTML = msg;
	$(div).dialog({
			title: 'MSG_ERROR_GENERIC',
			zIndex: 3999,
			//position: 'top',
			modal: true,
			closeOnEscape: false,
			beforeclose: function(event, ui) {mycallback();},
			buttons:{
				"OK":
					function(){
						//mycallback();
						$(this).dialog("close");
					}
				}
			});
	return false;
}
