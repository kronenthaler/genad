<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 	<xsl:output method="html" encoding="utf-8" indent="yes"/>
	<xsl:variable name="ids">
		<xsl:for-each select="/entity/ancestors/ancestor">&amp;<xsl:value-of select="@id"/>=<xsl:value-of select="@value"/></xsl:for-each>	
	</xsl:variable>
	
 	<xsl:template match="/">
 		<!--html><head><link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
				<script src="../js/dojo/dojo.js"></script>
				<script src="../js/tinymce/tiny_mce.js"></script>
 				<script src="../js/utils.js"></script>
 				<script src="../upload/js/upload.js"></script>
 				<script>dojo.require('dojo.widget.*');</script>
 				</head><body><div id="container"><div id="center"-->
		<script>var queue=Array();</script>
		<div id="content-header">
			<center>
				<table width="100%" border="1">
					<tr>
						<td valign="middle" align="left"><h2><xsl:value-of select="/entity/title"/></h2></td>
					</tr>
				</table>
			</center>
		</div>
		<xsl:apply-templates select="entity/fields"/>
		<!--/div></div></body></html-->
	</xsl:template>
 	
 	<xsl:template match="fields">
 		<script>
			var upload=new Upload('frm_<xsl:value-of select="//@name"/>');
 		</script>
		<div id="content">
			<form name="frm_{//@name}"
				  id="frm_{//@name}" 
				  action="exec{//@name}.{//@ext}"
				  method="post" 
				  enctype="multipart/form-data" 
				  >
				<center>
					<table cellpadding="0" cellspacing="0" border="0">
						<tr><th width="15%" colspan="2"><xsl:value-of select="/entity/title"/></th></tr>
						<xsl:apply-templates/>
					</table>
					<table class="plain" border="1">
						<tr>
							<td align="left" class="plain" id="id"><button type="button" onclick="getAndTransform('list{//@name}.{//@ext}?{$ids}','list.xsl','center')"><img src="images/cancel.png" align="left"/><span>Cancel</span></button></td>
							<td width="50%" align="right" class="plain"><button type="submit" onclick="return validate{//@name}(document.forms.frm_{//@name}) &amp;&amp; upload.uploadFiles();"><img src="images/accept.png" align="left"/><span>Apply</span></button></td>
						</tr>
					</table>
				</center>
				<input type="hidden" name="action" value="{//@action}"/>
				<input type="hidden" name="id" value="{//@id}"/>
				<xsl:for-each select="/entity/ancestors/ancestor">
					<input type="hidden" name="str_{@id}" id="str_{@id}" value="{@value}"/>
					<input type="hidden" name="{@id}" id="{@id}" value="{@value}"/>
				</xsl:for-each>	
			</form>
			<script>
				dojo.addOnLoad(function (){
					var x = new dojo.io.FormBind({
						formNode: document.forms.frm_<xsl:value-of select="//@name"/>,
						error: function(type,error){ prompt("",type+" : "+error.message);},
						handle: function(type, data, evt){ alert(type+"/"+data+"/"+evt); },
						load: function(load, data, evt){ transform(evt.responseXML, 'list.xsl','center'); },
						mimetype: "text/html"
					});
					upload.formComp = x;
				});//*/
			</script>
		</div>
 	</xsl:template>
 	
 	<xsl:template match="textfield | integer | decimal | email">
 		<tr class="part1">
			<td class="part1" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left"><input type="text" name="str_{@map}" id="str_{@map}" value="{.}"/></td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="textarea">
 		<tr class="part1">
			<td class="part1" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left"><textarea name="str_{@map}" id="str_{@map}"><xsl:value-of select="."/></textarea></td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="hidden">
 		<input type="hidden" name="{@prefix}{@map}" id="{@prefix}{@map}" value="{.}"/>
 	</xsl:template>
 	 	
 	<xsl:template match="radio">
 		<tr class="part1">
			<td class="part1" align="right" ><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<xsl:for-each select="option">
					<xsl:choose>
						<xsl:when test="@selected = 'true'">
							<input type="radio" name="str_{../@map}" id="{../@map}{position()}" value="{@value}" checked="" onclick="{@onclick}"/>
						</xsl:when>
						<xsl:otherwise>
							<input type="radio" name="str_{../@map}" id="{../@map}{position()}" value="{@value}" onclick="{@onclick}"/>
						</xsl:otherwise>
					</xsl:choose>
					<label for="{../@map}{position()}"><xsl:value-of select="@name"/></label> &nbsp;
				</xsl:for-each>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="checkbox">
 		<tr class="part1">
			<td class="part1" align="right" ><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<xsl:for-each select="option">
					<xsl:choose>
						<xsl:when test="@selected = 'true'">
							<input type="checkbox" name="str_{../@map}" id="{../@map}{position()}" value="{@value}" checked="" onclick="{@onclick}"/>
						</xsl:when>
						<xsl:otherwise>
							<input type="checkbox" name="str_{../@map}" id="{../@map}{position()}" value="{@value}" onclick="{@onclick}"/>
						</xsl:otherwise>
					</xsl:choose>
					<label for="{../@map}{position()}"><xsl:value-of select="@name"/></label><br/>
				</xsl:for-each>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="select">
 		<tr class="part1">
			<td class="part1" align="right" ><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<select name="str_{@map}" id="str_{@map}" onclick="{@onclick}">
					<xsl:for-each select="option">
						<xsl:choose>
							<xsl:when test="@selected = 'true'">
								<option id="{../@map}{position()}" value="{@value}" selected=""><xsl:value-of select="@name"/></option>
							</xsl:when>
							<xsl:otherwise>
								<option id="{../@map}{position()}" value="{@value}"><xsl:value-of select="@name"/></option>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:for-each>
				</select>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="password">
 		<tr class="part1">
			<td class="part1" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left"><input type="password" name="str_{@map}" id="str_{@map}" value="{.}"/></td>
		</tr>
		<tr class="part1">
			<td class="part1" align="right"><xsl:value-of select="@confirm"/>:</td>
			<td class="part1" align="left"><input type="password" name="conf_str_{@map}" id="conf_str_{@map}" value=""/></td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="richtext">
 		<tr class="part1">
			<td class="part1" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<input type="hidden" name="str_{@map}" id="str_{@map}" />
				<div id="rte_{@map}"><xsl:value-of select="."/></div>
				<script type="text/javascript">
					dojo.addOnLoad(function(){
						queue['rte_<xsl:value-of select="@map"/>']=1;
						tinyMCE.execCommand('mceAddControl', true, 'rte_<xsl:value-of select="@map"/>');
						tinyMCE.setContent(tinyMCE.entityDecode(tinyMCE.getContent('rte_<xsl:value-of select="@map"/>')));
					});
				</script>
			</td>
		</tr>
	</xsl:template>
 	
 	<xsl:template match="date">
 		<tr class="part1">
			<td class="part1" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<div id="div_{@map}"/>
				<script>
					dojo.addOnLoad(function (){
						dojo.widget.createWidget('DropdownDatePicker',
							{id:'<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>',
							name:'<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>',
							value:'<xsl:value-of select="@date"/>',
							displayWeeks:'4',
							saveFormat:'yyyyMMdd',
							displayFormat:'dd/MM/yyyy'},
							document.getElementById('div_<xsl:value-of select="@map"/>'))
					});
				</script>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="time">
 		<tr class="part1">
			<td class="part1" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<div id="div_{@map}"/>
				<script>
					dojo.addOnLoad(function (){
						dojo.widget.createWidget('DropdownTimePicker',
							{id:'<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>',
							name:'<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>',
							value:'<xsl:value-of select="@time"/>',
							displayFormat:'HH:mm'},
							document.getElementById('div_<xsl:value-of select="@map"/>'))
					});
				</script>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="datetime">
 		<tr class="part1">
			<td class="part1" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<div id="div_{@map}_date"/>
				<div id="div_{@map}_time"/>
				<script>
					dojo.addOnLoad(function (){
						dojo.widget.createWidget('DropdownDatePicker',
							{id:'<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_date',
							name:'<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_date',
							value:'<xsl:value-of select="@date"/>',
							displayWeeks:'4',
							saveFormat:'yyyyMMdd',
							displayFormat:'dd/MM/yyyy'},
							document.getElementById('div_<xsl:value-of select="@map"/>_date'))
						dojo.widget.createWidget('DropdownTimePicker',
							{id:'<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_time',
							name:'<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_time',
							value:'<xsl:value-of select="@time"/>',
							displayFormat:'HH:mm'},
							document.getElementById('div_<xsl:value-of select="@map"/>_time'))
					});
				</script>
				<input type="hidden" name="{@prefix}{@map}" id="{@prefix}{@map}" value="."/>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="file">
 		<xsl:variable name="params">
 		<xsl:for-each select="option">&amp;<xsl:value-of select="@name"/>=<xsl:value-of select="@value"/></xsl:for-each>
		</xsl:variable>
		<xsl:variable name="prev">
			<xsl:for-each select="option">
				<xsl:if test="@name = 'prev'"><xsl:value-of select="@value"/></xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<script>
			dojo.addOnLoad(function(){
				upload.addFile('<xsl:value-of select="@map"/>');
			});
		</script>
 		<tr class="part1">
			<td class="part1" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<img src="../upload/images/spacer.gif" id="loading_{@map}" align="absmiddle" style="display:none"/>
				<xsl:if test="$prev != ''">
				<a href="../{$prev}" target="_blank">View File</a>
				</xsl:if>
				<iframe id="if_{@map}" name="if_{@map}" src="../upload/component.php?name={@map}{$params}" scrolling="no" frameborder="0" width="100%" height="25px"/>
				<!--input type="file" name="file_{@map}" id="file_{@map}" onclick="dojo.byId('str_{@map}').value='changed';"/-->
				<input type="hidden" name="str_{@map}" id="str_{@map}"/>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="image">
 	</xsl:template>
</xsl:stylesheet>
