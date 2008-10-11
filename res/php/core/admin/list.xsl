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
		<html><head>
				<xsl:if test="/entity/basepath != ''">
					<base id="base" href="{/entity/basepath}" />
				</xsl:if>
				<xsl:if test="/error/basepath != ''">
					<base id="base" href="{/error/basepath}" />
				</xsl:if>
				<link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
				<script src="../js/utils.js"></script>
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
								<td colspan="4" valign="middle" align="left">
									<h2><xsl:value-of select="/entity/title"/></h2>
								</td>
							</tr>
							<tr>
								<td align="left">
									<xsl:if test="/entity/back/@entity != ''">
										<a href="{/entity/prefix}list{/entity/back/@entity}.{//@ext}?{$backids}">&lt; <xsl:value-of select="/entity/back/@title"/></a>
									</xsl:if>
								</td>
								<td width="50%" align="left" style="color:#f66">
									<xsl:if test="/entity/error != ''">
										<img src="images/error.png"/>&nbsp;<xsl:value-of select="/entity/error"/>
									</xsl:if>
								</td>
								<xsl:if test="/entity/properties/searchable = 1">
								<td valign="middle" align="right">
									<label for="search">Search:</label>
									<input type="text" id="search" name="search" value="{/entity/search}"/>
								</td>
								<td width="1%">
									<button type="button" onclick="getAndTransform('{/entity/prefix}list{//@name}.{//@ext}?search='+document.getElementById('search').value+'{$ids}','list.xsl','center');">
										<img src="images/search.png" border="0"/>
									</button>
								</td>
								</xsl:if>
							</tr>
							<tr>
								<xsl:apply-templates select="entity/pager"/>
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
				<table cellspacing="0" cellpadding="0" border="0" style="border-right: 1px solid #ccc;border-top: 1px solid #ccc;">
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
											<xsl:when test="@type ='image'">
												<th align="left" width="100px">&nbsp;<xsl:value-of select="@name"/>&nbsp;</th>
											</xsl:when>
											<xsl:otherwise>
												<th align="left">&nbsp;<xsl:value-of select="@name"/>&nbsp;</th>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:for-each>
									<!--xsl:if test="/entity/properties/sortable = 1">
										<th class="part1" width="50" align="center">Sort</th>
									</xsl:if-->
									<!-- problema aqui se esta trayendo TODOS los instance/child, deberia traerse solo los de name diferentes -->
									<xsl:for-each select="listable/child">
										<th align="center" width="50px"><xsl:value-of select="@name"/>&nbsp;</th>
									</xsl:for-each>
								</tr>
							</thead>
							<tbody>
								<xsl:for-each select="instance">
									<xsl:variable name="key" select="@key"/>
									<xsl:variable name="primarykey" select="@primarykey"/>
									<xsl:variable name="module"><xsl:value-of select="position() mod 2"/></xsl:variable>
									<xsl:choose>
										<xsl:when test="$module = 1">
											<tr>
												<td align="center" class="part1"><input type="checkbox" name="id[]" id="id[]" value="{@key}"/></td>
												<xsl:for-each select="field">
													<xsl:variable name="mapping" select="@map"/>
													<xsl:variable name="value" select="."/>
													<xsl:variable name="childs" select="count(../child)"/>
													<xsl:for-each select="../../listable/field">	
														<xsl:if test="@map = $mapping">
															<td class="part1">
																<xsl:choose>
																	<xsl:when test="@type = 'radio' or @type = 'checkbox'">
																		<center><a href="{/entity/prefix}exec{//@name}.{//@ext}?ini={/entity/pager/offset}&amp;action=mod&amp;id={$key}&amp;str_{@map}={1 - $value}{$ids}"><img src="images/{$value}.gif"/></a></center>
																	</xsl:when>
																	<xsl:when test="@type = 'image'">
																		<a href="{/entity/prefix}mod{//@name}.{//@ext}?action=mod&amp;id={$key}{$ids}"><img src="../{$value}" border="0"/></a>
																	</xsl:when>
																	<xsl:otherwise>
																		<a href="{/entity/prefix}mod{//@name}.{//@ext}?action=mod&amp;id={$key}{$ids}">
																			&nbsp;<xsl:value-of select="$value" disable-output-escaping="yes"/>
																		</a>
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</xsl:if>	
													</xsl:for-each>
												</xsl:for-each>
												<xsl:for-each select="child">
													<td align="center" class="part1"><a href="{/entity/prefix}list{@name}.{//@ext}?{$primarykey}={$key}{$ids}">(<xsl:value-of select="@count"/>)</a></td>
												</xsl:for-each>
											</tr>
										</xsl:when>
										<xsl:otherwise>
											<tr class="odd">
												<td align="center" class="part1"><input type="checkbox" name="id[]" id="id[]" value="{@key}"/></td>
												<xsl:for-each select="field">
													<xsl:variable name="mapping" select="@map"/>
													<xsl:variable name="value" select="."/>
													<xsl:variable name="childs" select="count(../child)"/>
													<xsl:for-each select="../../listable/field">	
														<xsl:if test="@map = $mapping">
															<td class="part1">
																<xsl:choose>
																	<xsl:when test="@type = 'radio' or @type = 'checkbox'">
																		<center><a href="{/entity/prefix}exec{//@name}.{//@ext}?ini={/entity/pager/offset}&amp;action=mod&amp;id={$key}&amp;str_{@map}={1 - $value}{$ids}"><img src="images/{$value}.gif"/></a></center>
																	</xsl:when>
																	<xsl:when test="@type = 'image'">
																		<a href="{/entity/prefix}mod{//@name}.{//@ext}?action=mod&amp;id={$key}{$ids}"><img src="../{$value}" border="0"/></a>
																	</xsl:when>
																	<xsl:otherwise>
																		<a href="{/entity/prefix}mod{//@name}.{//@ext}?action=mod&amp;id={$key}{$ids}">
																			&nbsp;<xsl:value-of select="$value" disable-output-escaping="yes"/>
																		</a>
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</xsl:if>	
													</xsl:for-each>
												</xsl:for-each>
												<xsl:for-each select="child">
													<td align="center" class="part1"><a href="{/entity/prefix}list{@name}.{//@ext}?{$primarykey}={$key}{$ids}">(<xsl:value-of select="@count"/>)</a></td>
												</xsl:for-each>
											</tr>
										</xsl:otherwise>
									</xsl:choose>
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
									<!--xsl:if test="/entity/properties/delete = 1"-->
										<button id="_deleteBtn_" type="submit" onclick="return confirm('Do you really want to delete the selected items?')" >
											<!--img src="images/delete.png" align="left"/><span>Remove</span-->
											<table border="0" cellpadding="0" cellspacing="0" width="100%">
												<tr>
													<td><img src="images/delete.png" align="left"/></td>
													<td>Delete</td></tr>
											</table>
										</button>
										<script>document.getElementById('_deleteBtn_').disabled = '<xsl:value-of select="/entity/properties/delete"/>'=='0';</script>
									<!--/xsl:if-->
								</td>
								<xsl:if test="/entity/properties/sortable = 1">
									<td align="center" class="plain">
									<button id="_sortBtn_" type="button" onclick="javascript:getAndTransform('{/entity/prefix}list{//@name}.{//@ext}?action=sort{$ids}','','')">
										<!--img src="images/delete.png" align="left"/><span>Remove</span-->
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><img src="images/order.png" align="left"/></td>
												<td>Sort</td>
											</tr>
										</table>
									</button>
									<script>document.getElementById('_sortBtn_').disabled = '<xsl:value-of select="count(instance)"/>' &lt; 2;</script>
									</td>
								</xsl:if>
								<td align="right" class="plain">
									<!--xsl:if test="/entity/properties/add = 1"-->
										<button id="_addBtn_" type="button" onclick="getAndTransform('{/entity/prefix}mod{//@name}.{//@ext}?action=add{$ids}','','')">
											<!--img src="images/delete.png" align="left"/><span>Remove</span-->
											<table border="0" cellpadding="0" cellspacing="0" width="100%">
												<tr>
													<td><img src="images/add.png" align="left"/></td>
													<td>Add</td>
												</tr>
											</table>
										</button>
										<script>document.getElementById('_addBtn_').disabled = '<xsl:value-of select="/entity/properties/add"/>'=='0';</script>
									<!--/xsl:if-->
								</td>
							</xsl:when>
							<xsl:otherwise>
								<td  align="center" class="plain">
									<!--xsl:if test="/entity/properties/add = 1"-->
										<button id="_addBtn_" type="button" onclick="getAndTransform('{/entity/prefix}mod{//@name}.{//@ext}?action=add{$ids}','','')">
											<!--img src="images/delete.png" align="left"/><span>Remove</span-->
											<table border="0" cellpadding="0" cellspacing="0" width="100%">
												<tr>
													<td><img src="images/add.png" align="left"/></td>
													<td>Add</td>
												</tr>
											</table>
										</button>
										<script>document.getElementById('_addBtn_').disabled = <xsl:value-of select="/entity/properties/add"/>==0;</script>
									<!--/xsl:if-->
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
		</center>
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
				<a href="{/entity/prefix}list{//@name}.{//@ext}?ini={offset - page-size}{$ids}"><img src="images/less.gif" class="border" align="absmiddle"/></a>&nbsp;
			</xsl:if>
			<xsl:value-of select="ceiling(offset div page-size)+1"/>/<xsl:value-of select="ceiling(total-rows div page-size)"/>
			<xsl:if test="offset &lt; total-rows - page-size">
				&nbsp;<a href="{/entity/prefix}list{//@name}.{//@ext}?ini={offset + page-size}{$ids}"><img src="images/more.gif" class="border" align="absmiddle"/></a>
			</xsl:if>
		</td>
	</xsl:template>
</xsl:stylesheet>
