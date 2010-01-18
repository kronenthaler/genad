<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 	<xsl:output method="html" encoding="utf-8" indent="yes"/>
	
	<xsl:include href="localize.xsl"/>
	<xsl:include href="mod.xsl"/>
	<xsl:include href="common.xsl"/>
	
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
 				</head><body><div id="container"><div id="center">
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
					<!--center>
						<table width="100%" border="1">
							<tr>
								<td valign="middle" align="left"><h2><xsl:value-of select="/entity/title"/></h2></td>
							</tr>
						</table>
					</center-->
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
		<ul id="relation" style="padding-right: 30px;">
			<xsl:for-each select="/entity/relations/relation">
				<xsl:choose>
					<xsl:when test="@active = 'selected'">
						<li><a class="ui-state-active ui-corner-tl ui-corner-tr"
								onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-active')"
								onmouseout="$(this).addClass('ui-state-active').removeClass('ui-state-hover')"
								href="{/entity/prefix}list{@class}.{//@ext}?rel_id={//@rel_id}&amp;class={@class}&amp;currentClass={@currentClass}&amp;{$ids}">
								<xsl:value-of select="."/>:&nbsp;
								<xsl:if test="//@action = 'add'">
									<xsl:call-template name="msg-adding"/>
								</xsl:if>
								<xsl:if test="//@action = 'mod'">
									<xsl:call-template name="msg-editing"/>
								</xsl:if>
							</a>
						</li>
					</xsl:when>
					<xsl:otherwise>
						<li><a class="ui-state-default ui-corner-tl ui-corner-tr"
							onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
							onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
							href="{/entity/prefix}list{@class}.{//@ext}?rel_id={//@rel_id}&amp;class={@class}&amp;currentClass={@currentClass}&amp;{$ids}"><xsl:value-of select="."/>
							</a>
						</li>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
			<li><a class="ui-state-default ui-corner-tl ui-corner-tr"
					onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
					onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
					href="{/entity/prefix}mod{//@currentClass}.{//@ext}?action=mod&amp;id={//@rel_id}&amp;{$ids}">
						<xsl:call-template name="msg-edit"/>&nbsp;<xsl:value-of select="/entity/title"/>
					</a>
			</li>
		</ul>
		<div id="content">
			<form name="frm_{//@name}"
				  id="frm_{//@name}" 
				  action="{/entity/prefix}exec{//@name}.{//@ext}"
				  method="POST" 
				  enctype="multipart/form-data">
				<center>
					<table cellpadding="0" cellspacing="0" border="0">
						<tr class="ui-state-active ui-corner-all"><th colspan="2">&nbsp;<!--<xsl:value-of select="/entity/title"/>--></th></tr>
						<xsl:apply-templates/>
					</table>
					<table>
						<tr>
							<td align="left" class="plain" id="id">
								<button type="button" 
										class="ui-state-default ui-corner-all"
										onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
										onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
										onclick="javascript:getAndTransform('{/entity/prefix}list{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@rel_id}&amp;{$ids}','','');">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td><img src="images/cancel.png" align="left"/></td>
											<td align="right"><xsl:call-template name="msg-cancel"/></td>
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
											<td><img src="images/accept.png" align="left"/></td>
											<td align="right"><xsl:call-template name="msg-apply"/></td>
										</tr>
									</table>
								</button>
							</td>
						</tr>
					</table>
				</center>
				<input type="hidden" name="action" value="{//@action}"/>
				<input type="hidden" name="id" value="{//@id}"/>
				<input type="hidden" name="rel_id" value="{//@rel_id}"/>
				<input type="hidden" name="currentClass" value="{//@currentClass}"/>
				<xsl:for-each select="/entity/ancestors/ancestor">
					<input type="hidden" name="str_{@id}" id="str_{@id}" value="{@value}"/>
					<input type="hidden" name="{@id}" id="{@id}" value="{@value}"/>
				</xsl:for-each>
			</form>
		</div>
 	</xsl:template>
</xsl:stylesheet>
