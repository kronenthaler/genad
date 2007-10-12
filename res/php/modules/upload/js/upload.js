dojo.require("dojo.collections.ArrayList");

Upload = function(mainform){
	var mainForm = mainform;
	var list = new dojo.collections.ArrayList();
	var answers = new dojo.collections.ArrayList();
	var formComp = undefined;
	
	this.EMPTY = 0;
	this.READY = 1;
	this.NOT_SENDED = 2;
	this.SENDING = 4;
	this.WRONG = 8;
	
	this.uploadFiles = function (){
		var ready2send=true;
		for(k=0;k<list.count;k++)
			if(answers.item(k) == this.WRONG)
				this.uploadReady(list.item(k),this.EMPTY);
		
		for(i=0;i<list.count;i++){
			var ifdom=frames['if_'+list.item(i)].document;
			var hidden= ifdom.getElementById('file_'+list.item(i));
			var form=ifdom.getElementById('frm_up');
			
			if(hidden != null && hidden.value != ''){
				this.uploadReady(list.item(i), this.SENDING);
				
				document.getElementById('loading_'+list.item(i)).src="../upload/images/loading.gif";
				document.getElementById('loading_'+list.item(i)).style.display="block";
				//document.getElementById('if_'+list.item(i)).style.display='none';
				
				if(ifdom.getElementById('error')!=null)
					ifdom.getElementById('error').style.display="none";
				ifdom.getElementById('btn_del').disabled=true;
				
				ready2send = false;
			}
		}
		
		for(i=0,n=list.count;i<n;i++){
			var ifdom=frames['if_'+list.item(i)].document;
			var form=ifdom.getElementById('frm_up');
			var hidden=ifdom.getElementById('file_'+list.item(i));
			
			if(hidden != null && hidden.value != '')
				form.submit();
		}
		
		return ready2send;
	};
	
	this.uploadReady = function (id, status){
		var index = list.indexOf(id);
		answers.removeAt(index);
		answers.insert(index, status);
		
		if(status == this.READY || status == this.WRONG)
			document.getElementById('loading_'+id).style.display='none';
		
		for(k=0;k<answers.count;k++)
			if(answers.item(k) == this.SENDING || answers.item(k) == this.WRONG)
				return;
		
		if(status != this.EMPTY){
			//if(formComp == undefined){ alert("ala verga"); return; }
			//formComp.submit();
			document.getElementById(mainForm).submit();
		}
		//document.getElementById(mainForm).submit();
		//hacer que se envie el mainform sin tener que hacer la llamada
	};
	
	this.addFile = function (name){
		list.add(name);
		answers.add(this.EMPTY);
	};
};