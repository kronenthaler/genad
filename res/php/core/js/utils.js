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
		alert("The XMLHttpRequest cannot be created");
		return;
	}

	request.onreadystatechange = function(){
	    if(request.readyState == 4) { //ready, process answer
			if(request.status == 200){ //answer complete correctly
				if(_text) handler(request.responseText);
				else 	  handler(request.responseXML);
			}else{ //answer with error, 404, 403, 500, or another
				alert("Error "+request.status);
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
 *	@param	targetStyle	The page xsl 
 *	@param	targetDiv	The target container to write the result	
 */
function getAndTransform(targetPage,targetStyle,targetDiv){
	if(window.XMLHttpRequest) {
		// load the xml file,
		request = new XMLHttpRequest();
		request.open("GET", targetPage, false);
		request.send(null);
		
		transform(request.responseXML,targetStyle,targetDiv);
	}else{
		var xml = new ActiveXObject("Microsoft.XMLDOM");
		xml.async = false;
		xml.load(targetPage);
		
		transform(xml,targetStyle,targetDiv);
	}
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
 	if(window.XMLHttpRequest) {
		var xsltProcessor = new XSLTProcessor();
		var request = new XMLHttpRequest();
		request.open("GET",targetStyle , false);
		request.send(null);
		//load the xsl file
		xsltProcessor.importStylesheet(request.responseXML)
				
		fragment = xsltProcessor.transformToFragment(XMLdoc,document);
	}else{
		// Load the XSL
		var xsl = new ActiveXObject("Microsoft.XMLDOM");
		xsl.async = false;
		xsl.load(targetStyle);

		fragment=XMLDoc.transformNode(xsl);
	}
	
	//remove all the child nodes of the target div, and append the new processed root.
	targetDiv=document.getElementById(targetDiv);
	while(targetDiv.childNodes.length!=0)
		targetDiv.removeChild(targetDiv.childNodes[0]);

	targetDiv.appendChild(fragment);
}

 
/** 
 *	Validation functions, is neccessary add function to validate numbers (integers and decimals), 
 *	email, alphanumeric. include the type range?
 *	@param	input	The input object to validate
 *	@param	text	The label text for this input in the form.
 *	@return	true iif the input is not empty, false otherwise. 
 */
function isRequired(input, text){
 	if(input.value == ''){
 		alert('The "'+text+'" field is required. Cannot be left in blank.');
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
		alert('The field "'+label+'" doesn\'t match with the confirmation.');
		return false;
	}
	return true;
}
 
function isValidEmail(input, label, required){
 	if(required && !isRequired(input,label)) return false;
 	
 	if(input.value.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1) {
		alert('The field "' + label + '" only can be a valid email address.');
		return false;
	}
 	return true;
}
 
function isValidInteger(input, label,required){
 	if(required && !isRequired(input,label)) return false;
 	
 	if(input.value.search(/^[-]?[0-9]+$/)==-1){
		alert('The field "' + label + '" only can be integers.');
		return false;
	}
 	return true;
}
 
function isValidDecimal(input, label,required){
 	if(required && !isRequired(input,label)) return false;
 	
 	input.value=input.value.replace(/,/,'.');	
 	if(input.value.search(/^[-]?([1-9]{1}[0-9]{0,}((\.)[0-9]+)?|0(\.[0-9]+)?|\.[0-9]{1,})$/)==-1){
		alert('The field "' + label + '" only can be decimals.');
		return false;
	}
	return true;
}

function isValidRTE(input, name, label, required){
	input.value=tinyMCE.getContent(name);
//	tinyMCE.removeMCEControl(name);
	if(required && !isRequired(input, label)) return false;
	return true;
}

function clean(q){
	for(var x in q)
		tinyMCE.removeMCEControl(x);
	return true;
}