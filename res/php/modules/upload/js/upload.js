Upload = function(mainform,_sender){
	var mainForm = mainform; /* MUST HAVE an absolute URL*/
	var list = new Array();
	var answers = new Array();
	var sender = _sender;
	
	this.EMPTY = 0;
	this.READY = 1;
	this.NOT_SENDED = 2;
	this.SENDING = 4;
	this.WRONG = 8;
	
	this.uploadFiles = function(){
		var ready2send=true;
	
		for(k=0;k<list.length;k++) //list.length
			if(answers[k] == this.WRONG) //answers[k]
				this.uploadReady(list[k],this.EMPTY); //list[k]
		
		var frames1 = Array();
		for(i=0;i<frames.length;i++) //patch for avoid nullpointers on references
			frames1[frames[i].name] = i;
		
		for(i=0;i<list.length;i++){ //list.length
			var frame   = frames[frames1['if_'+list[i]]];
			var ifdom   = frame.document;//document.getElementById('if_'+list.item(i));
			var hidden  = ifdom.getElementById('file_'+list[i]);
			var form 	= ifdom.getElementById('frm_up');
					
			//if(hidden != null && hidden.value != ''){
			if(frame.anychange){
				this.uploadReady(list[i], this.SENDING); //list[i]
				
				if(ifdom.getElementById('error'))
					ifdom.getElementById('error').style.display="none";
				ifdom.getElementById('btn_del').disabled=true;
				
				ready2send = false;
			}
		}
		
		for(i=0,n=list.length;i<n;i++){ //list.length
			var frame = frames[frames1['if_'+list[i]]]; //list[i]
			var ifdom = frame.document;
			var form=ifdom.getElementById('frm_up');
			var hidden=ifdom.getElementById('file_'+list[i]);//list[i]
			
			//if(hidden != null && hidden.value != '')
			if(frame.anychange)
				form.submit();
		}
		return ready2send;
	};
	
	this.uploadReady = function (id, status){
		var index = jQuery.inArray(id, list);
		answers.splice(index,1,status); //remove + insert
		//var index = list.indexOf(id);
		//answers.removeAt(index);
		//answers.insert(index, status);
		
		for(k=0;k<answers.length;k++) //answers.length
			if(answers[k] == this.SENDING || answers[k] == this.WRONG) //answers[k]
				return;
		
		if(status != this.EMPTY){
			var form=document.getElementById(mainForm);
			var action=form.getAttribute("action");
		
			/*
			if(action.indexOf("://")==-1) //do the action absolute for the bind
				form.setAttribute('action', window.location.href.substring(0, window.location.href.lastIndexOf('/')+1) + action);
			*/
			sender();
		}
	};
	
	this.addFile = function (name){
		//list.add(name);
		//answers.add(this.EMPTY);
		list.push(name);
		answers.push(this.EMPTY);
	};
};
