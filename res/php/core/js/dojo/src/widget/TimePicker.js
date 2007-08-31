/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.provide("dojo.widget.TimePicker");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.HtmlWidget");
dojo.require("dojo.event.*");
dojo.require("dojo.date.serialize");
dojo.require("dojo.date.format");
dojo.require("dojo.dom");
dojo.require("dojo.html.style");
dojo.requireLocalization("dojo.i18n.calendar", "gregorian", null, "ko,zh-cn,zh,sv,ja,en,zh-tw,it,hu,nl,fi,zh-hk,fr,pt,ROOT,es,de,pt-br");
dojo.requireLocalization("dojo.widget", "TimePicker", null, "ROOT");
dojo.widget.defineWidget("dojo.widget.TimePicker", dojo.widget.HtmlWidget, function () {
	this.time = "";
	this.useDefaultTime = false;
	this.useDefaultMinutes = false;
	this.storedTime = "";
	this.currentTime = {};
	this.classNames = {selectedTime:"selectedItem"};
	this.any = "any";
	this.selectedTime = {hour:"", minute:"", amPm:"", anyTime:false};
	this.hourIndexMap = ["", 2, 4, 6, 8, 10, 1, 3, 5, 7, 9, 11, 0];
	this.minuteIndexMap = [0, 2, 4, 6, 8, 10, 1, 3, 5, 7, 9, 11];
}, {isContainer:false, templateString:"<div class=\"timePickerContainer\" dojoAttachPoint=\"timePickerContainerNode\"><table class=\"timeContainer\" cellspacing=\"0\" ><thead><tr><td class=\"timeCorner cornerTopLeft\" valign=\"top\"></td><td class=\"timeLabelContainer hourSelector\">${this.calendar.field-hour}</td><td class=\"timeLabelContainer minutesHeading\">${this.calendar.field-minute}</td><td class=\"timeCorner cornerTopRight\" valign=\"top\"></td></tr></thead><tbody><tr><td valign=\"top\" colspan=\"2\" class=\"hours\"><table align=\"center\"><tbody dojoAttachPoint=\"hourContainerNode\"  dojoAttachEvent=\"onClick: onSetSelectedHour;\"><tr><td>12</td><td>6</td></tr><tr><td>1</td><td>7</td></tr><tr><td>2</td><td>8</td></tr><tr><td>3</td><td>9</td></tr><tr><td>4</td><td>10</td></tr><tr><td>5</td><td>11</td></tr></tbody></table></td><td valign=\"top\" class=\"minutes\" colspan=\"2\"><table align=\"center\"><tbody dojoAttachPoint=\"minuteContainerNode\" dojoAttachEvent=\"onClick: onSetSelectedMinute;\"><tr><td>00</td><td>30</td></tr><tr><td>05</td><td>35</td></tr><tr><td>10</td><td>40</td></tr><tr><td>15</td><td>45</td></tr><tr><td>20</td><td>50</td></tr><tr><td>25</td><td>55</td></tr></tbody></table></td></tr><tr><td class=\"cornerBottomLeft\"></td><td valign=\"top\" class=\"timeOptions\"><table class=\"amPmContainer\"><tbody dojoAttachPoint=\"amPmContainerNode\" dojoAttachEvent=\"onClick: onSetSelectedAmPm;\"><tr><td id=\"am\">${this.calendar.am}</td><td id=\"pm\">${this.calendar.pm}</td></tr></tbody></table></td><td class=\"timeOptions\"><div dojoAttachPoint=\"anyTimeContainerNode\" dojoAttachEvent=\"onClick: onSetSelectedAnyTime;\" class=\"anyTimeContainer\">${this.widgetStrings.any}</div></td><td class=\"cornerBottomRight\"></td></tr></tbody></table></div>", templateCssString:"/*Time Picker */.timePickerContainer {width:122px;font-family:Tahoma, Myriad, Helvetica, Arial, Verdana, sans-serif;font-size:16px;}.timeContainer {border-collapse:collapse;border-spacing:0;}.timeContainer thead {color:#7e7e7e;font-size:0.9em;font-weight:700;}.timeContainer thead td {padding:0.25em;font-size:0.80em;border-bottom:1px solid #7e7e7e;}.timeCorner {width:10px;}.cornerTopLeft {background: url(\"images/dpCurveTL.png\") top left no-repeat;}.cornerTopRight {background: url(\"images/dpCurveTR.png\") top right no-repeat;}.timeLabelContainer {background: url(\"images/dpMonthBg.png\") top left repeat-x;}.hours, .minutes, .timeBorder {background: #8d8d8d url(\"images/dpBg.gif\") top left repeat-x;}.hours td, .minutes td {padding:0.2em;text-align:center;font-size:0.7em;font-weight:bold;cursor:pointer;cursor:hand;color:#fff;}.minutes {border-left:1px solid #f5d1db;}.hours {border-right:1px solid #7e7e7e;}.hourSelector {border-right:1px solid #7e7e7e;padding:5px;padding-right:10px;}.minutesSelector {padding:5px;border-left:1px solid #f5c7d4;text-align:center;}.minutesHeading {padding-left:9px !important;}.timeOptions {background-color:#F9C9D7;}.timeContainer .cornerBottomLeft, .timeContainer .cornerBottomRight, .timeContainer .timeOptions {border-top:1px solid #7e7e7e;}.timeContainer .cornerBottomLeft {background: url(\"images/dpCurveBL.png\") bottom left no-repeat !important;width:9px !important;padding:0;margin:0;}.timeContainer .cornerBottomRight {background: url(\"images/dpCurveBR.png\") bottom right no-repeat !important;width:9px !important;padding:0;margin:0;}.timeOptions {color:#fff;background:url(\"images/dpYearBg.png\") top left repeat-x;}.selectedItem {background-color:#fff;color:#7e7e7e !important;}.timeOptions .selectedItem {color:#fff !important;background-color:#bebebe !important;}.anyTimeContainer {text-align:center;font-weight:bold;font-size:0.7em;padding:0.1em;cursor:pointer;cursor:hand;color:#fff !important;}.amPmContainer {width:100%;}.amPmContainer td {text-align:center;font-size:0.7em;font-weight:bold;cursor:pointer;cursor:hand;color:#fff;}/*.timePickerContainer {margin:1.75em 0 0.5em 0;width:10em;float:left;}.timeContainer {border-collapse:collapse;border-spacing:0;}.timeContainer thead td{border-bottom:1px solid #e6e6e6;padding:0 0.4em 0.2em 0.4em;}.timeContainer td {font-size:0.9em;padding:0 0.25em 0 0.25em;text-align:left;cursor:pointer;cursor:hand;}.timeContainer td.minutesHeading {border-left:1px solid #e6e6e6;border-right:1px solid #e6e6e6;}.timeContainer .minutes {border-left:1px solid #e6e6e6;border-right:1px solid #e6e6e6;}.selectedItem {background-color:#3a3a3a;color:#ffffff;}*/", templateCssPath:dojo.uri.moduleUri("dojo.widget", "templates/TimePicker.css"), postMixInProperties:function (localProperties, frag) {
	dojo.widget.TimePicker.superclass.postMixInProperties.apply(this, arguments);
	this.calendar = dojo.i18n.getLocalization("dojo.i18n.calendar", "gregorian", this.lang);
	this.widgetStrings = dojo.i18n.getLocalization("dojo.widget", "TimePicker", this.lang);
}, fillInTemplate:function (args, frag) {
	var source = this.getFragNodeRef(frag);
	dojo.html.copyStyle(this.domNode, source);
	if (args.value) {
		if (args.value instanceof Date) {
			this.storedTime = dojo.date.toRfc3339(args.value);
		} else {
			this.storedTime = args.value;
		}
	}
	this.initData();
	this.initUI();
}, initData:function () {
	if (this.storedTime.indexOf("T") != -1 && this.storedTime.split("T")[1] && this.storedTime != " " && this.storedTime.split("T")[1] != "any") {
		this.time = dojo.widget.TimePicker.util.fromRfcDateTime(this.storedTime, this.useDefaultMinutes, this.selectedTime.anyTime);
	} else {
		if (this.useDefaultTime) {
			this.time = dojo.widget.TimePicker.util.fromRfcDateTime("", this.useDefaultMinutes, this.selectedTime.anyTime);
		} else {
			this.selectedTime.anyTime = true;
			this.time = dojo.widget.TimePicker.util.fromRfcDateTime("", 0, 1);
		}
	}
}, initUI:function () {
	if (!this.selectedTime.anyTime && this.time) {
		var amPmHour = dojo.widget.TimePicker.util.toAmPmHour(this.time.getHours());
		var hour = amPmHour[0];
		var isAm = amPmHour[1];
		var minute = this.time.getMinutes();
		var minuteIndex = parseInt(minute / 5);
		this.onSetSelectedHour(this.hourIndexMap[hour]);
		this.onSetSelectedMinute(this.minuteIndexMap[minuteIndex]);
		this.onSetSelectedAmPm(isAm);
	} else {
		this.onSetSelectedAnyTime();
	}
}, setTime:function (date) {
	if (date) {
		this.selectedTime.anyTime = false;
		this.setDateTime(dojo.date.toRfc3339(date));
	} else {
		this.selectedTime.anyTime = true;
	}
	this.initData();
	this.initUI();
}, setDateTime:function (rfcDate) {
	this.storedTime = rfcDate;
}, onClearSelectedHour:function (evt) {
	this.clearSelectedHour();
}, onClearSelectedMinute:function (evt) {
	this.clearSelectedMinute();
}, onClearSelectedAmPm:function (evt) {
	this.clearSelectedAmPm();
}, onClearSelectedAnyTime:function (evt) {
	this.clearSelectedAnyTime();
	if (this.selectedTime.anyTime) {
		this.selectedTime.anyTime = false;
		this.time = dojo.widget.TimePicker.util.fromRfcDateTime("", this.useDefaultMinutes);
		this.initUI();
	}
}, clearSelectedHour:function () {
	var hourNodes = this.hourContainerNode.getElementsByTagName("td");
	for (var i = 0; i < hourNodes.length; i++) {
		dojo.html.setClass(hourNodes.item(i), "");
	}
}, clearSelectedMinute:function () {
	var minuteNodes = this.minuteContainerNode.getElementsByTagName("td");
	for (var i = 0; i < minuteNodes.length; i++) {
		dojo.html.setClass(minuteNodes.item(i), "");
	}
}, clearSelectedAmPm:function () {
	var amPmNodes = this.amPmContainerNode.getElementsByTagName("td");
	for (var i = 0; i < amPmNodes.length; i++) {
		dojo.html.setClass(amPmNodes.item(i), "");
	}
}, clearSelectedAnyTime:function () {
	dojo.html.setClass(this.anyTimeContainerNode, "anyTimeContainer");
}, onSetSelectedHour:function (evt) {
	this.onClearSelectedAnyTime();
	this.onClearSelectedHour();
	this.setSelectedHour(evt);
	this.onSetTime();
}, setSelectedHour:function (evt) {
	if (evt && evt.target) {
		if (evt.target.nodeType == dojo.dom.ELEMENT_NODE) {
			var eventTarget = evt.target;
		} else {
			var eventTarget = evt.target.parentNode;
		}
		dojo.event.browser.stopEvent(evt);
		dojo.html.setClass(eventTarget, this.classNames.selectedTime);
		this.selectedTime["hour"] = eventTarget.innerHTML;
	} else {
		if (!isNaN(evt)) {
			var hourNodes = this.hourContainerNode.getElementsByTagName("td");
			if (hourNodes.item(evt)) {
				dojo.html.setClass(hourNodes.item(evt), this.classNames.selectedTime);
				this.selectedTime["hour"] = hourNodes.item(evt).innerHTML;
			}
		}
	}
	this.selectedTime.anyTime = false;
}, onSetSelectedMinute:function (evt) {
	this.onClearSelectedAnyTime();
	this.onClearSelectedMinute();
	this.setSelectedMinute(evt);
	this.selectedTime.anyTime = false;
	this.onSetTime();
}, setSelectedMinute:function (evt) {
	if (evt && evt.target) {
		if (evt.target.nodeType == dojo.dom.ELEMENT_NODE) {
			var eventTarget = evt.target;
		} else {
			var eventTarget = evt.target.parentNode;
		}
		dojo.event.browser.stopEvent(evt);
		dojo.html.setClass(eventTarget, this.classNames.selectedTime);
		this.selectedTime["minute"] = eventTarget.innerHTML;
	} else {
		if (!isNaN(evt)) {
			var minuteNodes = this.minuteContainerNode.getElementsByTagName("td");
			if (minuteNodes.item(evt)) {
				dojo.html.setClass(minuteNodes.item(evt), this.classNames.selectedTime);
				this.selectedTime["minute"] = minuteNodes.item(evt).innerHTML;
			}
		}
	}
}, onSetSelectedAmPm:function (evt) {
	this.onClearSelectedAnyTime();
	this.onClearSelectedAmPm();
	this.setSelectedAmPm(evt);
	this.selectedTime.anyTime = false;
	this.onSetTime();
}, setSelectedAmPm:function (evt) {
	var eventTarget = evt.target;
	if (evt && eventTarget) {
		if (eventTarget.nodeType != dojo.dom.ELEMENT_NODE) {
			eventTarget = eventTarget.parentNode;
		}
		dojo.event.browser.stopEvent(evt);
		this.selectedTime.amPm = eventTarget.id;
		dojo.html.setClass(eventTarget, this.classNames.selectedTime);
	} else {
		evt = evt ? 0 : 1;
		var amPmNodes = this.amPmContainerNode.getElementsByTagName("td");
		if (amPmNodes.item(evt)) {
			this.selectedTime.amPm = amPmNodes.item(evt).id;
			dojo.html.setClass(amPmNodes.item(evt), this.classNames.selectedTime);
		}
	}
}, onSetSelectedAnyTime:function (evt) {
	this.onClearSelectedHour();
	this.onClearSelectedMinute();
	this.onClearSelectedAmPm();
	this.setSelectedAnyTime();
	this.onSetTime();
}, setSelectedAnyTime:function (evt) {
	this.selectedTime.anyTime = true;
	dojo.html.setClass(this.anyTimeContainerNode, this.classNames.selectedTime + " " + "anyTimeContainer");
}, onClick:function (evt) {
	dojo.event.browser.stopEvent(evt);
}, onSetTime:function () {
	if (this.selectedTime.anyTime) {
		this.time = new Date();
		var tempDateTime = dojo.widget.TimePicker.util.toRfcDateTime(this.time);
		this.setDateTime(tempDateTime.split("T")[0]);
	} else {
		var hour = 12;
		var minute = 0;
		var isAm = false;
		if (this.selectedTime["hour"]) {
			hour = parseInt(this.selectedTime["hour"], 10);
		}
		if (this.selectedTime["minute"]) {
			minute = parseInt(this.selectedTime["minute"], 10);
		}
		if (this.selectedTime["amPm"]) {
			isAm = (this.selectedTime["amPm"].toLowerCase() == "am");
		}
		this.time = new Date();
		this.time.setHours(dojo.widget.TimePicker.util.fromAmPmHour(hour, isAm));
		this.time.setMinutes(minute);
		this.setDateTime(dojo.widget.TimePicker.util.toRfcDateTime(this.time));
	}
	this.onValueChanged(this.time);
}, onValueChanged:function (date) {
}});
dojo.widget.TimePicker.util = new function () {
	this.toRfcDateTime = function (jsDate) {
		if (!jsDate) {
			jsDate = new Date();
		}
		jsDate.setSeconds(0);
		return dojo.date.strftime(jsDate, "%Y-%m-%dT%H:%M:00%z");
	};
	this.fromRfcDateTime = function (rfcDate, useDefaultMinutes, isAnyTime) {
		var tempDate = new Date();
		if (!rfcDate || rfcDate.indexOf("T") == -1) {
			if (useDefaultMinutes) {
				tempDate.setMinutes(Math.floor(tempDate.getMinutes() / 5) * 5);
			} else {
				tempDate.setMinutes(0);
			}
		} else {
			var tempTime = rfcDate.split("T")[1].split(":");
			var tempDate = new Date();
			tempDate.setHours(tempTime[0]);
			tempDate.setMinutes(tempTime[1]);
		}
		return tempDate;
	};
	this.toAmPmHour = function (hour) {
		var amPmHour = hour;
		var isAm = true;
		if (amPmHour == 0) {
			amPmHour = 12;
		} else {
			if (amPmHour > 12) {
				amPmHour = amPmHour - 12;
				isAm = false;
			} else {
				if (amPmHour == 12) {
					isAm = false;
				}
			}
		}
		return [amPmHour, isAm];
	};
	this.fromAmPmHour = function (amPmHour, isAm) {
		var hour = parseInt(amPmHour, 10);
		if (isAm && hour == 12) {
			hour = 0;
		} else {
			if (!isAm && hour < 12) {
				hour = hour + 12;
			}
		}
		return hour;
	};
};

