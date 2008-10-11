<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="2.0" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="utf-8"/>
	
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
				<script src="../js/jquery.js"></script>
 				<script src="../js/utils.js"></script>
				</head><body><div id="container"><div id="center">
		<xsl:choose>
			<xsl:when test="/entity/error != ''">
				<xsl:apply-templates select="/entity/error"/>
			</xsl:when>
			<xsl:otherwise>
				<div id="content-header">
					<center>
						<table width="100%" border="1">
							<tr>
								<td colspan="4" valign="middle" align="left">
									<h2><xsl:value-of select="/entity/title"/> > Sort</h2>
								</td>
							</tr>
						</table>
					</center>
				</div>
				<xsl:apply-templates select="entity/list"/>
			</xsl:otherwise>
		</xsl:choose>
		</div></div></body></html>
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

	<xsl:template match="list">
		<div id="content">
			<center>
				<form action="{/entity/prefix}exec{//@name}.{//@ext}"  
					  name="frm_{//@name}" 
					  id="frm_{//@name}" 
					  method="post" 
					  enctype="multipart/form-data"
					  >
				<table cellspacing="0" cellpadding="0" border="0" style="border-right: 1px solid #ccc;">
					<xsl:choose>
						<xsl:when test="count(instance) &gt; 0">
							<thead>
								<tr>
									<th width="99%">&nbsp;<xsl:value-of select="listable/field/@name"/>&nbsp;</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td align="center" class="part1">
										<select multiple="" style="width:100%;height:300px" name="id[]" id="id">
											<xsl:for-each select="instance">
												<option value="{@key}"><xsl:value-of select="field" disable-output-escaping="yes"/></option>
											</xsl:for-each>
										</select>
									</td>
									<td align="right">
										<table width="1%">
											<tr>
												<td align="right" class="plain">
													<button type="button" id="subir" onclick="javascript:move('up','id')">
														<table border="0" cellpadding="0" cellspacing="0" width="100%">
															<tr>
																<td><img src="images/up.png" align="left"/></td>
																<td align="right">Up</td>
															</tr>
														</table>
													</button>
												</td>
											</tr>
											<tr>
												<td align="right" class="plain">
													<button type="button" id="bajar" onclick="javascript:move('down','id')">
														<table border="0" cellpadding="0" cellspacing="0" width="100%">
															<tr>
																<td><img src="images/down.png" align="left"/></td>
																<td>Down</td>
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
							<tr><td class="part2" align="center">No hay elementos disponibles</td></tr>
						</xsl:otherwise>
					</xsl:choose>
				</table>
				<!-- aqui deberia aplicar un sheet para los controles <xsl:apply-templates select="entity/controls"/> -->
				<table>
					<tr>
						<td align="left" class="plain">
							<button type="button" onclick="javascript:getAndTransform('{/entity/prefix}list{//@name}.{//@ext}?{$ids}','','')">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td><img src="images/cancel.png" align="left"/></td>
										<td align="right">Cancel</td>
									</tr>
								</table>
							</button>
						</td>

						<td align="right" class="plain">
							<button type="submit" onclick="javascript:selectAll('id')">
								<!--img src="images/delete.png" align="left"/><span>Remove</span-->
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td><img src="images/add.png" align="left"/></td>
										<td>Sort</td>
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
