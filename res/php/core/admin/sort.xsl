<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="utf-8"/>

	<xsl:include href="getResource.php?file=admin/common.xsl"/>

	<!-- define the ids of the ancestors required for each link that modifies the entity -->
 	<xsl:variable name="ids">
		<xsl:for-each select="/entity/ancestors/ancestor">&amp;<xsl:value-of select="@id"/>=<xsl:value-of select="@value"/></xsl:for-each>	
	</xsl:variable>
	<xsl:variable name="backids">
		<xsl:for-each select="/entity/back/ancestor">&amp;<xsl:value-of select="@id"/>=<xsl:value-of select="@value"/></xsl:for-each>
	</xsl:variable>
	
	<xsl:template match="/">
		<html><head>
				<xsl:if test="/entity/basepath">
					<base id="base" href="{/entity/basepath}" />
				</xsl:if>
				<xsl:if test="/error/basepath">
					<base id="base" href="{/error/basepath}" />
				</xsl:if>
				<link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
				<link href="../admin/css/ui.css" rel="stylesheet" type="text/css"/>
				<script type="text/javascript" src= "../js/jquery.js"></script>
				<script type="text/javascript" src= "../js/jquery-ui.js"></script>
				<script type="text/javascript" src= "../js/jquery.form.js"></script>
				<script type="text/javascript" src= "../js/json.js"></script>
				<script type="text/javascript" src= "getResource.php?file=js/utils.js"></script>
				</head><body><div id="container"><div id="center">
		<xsl:choose>
			<xsl:when test="/entity/error != ''">
				<xsl:apply-templates select="/entity/error"/>
			</xsl:when>
			<xsl:otherwise>
				<ul id="navigation">
					<xsl:for-each select="/entity/ancestors/ancestor">
						<li><a class="ui-state-default ui-corner-bl ui-corner-br"
								onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
								onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
								href="{/entity/prefix}list{@class}.{//@ext}?{$backids}"><xsl:value-of select="."/></a></li>
					</xsl:for-each>
					<li><a class="ui-state-active ui-corner-bl ui-corner-br"><xsl:value-of select="/entity/title"/></a></li>
				</ul>
				<div id="content-header" />
				<xsl:apply-templates select="entity/list"/>
			</xsl:otherwise>
		</xsl:choose>
		</div></div></body></html>
	</xsl:template>

	<xsl:template match="list">
		<div id="content">
			<center>
				<form action="{/entity/prefix}exec{//@name}.{//@ext}"  
					  name="frm_{//@name}" 
					  id="frm_{//@name}" 
					  method="post" 
					  enctype="multipart/form-data"
					  >
				<table cellspacing="0" cellpadding="0" border="0">
					<xsl:choose>
						<xsl:when test="count(instance) &gt; 0">
							<thead>
								<tr class="ui-state-default ui-corner-all">
									<th width="99%">&nbsp;<xsl:value-of select="listable/field/@name"/>&nbsp;</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td align="center" class="ui-state-default" style="border-top:0px;background:#ffffff;">
										<select multiple="" style="width:100%;height:300px" name="id[]" id="id">
											<xsl:for-each select="instance">
												<option value="{@key}"><xsl:value-of select="field" disable-output-escaping="yes"/></option>
											</xsl:for-each>
										</select>
									</td>
									<td align="right" class="ui-state-default" style="border-top:0px;border-left:0px;background:#ffffff;">
										<table width="1%">
											<tr>
												<td align="right" class="plain">
													<button type="button" 
															id="subir"
															class="ui-state-default ui-corner-all"
															onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
															onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
															onclick="javascript:move('up','id')">
														<table border="0" cellpadding="0" cellspacing="0" width="100%">
															<tr>
																<td><!--img src="images/up.png" align="left"/--><span class="ui-icon ui-icon-arrowthick-1-n"/></td>
																<td align="right">MSG_UP</td>
															</tr>
														</table>
													</button>
												</td>
											</tr>
											<tr>
												<td align="right" class="plain">
													<button type="button"
															id="bajar"
															class="ui-state-default ui-corner-all"
															onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
															onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
															onclick="javascript:move('down','id')">
														<table border="0" cellpadding="0" cellspacing="0" width="100%">
															<tr>
																<td><!--img src="images/down.png" align="left"/--><span class="ui-icon ui-icon-arrowthick-1-s"/></td>
																<td>MSG_DOWN</td>
															</tr>
														</table>
													</button>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</tbody>
						</xsl:when>
						<xsl:otherwise>
							<tr><td class="ui-state-default" style="border-top:0px;border-left:0px;" align="center">MSG_NOTHING</td></tr>
						</xsl:otherwise>
					</xsl:choose>
				</table>
				<!-- aqui deberia aplicar un sheet para los controles <xsl:apply-templates select="entity/controls"/> -->
				<table>
					<tr>
						<td align="left" class="plain">
							<button type="button" 
									class="ui-state-default ui-corner-all"
									onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
									onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
									onclick="javascript:goTo('{/entity/prefix}list{//@name}.{//@ext}?{$ids}')">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td><!--img src="images/cancel.png" align="left"/--><span class="ui-icon ui-icon-closethick"/></td>
										<td align="right">MSG_CANCEL</td>
									</tr>
								</table>
							</button>
						</td>
						<td align="right" class="plain">
							<button type="submit" 
									class="ui-state-default ui-corner-all"
									onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
									onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
									onclick="javascript:selectAll('id')">
								<!--img src="images/delete.png" align="left"/><span>Remove</span-->
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td><!--img src="images/accept.png" align="left"/--><span class="ui-icon ui-icon-check"/></td>
										<td>MSG_SORT</td>
									</tr>
								</table>
							</button>
						</td>
					</tr>
				</table>
				<input type="hidden" name="action" value="sort"/>
				<xsl:for-each select="/entity/ancestors/ancestor">
					<input type="hidden" name="{@id}" id="{@id}" value="{@value}"/>
				</xsl:for-each>
			</form>
		</center>
		<!--img src="images/open.gif" height="20" width="1"/-->
	</div>
	</xsl:template>
</xsl:stylesheet>
