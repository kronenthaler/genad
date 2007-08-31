/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.provide("dojo.widget.DatePicker");
dojo.require("dojo.date.common");
dojo.require("dojo.date.format");
dojo.require("dojo.date.serialize");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.HtmlWidget");
dojo.require("dojo.event.*");
dojo.require("dojo.dom");
dojo.require("dojo.html.style");
dojo.widget.defineWidget("dojo.widget.DatePicker", dojo.widget.HtmlWidget, {value:"", name:"", displayWeeks:6, adjustWeeks:false, startDate:"1492-10-12", endDate:"2941-10-12", weekStartsOn:"", staticDisplay:false, dayWidth:"narrow", classNames:{previous:"previousMonth", disabledPrevious:"previousMonthDisabled", current:"currentMonth", disabledCurrent:"currentMonthDisabled", next:"nextMonth", disabledNext:"nextMonthDisabled", currentDate:"currentDate", selectedDate:"selectedDate"}, templateString:"<div class=\"datePickerContainer\" dojoAttachPoint=\"datePickerContainerNode\"><table cellspacing=\"0\" cellpadding=\"0\" class=\"calendarContainer\"><thead><tr><td class=\"monthWrapper\" valign=\"top\"><table class=\"monthContainer\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td class=\"monthCurve monthCurveTL\" valign=\"top\"></td><td class=\"monthLabelContainer\" valign=\"top\"><span dojoAttachPoint=\"increaseWeekNode\" dojoAttachEvent=\"onClick: onIncrementWeek;\" class=\"incrementControl increase\"><img src=\"${dojoWidgetModuleUri}templates/images/incrementMonth.png\" alt=\"1\" style=\"width:7px;height:5px;\" /></span><span dojoAttachPoint=\"increaseMonthNode\" dojoAttachEvent=\"onClick: onIncrementMonth;\" class=\"incrementControl increase\"><img src=\"${dojoWidgetModuleUri}templates/images/incrementMonth.png\" alt=\"2\" dojoAttachPoint=\"incrementMonthImageNode\"/></span><span dojoAttachPoint=\"decreaseWeekNode\" dojoAttachEvent=\"onClick: onIncrementWeek;\" class=\"incrementControl decrease\"><img src=\"${dojoWidgetModuleUri}templates/images/decrementMonth.png\" alt=\"3\" style=\"width:7px;height:5px;\" /></span><span dojoAttachPoint=\"decreaseMonthNode\" dojoAttachEvent=\"onClick: onIncrementMonth;\" class=\"incrementControl decrease\"><img src=\"${dojoWidgetModuleUri}templates/images/decrementMonth.png\" alt=\"4\" dojoAttachPoint=\"decrementMonthImageNode\"/></span><span dojoAttachPoint=\"monthLabelNode\" class=\"month\"></span></td><td class=\"monthCurve monthCurveTR\" valign=\"top\"></td></tr></table></td></tr></thead><tbody><tr><td colspan=\"3\"><table class=\"calendarBodyContainer\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><thead><tr dojoAttachPoint=\"dayLabelsRow\"><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr></thead><tbody dojoAttachPoint=\"calendarDatesContainerNode\" dojoAttachEvent=\"onClick: _handleUiClick;\"><tr dojoAttachPoint=\"calendarWeekTemplate\"><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr></tbody></table></td></tr></tbody><tfoot><tr><td colspan=\"3\" class=\"yearWrapper\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"yearContainer\"><tr><td class=\"curveBL\" valign=\"top\"></td><td valign=\"top\"><h3 class=\"yearLabel\"><span dojoAttachPoint=\"previousYearLabelNode\" dojoAttachEvent=\"onClick: onIncrementYear;\" class=\"previousYear\"></span><span class=\"selectedYear\" dojoAttachPoint=\"currentYearLabelNode\"></span><span dojoAttachPoint=\"nextYearLabelNode\" dojoAttachEvent=\"onClick: onIncrementYear;\" class=\"nextYear\"></span></h3></td><td class=\"curveBR\" valign=\"top\"></td></tr></table></td></tr></tfoot></table></div>", templateCssString:".datePickerContainer {width:164px; /* needed for proper user styling */}.calendarContainer {/*border:1px solid #566f8f;*/}.calendarBodyContainer {width:100%; /* needed for the explode effect (explain?) */background: #8d8d8d url(\"images/dpBg.gif\") top left repeat-x;}.calendarBodyContainer thead tr td {color:#373737;font:bold 0.75em Helvetica, Arial, Verdana, sans-serif;text-align:center;padding:0.25em;background: url(\"images/dpHorizLine.gif\") bottom left repeat-x;}.calendarBodyContainer tbody tr td {color:#fff;font:bold 0.7em Helvetica, Arial, Verdana, sans-serif;text-align:center;padding:0.4em;background: url(\"images/dpVertLine.gif\") top right repeat-y;cursor:pointer;cursor:hand;}.monthWrapper {padding-bottom:2px;background: url(\"images/dpHorizLine.gif\") bottom left repeat-x;}.monthContainer {width:100%;}.monthLabelContainer {text-align:center;font:bold 0.75em Helvetica, Arial, Verdana, sans-serif;background: url(\"images/dpMonthBg.png\") repeat-x top left !important;color:#999999;padding:0.25em;}.monthCurve {width:12px;}.monthCurveTL {background: url(\"images/dpCurveTL.png\") no-repeat top left !important;}.monthCurveTR {background: url(\"images/dpCurveTR.png\") no-repeat top right !important;}.yearWrapper {background: url(\"images/dpHorizLineFoot.gif\") top left repeat-x;padding-top:2px;}.yearContainer {width:100%;}.yearContainer td {background:url(\"images/dpYearBg.png\") top left repeat-x;}.yearContainer .yearLabel {margin:0;padding:0.45em 0 0.45em 0;color:#fff;font:bold 0.75em Helvetica, Arial, Verdana, sans-serif;text-align:center;}.curveBL {background: url(\"images/dpCurveBL.png\") bottom left no-repeat !important;width:9px !important;padding:0;margin:0;}.curveBR {background: url(\"images/dpCurveBR.png\") bottom right no-repeat !important;width:9px !important;padding:0;margin:0;}.previousMonth {background-color:#7e7e7e !important;}.previousMonthDisabled {background-color:#a4a5a6 !important;cursor:default !important}.currentMonth {}.currentMonthDisabled {background-color:#bbbbbc !important;cursor:default !important}.nextMonth {background-color:#7e7e7e !important;}.nextMonthDisabled {background-color:#a4a5a6 !important;cursor:default !important;}.currentDate {text-decoration:underline;font-style:italic;}.selectedDate {background-color:#fff !important;color:#7e7e7e !important;}.yearLabel .selectedYear {padding:0.2em;background-color:#bebebe !important;}.nextYear, .previousYear {cursor:pointer;cursor:hand;padding:0;}.nextYear {margin:0 0 0 0.55em;}.previousYear {margin:0 0.55em 0 0;}.incrementControl {cursor:pointer;cursor:hand;width:1em;}.increase {float:right;}.decrease {float:left;}.lastColumn {background-image:none !important;}", templateCssPath:dojo.uri.moduleUri("dojo.widget", "templates/DatePicker.css"), postMixInProperties:function () {
	dojo.widget.DatePicker.superclass.postMixInProperties.apply(this, arguments);
	if (!this.weekStartsOn) {
		this.weekStartsOn = dojo.date.getFirstDayOfWeek(this.lang);
	}
	this.today = new Date();
	this.today.setHours(0, 0, 0, 0);
	if (typeof (this.value) == "string" && this.value.toLowerCase() == "today") {
		this.value = new Date();
	} else {
		if (this.value && (typeof this.value == "string") && (this.value.split("-").length > 2)) {
			this.value = dojo.date.fromRfc3339(this.value);
			this.value.setHours(0, 0, 0, 0);
		}
	}
}, fillInTemplate:function (args, frag) {
	dojo.widget.DatePicker.superclass.fillInTemplate.apply(this, arguments);
	var source = this.getFragNodeRef(frag);
	dojo.html.copyStyle(this.domNode, source);
	this.weekTemplate = dojo.dom.removeNode(this.calendarWeekTemplate);
	this._preInitUI(this.value ? this.value : this.today, false, true);
	var dayLabels = dojo.lang.unnest(dojo.date.getNames("days", this.dayWidth, "standAlone", this.lang));
	if (this.weekStartsOn > 0) {
		for (var i = 0; i < this.weekStartsOn; i++) {
			dayLabels.push(dayLabels.shift());
		}
	}
	var dayLabelNodes = this.dayLabelsRow.getElementsByTagName("td");
	for (i = 0; i < 7; i++) {
		dayLabelNodes.item(i).innerHTML = dayLabels[i];
	}
	if (this.value) {
		this.setValue(this.value);
	}
}, getValue:function () {
	return dojo.date.toRfc3339(new Date(this.value), "dateOnly");
}, getDate:function () {
	return this.value;
}, setValue:function (rfcDate) {
	this.setDate(rfcDate);
}, setDate:function (dateObj) {
	if (dateObj == "") {
		this.value = "";
		this._preInitUI(this.curMonth, false, true);
	} else {
		if (typeof dateObj == "string") {
			this.value = dojo.date.fromRfc3339(dateObj);
			this.value.setHours(0, 0, 0, 0);
		} else {
			this.value = new Date(dateObj);
			this.value.setHours(0, 0, 0, 0);
		}
	}
	if (this.selectedNode != null) {
		dojo.html.removeClass(this.selectedNode, this.classNames.selectedDate);
	}
	if (this.clickedNode != null) {
		dojo.debug("adding selectedDate");
		dojo.html.addClass(this.clickedNode, this.classNames.selectedDate);
		this.selectedNode = this.clickedNode;
	} else {
		this._preInitUI(this.value, false, true);
	}
	this.clickedNode = null;
	this.onValueChanged(this.value);
}, _preInitUI:function (dateObj, initFirst, initUI) {
	if (typeof (this.startDate) == "string") {
		this.startDate = dojo.date.fromRfc3339(this.startDate);
	}
	if (typeof (this.endDate) == "string") {
		this.endDate = dojo.date.fromRfc3339(this.endDate);
	}
	this.startDate.setHours(0, 0, 0, 0);
	this.endDate.setHours(24, 0, 0, -1);
	if (dateObj < this.startDate || dateObj > this.endDate) {
		dateObj = new Date((dateObj < this.startDate) ? this.startDate : this.endDate);
	}
	this.firstDay = this._initFirstDay(dateObj, initFirst);
	this.selectedIsUsed = false;
	this.currentIsUsed = false;
	var nextDate = new Date(this.firstDay);
	var tmpMonth = nextDate.getMonth();
	this.curMonth = new Date(nextDate);
	this.curMonth.setDate(nextDate.getDate() + 6);
	this.curMonth.setDate(1);
	if (this.displayWeeks == "" || this.adjustWeeks) {
		this.adjustWeeks = true;
		this.displayWeeks = Math.ceil((dojo.date.getDaysInMonth(this.curMonth) + this._getAdjustedDay(this.curMonth)) / 7);
	}
	var days = this.displayWeeks * 7;
	if (dojo.date.diff(this.startDate, this.endDate, dojo.date.dateParts.DAY) < days) {
		this.staticDisplay = true;
		if (dojo.date.diff(nextDate, this.endDate, dojo.date.dateParts.DAY) > days) {
			this._preInitUI(this.startDate, true, false);
			nextDate = new Date(this.firstDay);
		}
		this.curMonth = new Date(nextDate);
		this.curMonth.setDate(nextDate.getDate() + 6);
		this.curMonth.setDate(1);
		var curClass = (nextDate.getMonth() == this.curMonth.getMonth()) ? "current" : "previous";
	}
	if (initUI) {
		this._initUI(days);
	}
}, _initUI:function (days) {
	dojo.dom.removeChildren(this.calendarDatesContainerNode);
	for (var i = 0; i < this.displayWeeks; i++) {
		this.calendarDatesContainerNode.appendChild(this.weekTemplate.cloneNode(true));
	}
	var nextDate = new Date(this.firstDay);
	this._setMonthLabel(this.curMonth.getMonth());
	this._setYearLabels(this.curMonth.getFullYear());
	var calendarNodes = this.calendarDatesContainerNode.getElementsByTagName("td");
	var calendarRows = this.calendarDatesContainerNode.getElementsByTagName("tr");
	var currentCalendarNode;
	for (i = 0; i < days; i++) {
		currentCalendarNode = calendarNodes.item(i);
		currentCalendarNode.innerHTML = nextDate.getDate();
		currentCalendarNode.setAttribute("djDateValue", nextDate.valueOf());
		var curClass = (nextDate.getMonth() != this.curMonth.getMonth() && Number(nextDate) < Number(this.curMonth)) ? "previous" : (nextDate.getMonth() == this.curMonth.getMonth()) ? "current" : "next";
		var mappedClass = curClass;
		if (this._isDisabledDate(nextDate)) {
			var classMap = {previous:"disabledPrevious", current:"disabledCurrent", next:"disabledNext"};
			mappedClass = classMap[curClass];
		}
		dojo.html.setClass(currentCalendarNode, this._getDateClassName(nextDate, mappedClass));
		if (dojo.html.hasClass(currentCalendarNode, this.classNames.selectedDate)) {
			this.selectedNode = currentCalendarNode;
		}
		nextDate = dojo.date.add(nextDate, dojo.date.dateParts.DAY, 1);
	}
	this.lastDay = dojo.date.add(nextDate, dojo.date.dateParts.DAY, -1);
	this._initControls();
}, _initControls:function () {
	var d = this.firstDay;
	var d2 = this.lastDay;
	var decWeek, incWeek, decMonth, incMonth, decYear, incYear;
	decWeek = incWeek = decMonth = incMonth = decYear = incYear = !this.staticDisplay;
	with (dojo.date.dateParts) {
		var add = dojo.date.add;
		if (decWeek && add(d, DAY, (-1 * (this._getAdjustedDay(d) + 1))) < this.startDate) {
			decWeek = decMonth = decYear = false;
		}
		if (incWeek && d2 > this.endDate) {
			incWeek = incMonth = incYear = false;
		}
		if (decMonth && add(d, DAY, -1) < this.startDate) {
			decMonth = decYear = false;
		}
		if (incMonth && add(d2, DAY, 1) > this.endDate) {
			incMonth = incYear = false;
		}
		if (decYear && add(d2, YEAR, -1) < this.startDate) {
			decYear = false;
		}
		if (incYear && add(d, YEAR, 1) > this.endDate) {
			incYear = false;
		}
	}
	function enableControl(node, enabled) {
		dojo.html.setVisibility(node, enabled ? "" : "hidden");
	}
	enableControl(this.decreaseWeekNode, decWeek);
	enableControl(this.increaseWeekNode, incWeek);
	enableControl(this.decreaseMonthNode, decMonth);
	enableControl(this.increaseMonthNode, incMonth);
	enableControl(this.previousYearLabelNode, decYear);
	enableControl(this.nextYearLabelNode, incYear);
}, _incrementWeek:function (evt) {
	var d = new Date(this.firstDay);
	switch (evt.target) {
	  case this.increaseWeekNode.getElementsByTagName("img").item(0):
	  case this.increaseWeekNode:
		var tmpDate = dojo.date.add(d, dojo.date.dateParts.WEEK, 1);
		if (tmpDate < this.endDate) {
			d = dojo.date.add(d, dojo.date.dateParts.WEEK, 1);
		}
		break;
	  case this.decreaseWeekNode.getElementsByTagName("img").item(0):
	  case this.decreaseWeekNode:
		if (d >= this.startDate) {
			d = dojo.date.add(d, dojo.date.dateParts.WEEK, -1);
		}
		break;
	}
	this._preInitUI(d, true, true);
}, _incrementMonth:function (evt) {
	var d = new Date(this.curMonth);
	var tmpDate = new Date(this.firstDay);
	switch (evt.currentTarget) {
	  case this.increaseMonthNode.getElementsByTagName("img").item(0):
	  case this.increaseMonthNode:
		tmpDate = dojo.date.add(tmpDate, dojo.date.dateParts.DAY, this.displayWeeks * 7);
		if (tmpDate < this.endDate) {
			d = dojo.date.add(d, dojo.date.dateParts.MONTH, 1);
		} else {
			var revertToEndDate = true;
		}
		break;
	  case this.decreaseMonthNode.getElementsByTagName("img").item(0):
	  case this.decreaseMonthNode:
		if (tmpDate > this.startDate) {
			d = dojo.date.add(d, dojo.date.dateParts.MONTH, -1);
		} else {
			var revertToStartDate = true;
		}
		break;
	}
	if (revertToStartDate) {
		d = new Date(this.startDate);
	} else {
		if (revertToEndDate) {
			d = new Date(this.endDate);
		}
	}
	this._preInitUI(d, false, true);
}, _incrementYear:function (evt) {
	var year = this.curMonth.getFullYear();
	var tmpDate = new Date(this.firstDay);
	switch (evt.target) {
	  case this.nextYearLabelNode:
		tmpDate = dojo.date.add(tmpDate, dojo.date.dateParts.YEAR, 1);
		if (tmpDate < this.endDate) {
			year++;
		} else {
			var revertToEndDate = true;
		}
		break;
	  case this.previousYearLabelNode:
		tmpDate = dojo.date.add(tmpDate, dojo.date.dateParts.YEAR, -1);
		if (tmpDate > this.startDate) {
			year--;
		} else {
			var revertToStartDate = true;
		}
		break;
	}
	var d;
	if (revertToStartDate) {
		d = new Date(this.startDate);
	} else {
		if (revertToEndDate) {
			d = new Date(this.endDate);
		} else {
			d = new Date(year, this.curMonth.getMonth(), 1);
		}
	}
	this._preInitUI(d, false, true);
}, onIncrementWeek:function (evt) {
	evt.stopPropagation();
	if (!this.staticDisplay) {
		this._incrementWeek(evt);
	}
}, onIncrementMonth:function (evt) {
	evt.stopPropagation();
	if (!this.staticDisplay) {
		this._incrementMonth(evt);
	}
}, onIncrementYear:function (evt) {
	evt.stopPropagation();
	if (!this.staticDisplay) {
		this._incrementYear(evt);
	}
}, _setMonthLabel:function (monthIndex) {
	this.monthLabelNode.innerHTML = dojo.date.getNames("months", "wide", "standAlone", this.lang)[monthIndex];
}, _setYearLabels:function (year) {
	var y = year - 1;
	var that = this;
	function f(n) {
		that[n + "YearLabelNode"].innerHTML = dojo.date.format(new Date(y++, 0), {formatLength:"yearOnly", locale:that.lang});
	}
	f("previous");
	f("current");
	f("next");
}, _getDateClassName:function (date, monthState) {
	var currentClassName = this.classNames[monthState];
	if ((!this.selectedIsUsed && this.value) && (Number(date) == Number(this.value))) {
		currentClassName = this.classNames.selectedDate + " " + currentClassName;
		this.selectedIsUsed = true;
	}
	if ((!this.currentIsUsed) && (Number(date) == Number(this.today))) {
		currentClassName = currentClassName + " " + this.classNames.currentDate;
		this.currentIsUsed = true;
	}
	return currentClassName;
}, onClick:function (evt) {
	dojo.event.browser.stopEvent(evt);
}, _handleUiClick:function (evt) {
	var eventTarget = evt.target;
	if (eventTarget.nodeType != dojo.dom.ELEMENT_NODE) {
		eventTarget = eventTarget.parentNode;
	}
	dojo.event.browser.stopEvent(evt);
	this.selectedIsUsed = this.todayIsUsed = false;
	if (dojo.html.hasClass(eventTarget, this.classNames["disabledPrevious"]) || dojo.html.hasClass(eventTarget, this.classNames["disabledCurrent"]) || dojo.html.hasClass(eventTarget, this.classNames["disabledNext"])) {
		return;
	}
	this.clickedNode = eventTarget;
	this.setDate(new Date(Number(dojo.html.getAttribute(eventTarget, "djDateValue"))));
}, onValueChanged:function (date) {
}, _isDisabledDate:function (dateObj) {
	if (dateObj < this.startDate || dateObj > this.endDate) {
		return true;
	}
	return this.isDisabledDate(dateObj, this.lang);
}, isDisabledDate:function (dateObj, locale) {
	return false;
}, _initFirstDay:function (dateObj, adj) {
	var d = new Date(dateObj);
	if (!adj) {
		d.setDate(1);
	}
	d.setDate(d.getDate() - this._getAdjustedDay(d, this.weekStartsOn));
	d.setHours(0, 0, 0, 0);
	return d;
}, _getAdjustedDay:function (dateObj) {
	var days = [0, 1, 2, 3, 4, 5, 6];
	if (this.weekStartsOn > 0) {
		for (var i = 0; i < this.weekStartsOn; i++) {
			days.unshift(days.pop());
		}
	}
	return days[dateObj.getDay()];
}, destroy:function () {
	dojo.widget.DatePicker.superclass.destroy.apply(this, arguments);
	dojo.html.destroyNode(this.weekTemplate);
}});

