<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" 
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
		<!--html><head>
				<link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
				<script src="../js/dojo/dojo.js"></script>
				<script src="../js/tinymce/tiny_mce.js"></script>
 				<script src="../js/utils.js"></script>
				<script src="js/validators.js"></script>
 				<script src="../upload/js/upload.js"></script>
 				<script>dojo.require('dojo.widget.*');</script>
 				</head><body><div id="container"><div id="center"-->
		<div id="content-header">
			<center>
				<table width="100%" border="1">
					<tr>
						<td colspan="4" valign="middle" align="left">
							<h2><xsl:value-of select="/entity/title"/></h2>
						</td>
					</tr>
					<tr>
						<td align="left">
							<xsl:if test="/entity/back/@entity != ''">
								<a href="javascript:getAndTransform('list{/entity/back/@entity}.{//@ext}?{$backids}','list.xsl','center')">&lt; <xsl:value-of select="/entity/back/@title"/></a>
							</xsl:if>
						</td>
						<td width="50%" align="left" style="color:#f66">
							<xsl:if test="/entity/error != ''">
								<img src="images/error.png"/>&nbsp;<xsl:value-of select="/entity/error"/>
							</xsl:if>
						</td>
						<td valign="middle" align="right">
							<label for="search">Search:</label>
							<input type="text" id="search" name="search"/>
						</td>
						<td width="1%">
							<button type="button" onclick="getAndTransform('list{//@name}.{//@ext}?search='+document.getElementById('search').value+'{$ids}','list.xsl','center');">
								<img src="images/search.png" border="0"/>
							</button>
						</td>
					</tr>
					<tr>
						<xsl:apply-templates select="entity/pager"/>
					</tr>
				</table>
			</center>
		</div>
		<xsl:apply-templates select="entity/list"/>
		<!--/div></div></body></html-->
	</xsl:template>
	
	<xsl:template match="list">
		<div id="content">
			<center>
				<form action="exec{//@name}.{//@ext}"  
					  name="frm_{//@name}" 
					  id="frm_{//@name}" 
					  method="post" 
					  enctype="multipart/form-data"
					  >
				<table cellspacing="0" cellpadding="0" border="0" style="border-left: 1px solid #ccc; border-right: 1px solid #ccc;">
					<xsl:choose>
						<xsl:when test="count(instance) &gt; 0">
							<thead>
								<tr>
									<th width="30px">&nbsp;</th>
									<xsl:for-each select="listable/field">
										<xsl:choose>
											<xsl:when test="@type ='checkbox' or @type = 'radio'">
												<th align="center" width="30px">&nbsp;<xsl:value-of select="@name"/>&nbsp;</th>
											</xsl:when>
											<xsl:otherwise>
												<th>&nbsp;<xsl:value-of select="@name"/>&nbsp;</th>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:for-each>
									<xsl:for-each select="instance/child">
										<th align="center" width="50px">&nbsp;<img src="images/relations.png" alt="Has many {@name}" title="Has many {@name}"/><xsl:value-of select="@name"/>&nbsp;</th>
									</xsl:for-each>
								</tr>
							</thead>
							<tbody>
								<xsl:for-each select="instance">
									<xsl:variable name="key" select="@key"/>
									<xsl:variable name="primarykey" select="@primarykey"/>
									<tr>
										<td align="center" class="part1"><input type="checkbox" name="id[]" id="id[]" value="{@key}"/></td>
										<xsl:for-each select="field">
											<xsl:variable name="mapping" select="@map"/>
											<xsl:variable name="value" select="."/>
											<xsl:variable name="childs" select="count(../child)"/>
											<xsl:for-each select="../../listable/field">	
												<xsl:if test="@map = $mapping">
													<td class="part1">
														<!--xsl:if test="position()=1 and $childs &gt; 0">
															<img onclick="showNextRow(this)" src="images/close.gif" alt="+" title="Show relations"/>&nbsp;
														</xsl:if-->
														<xsl:choose>
															<xsl:when test="@type = 'radio' or @type = 'checkbox'">
																<center><a href="javascript:getAndTransform('exec{//@name}.{//@ext}?ini={/entity/pager/offset}&amp;action=mod&amp;id={$key}&amp;str_{@map}={1 - $value}{$ids}','list.xsl','center');"><img src="images/{$value}.gif"/></a></center>
															</xsl:when>							
															<xsl:otherwise>
																<a href="javascript:getAndTransform('mod{//@name}.{//@ext}?action=mod&amp;id={$key}{$ids}','mod.xsl','center')">
																	&nbsp;<xsl:value-of select="$value" disable-output-escaping="yes"/>
																</a>
															</xsl:otherwise>
														</xsl:choose>
													</td>
												</xsl:if>	
											</xsl:for-each>
										</xsl:for-each>
										<xsl:for-each select="child">
											<td align="center"><a href="javascript: getAndTransform('list{@name}.{//@ext}?{$primarykey}={$key}{$ids}','list.xsl','center')">(<xsl:value-of select="@count"/>)</a></td>
										</xsl:for-each>
									</tr>
									<!-- the row with the subentities related to this 
									<tr class="part1" style="display:none">
										<td class="part1">&nbsp;</td>
										<td class="part1" colspan="10">
											<xsl:for-each select="child">
												&nbsp;<a href="javascript: getAndTransform('list{@name}.{//@ext}?{$primarykey}={$key}{$ids}','list.xsl','center')"><xsl:value-of select="@name"/>(<xsl:value-of select="@count"/>)</a>
											</xsl:for-each>
										</td>
									</tr>-->
								</xsl:for-each>	
							</tbody>
						</xsl:when>
						<xsl:otherwise>
							<tr><td class="part2" align="center">Nothing to show</td></tr>
						</xsl:otherwise>
					</xsl:choose>
				</table>
				<table>
					<tr>
						<xsl:choose>
							<xsl:when test="count(instance) &gt; 0">
								<td align="left" class="plain">
									<button type="submit" onclick="return confirm('Do you really want to delete the selected items?')">
										<!--img src="images/delete.png" align="left"/><span>Remove</span-->
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><img src="images/delete.png" align="left"/></td>
												<td>Remove</td></tr>
										</table>
									</button>
								</td>
								<td width="50%" align="right" class="plain">
									<button type="button" onclick="getAndTransform('mod{//@name}.{//@ext}?action=add{$ids}','mod.xsl','center')">
										<!--img src="images/delete.png" align="left"/><span>Remove</span-->
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><img src="images/add.png" align="left"/></td>
												<td>Add</td>
											</tr>
										</table>
									</button>
								</td>
							</xsl:when>
							<xsl:otherwise>
								<td  align="center" class="plain">
									<button type="button" onclick="getAndTransform('mod{//@name}.{//@ext}?action=add{$ids}','mod.xsl','center')">
										<!--img src="images/delete.png" align="left"/><span>Remove</span-->
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><img src="images/add.png" align="left"/></td>
												<td>Add</td>
											</tr>
										</table>
									</button>
								</td>
							</xsl:otherwise>
						</xsl:choose>
					</tr>
				</table>
				<input type="hidden" name="action" value="del"/>
				<xsl:for-each select="/entity/ancestors/ancestor">
					<input type="hidden" name="{@id}" id="{@id}" value="{@value}"/>
				</xsl:for-each>
			</form>
			<script>
				dojo.addOnLoad(function (){
					new dojo.io.FormBind({
						formNode: document.forms.frm_<xsl:value-of select="//@name"/>,
						handle: function(type, data, evt) {  },
						load: function(load, data,evt){ transform(evt.responseXML, 'list.xsl','center');},
						mimetype: "text/html"
					});
				});
			</script>
		</center>
		<img src="images/open.gif" height="20" width="1"/>
	</div>
	</xsl:template>
	
	<xsl:template match="pager">
		<td colspan="2" align="left">
			Showing <xsl:value-of select="offset + 1"/>
			- <xsl:choose>
				<xsl:when test="offset + page-size &lt; total-rows">
					<xsl:value-of select="offset + page-size"/>
				</xsl:when>
				<xsl:otherwise><xsl:value-of select="total-rows"/></xsl:otherwise>
			</xsl:choose>
			of <xsl:value-of select="total-rows"/>
		</td>
		<td colspan="2" align="right">	
			<xsl:if test="offset > 0">
				<a href="javascript:getAndTransform('list{//@name}.{//@ext}?ini={offset - page-size}{$ids}','list.xsl','center')"><img src="images/less.gif" class="border" align="absmiddle"/></a>&nbsp;
			</xsl:if>
			<xsl:value-of select="ceiling(offset div page-size)+1"/>/<xsl:value-of select="ceiling(total-rows div page-size)"/>
			<xsl:if test="offset &lt; total-rows - page-size">
				&nbsp;<a href="javascript:getAndTransform('list{//@name}.{//@ext}?ini={offset + page-size}{$ids}','list.xsl','center')"><img src="images/more.gif" class="border" align="absmiddle"/></a>
			</xsl:if>
		</td>
	</xsl:template>
</xsl:stylesheet>