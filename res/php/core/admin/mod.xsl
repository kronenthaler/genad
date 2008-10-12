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
	<xsl:include href="modules.php"/><!-- change the extension for others languages -->

 	<xsl:template match="/">
 		<html><head>
				<xsl:if test="/entity/basepath != ''">
					<base id="base" href="{/entity/basepath}" />
				</xsl:if>
				<xsl:if test="/error/basepath != ''">
					<base id="base" href="{/error/basepath}" />
				</xsl:if>
				<link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
				<link href="../css/ui.all.css" rel="stylesheet" type="text/css"/>
				<script language="javascript" src="../js/utils.js"></script>
				<script language="javascript" src="../js/jquery.js"></script>
				<script language="javascript" src="../js/ui.core.js"></script>
				<script language="javascript" src="../js/ui.datepicker.js"></script>
				<script language="javascript" src="../js/ui.timepicker.js"></script>
                <script language="javascript" src="../js/jsXMLParser/xmldom.js"></script>
				<script language="javascript" src="../js/fckeditor/fckeditor.js"></script> 				
                <script language="javascript" src="js/validators.js"></script>
				<xsl:apply-templates select="/entity/prefix"/><!-- trick to add the modules scripts prefix always exists-->
 				</head><body><div id="container"><div id="center">
		<xsl:choose>
			<xsl:when test="/error != ''">
				<xsl:apply-templates select="/error"/>
			</xsl:when>
			<xsl:otherwise>
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
			</xsl:otherwise>
		</xsl:choose>
		</div></div></body></html>
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
		<div id="content">
			<form name="frm_{//@name}"
				  id="frm_{//@name}" 
				  action="{/entity/prefix}exec{//@name}.{//@ext}"
				  method="POST" 
				  enctype="multipart/form-data">
				<center>
					<table cellpadding="0" cellspacing="0" border="0" style="border-left: 1px solid #ccc; border-right: 1px solid #ccc;">
						<tr><th width="15%" colspan="2"><xsl:value-of select="/entity/title"/></th></tr>
						<xsl:apply-templates/>
					</table>
					<table>
						<tr>
							<td align="left" class="plain" id="id">
								<button type="button" onclick="javascript:getAndTransform('{/entity/prefix}list{//@name}.{//@ext}?{$ids}','','');">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td><img src="images/cancel.png" align="left"/></td>
											<td align="right">Cancel</td>
										</tr>
									</table>
								</button>
							</td>
							<td width="50%" align="right" class="plain">
								<button type="submit" onclick="return validate{//@name}(document.forms.frm_{//@name}) &amp;&amp; upload.uploadFiles();">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td><img src="images/accept.png" align="left"/></td>
											<td align="right">Apply</td>
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
 	
	<xsl:template match="error">
		<div id="content">
			<center>
				<table height="50%">
					<tr height="100%" valign="top">
						<td width="100" class="plain"><img src="images/forbidden.png" style="background: #fff;border:0px;cursor: default;" /></td>
						<td class="plain">
							<h1>Forbidden!</h1>
							<xsl:value-of select="msg"/><br/>
							<!--a href="javascript: getAndTransform('{@href}','list.xsl','center');">Back</a-->
						</td>
					</tr>
				</table>
			</center>
		</div>
	</xsl:template>

 	<xsl:template match="textfield | integer | decimal | email">
 		<tr class="part1">
			<td class="part2" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left"><input type="text" name="str_{@map}" id="str_{@map}" value="{.}"/></td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="textarea">
 		<tr class="part1">
			<td class="part2 top" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left"><textarea name="str_{@map}" id="str_{@map}"><xsl:value-of select="."/></textarea></td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="hidden">
 		<input type="hidden" name="{@prefix}{@map}" id="{@prefix}{@map}" value="{.}"/>
 	</xsl:template>
 	 	
 	<xsl:template match="radio">
 		<tr class="part1">
			<td class="part2 top" align="right" ><xsl:value-of select="@name"/>:</td>
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
			<td class="part2 top" align="right" ><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<xsl:for-each select="option">
					<p>
					<xsl:choose>
						<xsl:when test="@selected = 'true'">
							<input type="checkbox" name="str_{../@map}[]" id="{../@map}{position()}" value="{@value}" checked="" onclick="{@onclick}"/>
						</xsl:when>
						<xsl:otherwise>
							<input type="checkbox" name="str_{../@map}[]" id="{../@map}{position()}" value="{@value}" onclick="{@onclick}"/>
						</xsl:otherwise>
					</xsl:choose>
					<label for="{../@map}{position()}"><xsl:value-of select="@name"/></label><br/>
					</p>
				</xsl:for-each>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="select">
 		<tr class="part1">
			<td class="part2" align="right" ><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<select name="str_{@map}" id="str_{@map}" onclick="{@onclick}">
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
 		<tr class="part1">
			<td class="part2" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left"><input type="password" name="str_{@map}" id="str_{@map}" value="{.}"/></td>
		</tr>
		<tr class="part1">
			<td class="part2" align="right"><xsl:value-of select="@confirm"/>:</td>
			<td class="part1" align="left"><input type="password" name="conf_str_{@map}" id="conf_str_{@map}" value=""/></td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="richtext">
 		<tr class="part1">
			<td class="part2 top" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<input type="hidden" name="str_{@map}" id="str_{@map}" />
				<textarea id="rte_{@map}"><xsl:value-of select="."/></textarea>
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
 		<tr class="part1">
			<td class="part2" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<input type="text" id="div_{@map}" value="{@date}"/>
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
							buttonImageOnly: true
						}).attr("readonly", "readonly");
						document.getElementById("<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>").value = convertDate('<xsl:value-of select="@date"/>','dd/MM/yyyy','yMd');
					});
				</script>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="time">
 		<tr class="part1">
			<td class="part2" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<input type="text" id="{@prefix}{@map}" value="{@time}" name="{@prefix}{@map}" size="5"/>
				<img src="images/timeIcon.gif" id="div_{@map}"/>
				<script language="javascript">
					$(function (){
						$('#div_<xsl:value-of select="@map"/>').timepicker({
							altField: '<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>',
						});
					});
				</script>
			</td>
		</tr>
 	</xsl:template>
 	
 	<xsl:template match="datetime">
 		<tr class="part1">
			<td class="part2" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<input type="text" id="div_{@map}_date" value="{@date}" size="10" />
				<input type="hidden" id="{@prefix}{@map}_date" name="{@prefix}{@map}_date" value="" />
				<input type="text" id="{@prefix}{@map}_time" value="{@time}" name="{@prefix}{@map}_time" size="5"/>
				<img src="images/timeIcon.gif" id="div_{@map}_time"/>
				
				<script language="javascript">
					$(function (){
						$("#div_<xsl:value-of select="@map"/>_date").datepicker({
							altField: "#<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_date",
							altFormat: "yymmdd",
							dateFormat: "dd/mm/yy",
							duration: "", 
							showOn: "both",
							buttonImage: "images/dateIcon.gif",
							buttonImageOnly: true
						}).attr("readonly", "readonly");
						document.getElementById("<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_date").value = convertDate('<xsl:value-of select="@date"/>','dd/MM/yyyy','yMd');

						$('#div_<xsl:value-of select="@map"/>_time').timepicker({
							altField: '<xsl:value-of select="@prefix"/><xsl:value-of select="@map"/>_time',
						});
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
 		<tr class="part1">
			<td class="part2 top" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
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
 		<tr class="part1">
			<td class="part2 top" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
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
		<tr class="part1">
			<td class="part2" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left"><xsl:if test=".=''">N/A</xsl:if><xsl:value-of select="."/></td>
		</tr>
	</xsl:template>

	<xsl:template match="timestamp">
		<tr class="part1">
			<td class="part2" align="right"><xsl:value-of select="@name"/>:</td>
			<td class="part1" align="left">
				<xsl:if test="@date=''">N/A</xsl:if><xsl:value-of select="@date"/>&nbsp;<xsl:value-of select="@time"/></td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
