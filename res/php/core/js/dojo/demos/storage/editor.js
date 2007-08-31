/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/

dojo.require("dojo.dom");
dojo.require("dojo.event.*");
dojo.require("dojo.html");
dojo.require("dojo.lfx.*");
dojo.require("dojo.widget.Editor2");
dojo.require("dojo.storage.*");

// IE 7.0 does not support XHR loading from file:// URLs
// when loaded from a file:// URL itself - this breaks
// us for loading all of our Editor2 widget resources.
// This is if we want to use Moxie in conjunction with the 
// dojo.storage.browser.FileStorageProvider for web apps
// loaded from the native file system
if(dojo.render.html.ie70 == true
	&& window.location.protocol.indexOf("file") != -1){
	alert("Moxie on Internet Explorer 7.0 is not yet supported when loaded from the file:// URL");		
}

var Moxie = {
	initialize: function(){
		//dojo.debug("test_storage.initialize()");
		
		// fill out the offline link
		dojo.byId("offlineLink").href = window.location.href;
		
		// clear out old values
		dojo.byId("storageKey").value = "";
		dojo.byId("storageValue").value = "";
		
		// write out our available keys
		this._printAvailableKeys();
		
		// initialize our event handlers
		var directory = dojo.byId("directory");
		dojo.event.connect(directory, "onchange", this, this.directoryChange);
		dojo.event.connect(dojo.byId("saveButton"), "onclick", this, this.save);
		dojo.event.connect(dojo.byId("configureButton"), "onclick", this, this.configure);
	},
	
	directoryChange: function(evt){
		var key = evt.target.value;
		
		// add this value into the form
		var keyNameField = dojo.byId("storageKey");
		keyNameField.value = key;
		
		// if blank key ignore
		if(key == ""){
			return;
		}
		
		this._handleLoad(key);		
	},
	
	save: function(evt){
		// cancel the button's default behavior
		evt.preventDefault();
		evt.stopPropagation();
		
		// get the new values
		var key = dojo.byId("storageKey").value;
		var richTextControl = dojo.widget.byId("storageValue");
		var value = richTextControl.getEditorContent();
		
		if(key == null || typeof key == "undefined" || key == ""){
			alert("Please enter a file name");
			return;
		}
		
		if(value == null || typeof value == "undefined" || value == ""){
			alert("Please enter file contents");
			return;
		}
		
		// do the save
		this._save(key, value)
	},
	
	configure: function(evt){
		// cancel the button's default behavior
		evt.preventDefault();
		evt.stopPropagation();
		
		if(dojo.storage.hasSettingsUI()){
			// redraw our keys after the dialog is closed, in
			// case they have all been erased
			var self = this;
			dojo.storage.onHideSettingsUI = function(){
				self._printAvailableKeys();
				
				// Reshow the Editor (see below)
				if(dojo.render.html.moz){
					var storageValue = dojo.byId("storageValue");
					storageValue.style.display = "block";
				}
			}
			
			// The Flash dialog plus the underlying Editor on Firefox
			// creates screen glitches; temporary
			// workaround to just hide the Editors while dialog is showing
			if(dojo.render.html.moz){
				var storageValue = dojo.byId("storageValue");
				storageValue.style.display = "none";
			}
			
			// show the dialog
			dojo.storage.showSettingsUI();
		}
	},
	
	_save: function(key, value){
		this._printStatus("Saving '" + key + "'...");
		var self = this;
		var saveHandler = function(status, keyName){
			if(status == dojo.storage.PENDING){
				// The Flash dialog plus the underlying Editor on Firefox
				// creates screen glitches; temporary
				// workaround to just hide the Editors while dialog is showing
				if(dojo.render.html.moz){
					var storageValue = dojo.byId("storageValue");
					storageValue.style.display = "none";
				}
				
				return;
			}
			
			if(status == dojo.storage.FAILED){
				alert("You do not have permission to store data for this web site. "
			        + "Press the Configure button to grant permission.");
			}else if(status == dojo.storage.SUCCESS){
				// clear out the old value
				dojo.byId("storageKey").value = "";
				dojo.byId("storageValue").value = "";
				self._printStatus("Saved '" + key + "'");
				
				// update the list of available keys
				// put this on a slight timeout, because saveHandler is called back
				// from Flash, which can cause problems in Flash 8 communication
				// which affects Safari
				// FIXME: Find out what is going on in the Flash 8 layer and fix it
				// there
				window.setTimeout(function(){ self._printAvailableKeys() }, 1);
			}
			
			// Reshow the Editor (see below)
			if(dojo.render.html.moz){
				var storageValue = dojo.byId("storageValue");
				storageValue.style.display = "block";
			}
		};
		try{
			dojo.storage.put(key, value, saveHandler);
		}catch(exp){
			alert(exp);
		}
	},
	
	_printAvailableKeys: function(){
		var directory = dojo.byId("directory");
		
		// clear out any old keys
		directory.innerHTML = "";
		
		// add a blank selection
		var optionNode = document.createElement("option");
		optionNode.appendChild(document.createTextNode(""));
		optionNode.value = "";
		directory.appendChild(optionNode);
		
		// add new ones
		var availableKeys = dojo.storage.getKeys();
		for (var i = 0; i < availableKeys.length; i++) {
			var optionNode = document.createElement("option");
			optionNode.appendChild(document.createTextNode(availableKeys[i]));
			optionNode.value = availableKeys[i];
			directory.appendChild(optionNode);
		}
	},
	
	_handleLoad: function(key){
		this._printStatus("Loading '" + key + "'...");
		
		// get the value
		var results = dojo.storage.get(key);
		
		// set the new Editor widget value
		var richTextControl = dojo.widget.byId("storageValue")
		richTextControl.replaceEditorContent(results);
		// FIXME: Editor2 should be reflowing this height
		// internally; we shouldn't be exposed to this - fix
		// bug in Editor2
		richTextControl._updateHeight();
	
		// print out that we are done
		this._printStatus("Loaded '" + key + "'");
	},
	
	_printStatus: function(message){
		// remove the old status
		var top = dojo.byId("top");
		for (var i = 0; i < top.childNodes.length; i++) {
			var currentNode = top.childNodes[i];
			if (currentNode.nodeType == dojo.dom.ELEMENT_NODE &&
					currentNode.className == "status") {
				top.removeChild(currentNode);
			}		
		}
		
		var status = document.createElement("span");
		status.className = "status";
		status.innerHTML = message;
		
		top.appendChild(status);
		dojo.lfx.fadeOut(status, 2000).play();
	}
};

// wait until the storage system is finished loading
if(dojo.storage.manager.isInitialized() == false){ // storage might already be loaded when we get here
	dojo.event.connect(dojo.storage.manager, "loaded", Moxie, 
	                  Moxie.initialize);
}else{
	dojo.event.connect(dojo, "loaded", Moxie, Moxie.initialize);
}
