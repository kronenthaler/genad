<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 	<xsl:output method="html" encoding="utf-8" indent="yes"/>
	
	<xsl:include href="localize.xsl"/>
	<xsl:include href="common.xsl"/>

	<xsl:variable name="ids">
		<xsl:for-each select="/entity/ancestors/ancestor">&amp;<xsl:value-of select="@id"/>=<xsl:value-of select="@value"/></xsl:for-each>	
	</xsl:variable>
	
	<xsl:include href="modules.php"/><!-- change the extension for others languages -->

 	<xsl:template match="/">
 		<html>
			<head>
				<xsl:if test="/entity/basepath != ''">
					<base id="base" href="{/entity/basepath}" />
				</xsl:if>
				<xsl:if test="/error/basepath != ''">
					<base id="base" href="{/error/basepath}" />
				</xsl:if>
				<link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
				<link href="../admin/css/ui.css" rel="stylesheet" type="text/css"/>
				<script type="text/javascript" src= "../js/jquery.js"></script>
				<script type="text/javascript" src= "../js/jquery-ui.js"></script>
				<script type="text/javascript" src= "../js/jquery.form.js"></script>
				<script type="text/javascript" src= "../js/json.js"></script>
				<script type="text/javascript" src= "../js/utils.js"></script>
				<script type="text/javascript" src="../js/jsXMLParser/xmldom.js"></script>
				<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script>
				
                <script language="javascript" src="js/validators.js"></script>
				<xsl:apply-templates select="/entity/prefix"/><!-- trick to add the modules scripts prefix always exists-->
				<xsl:apply-templates select="/entity/validator"/>
 			</head>
			<body>
				<div id="container">
					<div id="center">
						<xsl:choose>
							<xsl:when test="/error != ''">
								<xsl:apply-templates select="/error"/>
							</xsl:when>
							<xsl:otherwise>
								<ul id="navigation">
									<xsl:for-each select="/entity/ancestors/ancestor">
										<li><a class="ui-state-default ui-corner-bl ui-corner-br"
												onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
												onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
												href="{/entity/prefix}list{@class}.{//@ext}?{$ids}"><xsl:value-of select="."/></a></li>
									</xsl:for-each>
									<li><a class="ui-state-active ui-corner-bl ui-corner-br"><xsl:value-of select="/entity/title"/></a></li>
								</ul>
								<div id="content-header">
								</div>
								<xsl:apply-templates select="entity/fields"/>
							</xsl:otherwise>
						</xsl:choose>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="fields">
 		<script>
			function sender(){
				var finalForm = document.getElementById("frm_<xsl:value-of select="//@name"/>");
				finalForm.onsubmit = null; //avoid revalidate
				finalForm.submit();
			}
			var upload=new Upload('frm_<xsl:value-of select="//@name"/>',sender);
 		</script>
		<ul id="relation" style="padding-right: 30px;">
			<xsl:for-each select="/entity/relations/relation">
				<li>
					<xsl:choose>
					<xsl:when test="//@action = 'add'">
						<a class="ui-state-default ui-corner-tl ui-corner-tr"><xsl:value-of select="."/></a>
					</xsl:when>
					<xsl:otherwise>
						<a class="ui-state-default ui-corner-tl ui-corner-tr"
							onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
							onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
							href="{/entity/prefix}list{@class}.{//@ext}?rel_id={//@id}&amp;class={@class}&amp;currentClass={@currentClass}&amp;{$ids}"><xsl:value-of select="."/></a>
					</xsl:otherwise>
					</xsl:choose>
				</li>
			</xsl:for-each>
			<li><a class="ui-state-active ui-corner-tl ui-corner-tr"><xsl:call-template name="msg-edit"/>&nbsp;<xsl:value-of select="/entity/title"/></a></li>
		</ul>
		<div id="content">
			<form name="frm_{//@name}"
				  id="frm_{//@name}" 
				  action="{/entity/prefix}exec{//@name}.{//@ext}"
				  method="POST" 
				  enctype="multipart/form-data">
				<center>
					<table cellpadding="0" cellspacing="0" border="0">
						<tr class="ui-state-default ui-corner-all"><th colspan="2">&nbsp;<!--<xsl:value-of select="/entity/title"/>--></th></tr>
						<xsl:apply-templates/>
					</table>
					<table>
						<tr>
							<td align="left" class="plain" id="id">
								<button type="button" 
										class="ui-state-default ui-corner-all"
										onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
										onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
										onclick="javascript:getAndTransform('{/entity/prefix}list{//@name}.{//@ext}?{$ids}','','');">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td><!--img src="images/cancel.png" align="left"/--><span class="ui-icon ui-icon-closethick"/></td>
											<td align="right"><a><xsl:call-template name="msg-cancel"/></a></td>
										</tr>
									</table>
								</button>
							</td>
							<td width="50%" align="right" class="plain">
								<button type="submit"
										class="ui-state-default ui-corner-all"
										onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
										onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
										onclick="return validate{//@name}(document.forms.frm_{//@name}) &amp;&amp; upload.uploadFiles();">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td><!--img src="images/accept.png" align="left"/--><span class="ui-icon ui-icon-check"/></td>
											<td align="right"><a><xsl:call-template name="msg-apply"/></a></td>
										</tr>
									</table>
								</button>
							</td>
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
		</div>
 	</xsl:template>

	<xsl:template match="textfield | integer | decimal | email">
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left"><input class="ui-widget ui-widget-content" type="text" name="str_{@map}" id="str_{@map}" value="{.}"/></td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="textarea">
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label top ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left"><textarea class="ui-widget ui-widget-content" name="str_{@map}" id="str_{@map}"><xsl:value-of select="."/></textarea></td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="hidden">
 		<input type="hidden" name="{@prefix}{@map}" id="{@prefix}{@map}" value="{.}"/>
 	</xsl:template>
 	 	
 	<xsl:template match="radio">
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label top ui-state-default" style="border-top:0px;background:#ffffff;" align="right" ><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<xsl:for-each select="option">
					<xsl:choose>
						<xsl:when test="@selected = 'true'">
							<input class="ui-widget ui-widget-content" type="radio" name="str_{../@map}" id="{../@map}{position()}" value="{@value}" checked="" onclick="{@onclick}"/>
						</xsl:when>
						<xsl:otherwise>
							<input class="ui-widget ui-widget-content" type="radio" name="str_{../@map}" id="{../@map}{position()}" value="{@value}" onclick="{@onclick}"/>
						</xsl:otherwise>
					</xsl:choose>
					<label class="ui-widget" for="{../@map}{position()}"><xsl:value-of select="@name"/></label> &nbsp;
				</xsl:for-each>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="checkbox">
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label top ui-state-default" style="border-top:0px;background:#ffffff;" align="right" ><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<xsl:for-each select="option">
					<p>
					<xsl:choose>
						<xsl:when test="@selected = 'true'">
							<input class="ui-widget ui-widget-content" type="checkbox" name="str_{../@map}[]" id="{../@map}{position()}" value="{@value}" checked="" onclick="{@onclick}"/>
						</xsl:when>
						<xsl:otherwise>
							<input class="ui-widget ui-widget-content" type="checkbox" name="str_{../@map}[]" id="{../@map}{position()}" value="{@value}" onclick="{@onclick}"/>
						</xsl:otherwise>
					</xsl:choose>
					<label class="ui-widget" for="{../@map}{position()}"><xsl:value-of select="@name"/></label><br/>
					</p>
				</xsl:for-each>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="select">
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label ui-state-default" style="border-top:0px;background:#ffffff;" align="right" ><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<select name="str_{@map}" id="str_{@map}" onclick="{@onclick}" class="ui-widget ui-widget-content">
					<xsl:for-each select="option">
						<xsl:choose>
							<xsl:when test="@selected = 'optgroup'">
								<optgroup label="{@value}" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:choose>
									<xsl:when test="@selected = 'true'">
										<option id="{../@map}{position()}" value="{@value}" selected=""><xsl:value-of select="@name"/></option>
									</xsl:when>
									<xsl:otherwise>
										<option id="{../@map}{position()}" value="{@value}"><xsl:value-of select="@name"/></option>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:for-each>
				</select>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="password">
 		<tr class="ui-state-default" style="border-top:0px;background:#ffffff;">
			<td class="label ui-state-default" style="border-top:0px; " align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left"><input class="ui-widget ui-widget-content" type="password" name="str_{@map}" id="str_{@map}" value="{.}"/></td>
		</tr>
		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label ui-state-default" style="border-top:0px; " align="right"><xsl:value-of select="@confirm"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left"><input class="ui-widget ui-widget-content" type="password" name="conf_str_{@map}" id="conf_str_{@map}" value=""/></td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="richtext">
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label top ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<input type="hidden" name="str_{@map}" id="str_{@map}" />
				<textarea id="rte_{@map}" class="ui-widget ui-widget-content"><xsl:value-of select="."/></textarea>
				<script type="text/javascript">
					//var div = document.getElementById("rte_<xsl:value-of select="@map"/>");
					var fck = new FCKeditor("rte_<xsl:value-of select="@map"/>");
					fck.BasePath = "../js/fckeditor/" ;
					fck.ToolbarSet = '<xsl:value-of select="@toolbar"/>';
					fck.ReplaceTextarea();
				</script>
			</td>
		</tr>
	</xsl:template>
 	
 	<xsl:template match="date">
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<input type="text" id="div_{@map}" value="{@date}" class="ui-widget ui-widget-content"/>
				<input type="hidden" id="{@prefix}{@map}" value="" name="{@prefix}{@map}" />
				<script language="javascript">
					$(function (){
						$("#div_<xsl:value-of select="@map"/>").datepicker({
							altField: "#<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>",
							altFormat: "yymmdd",
							dateFormat: "dd/mm/yy",
							duration: "", 
							showOn: "both",
							buttonImage: "images/dateIcon.gif",
							buttonImageOnly: true,
							changeMonth: true,
							changeYear: true,
							showButtonPanel: true
						}).attr("readonly", "readonly");
						document.getElementById("<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>").value = convertDate('<xsl:value-of select="@date"/>','dd/MM/yyyy','yMd');
					});
				</script>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="time">
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<input type="hidden" id="{@prefix}{@map}" value="{@time}" name="{@prefix}{@map}" size="5" class="ui-widget ui-widget-content"/>
				<select class="ui-widget ui-widget-content" name="hour_{@map}" id="hour_{@map}" onchange="updateTimeHidden(this,'{@prefix}{@map}',true)"></select>:
				<select class="ui-widget ui-widget-content" name="min_{@map}" id="min_{@map}" onchange="updateTimeHidden(this,'{@prefix}{@map}',false)"></select>
				<script language="javascript">
					/*$(function (){
						$('#div_<xsl:value-of select="@map"/>').timepicker({
							altField: '<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>',
						});
					});*/
					$(function(){
						fillTimePicker('<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>','<xsl:value-of select="@map"/>','<xsl:value-of select="@time"/>');
					});
				</script>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="datetime">
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<input type="text" id="div_{@map}_date" value="{@date}" size="10" class="ui-widget ui-widget-content"/>
				<input type="hidden" id="{@prefix}{@map}_date" name="{@prefix}{@map}_date" value="" />
				<input type="hidden" id="{@prefix}{@map}_time" value="{@time}" name="{@prefix}{@map}_time" size="5"/>
				&nbsp;
				<select class="ui-widget ui-widget-content" name="hour_{@map}" id="hour_{@map}" onchange="updateTimeHidden(this,'{@prefix}{@map}_time',true)"></select>&nbsp;:
				<select class="ui-widget ui-widget-content" name="min_{@map}" id="min_{@map}" onchange="updateTimeHidden(this,'{@prefix}{@map}_time',false)"></select>
				
				<script language="javascript">
					$(function (){
						$("#div_<xsl:value-of select="@map"/>_date").datepicker({
							altField: "#<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_date",
							altFormat: "yymmdd",
							dateFormat: "dd/mm/yy",
							duration: "", 
							showOn: "both",
							buttonImage: "images/dateIcon.gif",
							buttonImageOnly: true,
							changeMonth: true,
							changeYear: true,
							showButtonPanel: true
						}).attr("readonly", "readonly");
						document.getElementById("<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_date").value = convertDate('<xsl:value-of select="@date"/>','dd/MM/yyyy','yMd');

						fillTimePicker('<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_time','<xsl:value-of select="@map"/>','<xsl:value-of select="@time"/>');
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
			upload.addFile('<xsl:value-of select="@map"/>');
		</script>
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label top ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<xsl:if test="$prev != ''">
				<a href="../{$prev}" target="_blank" id="link_{@map}">View File</a>
				</xsl:if>
				<iframe id="if_{@map}" name="if_{@map}" 
						src="../upload/component.{//@ext}?name={@map}{$params}" 
						scrolling="no" 
						frameborder="0" 
						width="100%" 
						height="30px"/>
				<input type="hidden" name="str_{@map}" id="str_{@map}" value="{$prev}"/>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="image">
 		<xsl:variable name="params">
		<xsl:for-each select="option">&amp;<xsl:value-of select="@name"/>=<xsl:value-of select="@value"/></xsl:for-each>
		</xsl:variable>
		<xsl:variable name="prev">
			<xsl:for-each select="option">
				<xsl:if test="@name = 'prev'"><xsl:value-of select="@value"/></xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<xsl:variable name="iframe-width">
			<xsl:for-each select="option">
				<xsl:if test="@name = 'maxWidth'"><xsl:value-of select="@value"/></xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<xsl:variable name="iframe-height">
			<xsl:for-each select="option">
				<xsl:if test="@name = 'maxHeight'"><xsl:value-of select="@value"/></xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<script>
			upload.addFile('<xsl:value-of select="@map"/>');
		</script>
 		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label top ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<xsl:if test="$prev != ''">
				<a href="../{$prev}" target="_blank" id="link_{@map}">
					<img src="../{$prev}" border="0" id="img_link_{@map}"/></a><!--width="{$iframe-width}" height="{$iframe-height}"-->
				</xsl:if>
				<iframe id="if_{@map}" name="if_{@map}" 
						src="../upload/component.{//@ext}?name={@map}{$params}" 
						scrolling="no" 
						frameborder="0" 
						width="100%" 
						height="30px"/>
				<input type="hidden" name="str_{@map}" id="str_{@map}" value="{$prev}"/>
			</td>
		</tr>
 	</xsl:template>

	<xsl:template match="label">
		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-widget ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left"><xsl:if test=".=''">N/A</xsl:if><xsl:value-of select="."/></td>
		</tr>
	</xsl:template>

	<xsl:template match="keylabel">
		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-widget ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">&nbsp;<xsl:if test=".=''">N/A</xsl:if><xsl:value-of select="."/>
				<input type="hidden" name="{@prefix}{@map}" id="{@prefix}{@map}" value="{@value}"/>
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="timestamp">
		<tr class="ui-state-default" style="border-top:0px;">
			<td class="label ui-state-default" style="border-top:0px;background:#ffffff;" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="ui-widget ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;" align="left">
				<xsl:if test="@date=''">N/A</xsl:if><xsl:value-of select="@date"/>&nbsp;<xsl:value-of select="@time"/></td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
