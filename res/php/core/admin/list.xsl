<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 	<xsl:output method="html" encoding="utf-8"/>
	
	<xsl:include href="localize.xsl"/>
	<xsl:include href="common.xsl"/>
	
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
				<link href="../admin/css/ui.css" rel="stylesheet" type="text/css"/>
				<script type="text/javascript" src= "../js/jquery.js"></script>
				<script type="text/javascript" src= "../js/jquery-ui.js"></script>
				<script type="text/javascript" src= "../js/jquery.form.js"></script>
				<script type="text/javascript" src= "../js/json.js"></script>
				<script type="text/javascript" src= "../js/utils.js"></script>
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
								href="{/entity/prefix}list{@class}.{//@ext}?{$backids}"><xsl:value-of select="."/></a></li>
					</xsl:for-each>
					<li><a class="ui-state-active ui-corner-bl ui-corner-br"><xsl:value-of select="/entity/title"/></a></li>
				</ul>
				<div id="content-header">
					<center>
						<form action="{/entity/prefix}list{//@name}.{//@ext}?{$ids}" method="GET">
						<table width="100%" border="1">
							<tr>
								<xsl:choose>
								<xsl:when test="/entity/error != ''">
								<td width="75%" align="left">
									<div class="ui-state-error ui-corner-all" style="padding: 10px 10px 10px 10px ;">
										<span class="ui-icon ui-icon-alert" style="float: left;"></span>&nbsp;
										<xsl:value-of select="/entity/error"/>
									</div>
								</td>
								</xsl:when>
								<xsl:otherwise>
									<td width="75%" align="left">&nbsp;</td>
								</xsl:otherwise>
								</xsl:choose>
								<xsl:if test="/entity/properties/searchable = 1">
								<td valign="middle" align="right" width="100%">
									<input type="text" id="search" name="search" value="{/entity/search}"/>
								</td>
								<td>
									<button type="submit"
											class="ui-state-default ui-corner-all"
											onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
											onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')">
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><img src="images/search.png" border="0"/></td>
												<td><xsl:call-template name="msg-search"/></td>
											</tr>
										</table>
									</button>
								</td>
								</xsl:if>
							</tr>
							<tr>
								<xsl:apply-templates select="entity/pager"/>
							</tr>
						</table>
						</form>
					</center>
				</div>
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
					<xsl:choose>
						<xsl:when test="count(instance) &gt; 0">
							<table cellspacing="0" cellpadding="0" border="0">
							<thead>
								<tr class="ui-state-active ui-corner-all">
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
															<td class="part2">
																<xsl:choose>
																	<xsl:when test="@type = 'radio' or @type = 'checkbox'">
																		<center><a href="{/entity/prefix}exec{//@name}.{//@ext}?ini={/entity/pager/offset}&amp;action=mod&amp;id={$key}&amp;str_{@map}={1 - $value}{$ids}"><img class="checkbox" src="images/{$value}.png"/></a></center>
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
													<td align="center" class="part2"><a href="{/entity/prefix}list{@name}.{//@ext}?{$primarykey}={$key}{$ids}">(<xsl:value-of select="@count"/>)</a></td>
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
															<td class="part2">
																<xsl:choose>
																	<xsl:when test="@type = 'radio' or @type = 'checkbox'">
																		<center><a href="{/entity/prefix}exec{//@name}.{//@ext}?ini={/entity/pager/offset}&amp;action=mod&amp;id={$key}&amp;str_{@map}={1 - $value}{$ids}"><img class="checkbox" src="images/{$value}.png"/></a></center>
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
													<td align="center" class="part2"><a href="{/entity/prefix}list{@name}.{//@ext}?{$primarykey}={$key}{$ids}">(<xsl:value-of select="@count"/>)</a></td>
												</xsl:for-each>
											</tr>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:for-each>	
							</tbody>
							</table>
						</xsl:when>
						<xsl:otherwise>
							<table cellspacing="0" cellpadding="0" border="0" style="border: 1px solid #ccc;">
							<tr><td align="center"><xsl:call-template name="msg-nothing"/></td></tr>
							</table>
						</xsl:otherwise>
					</xsl:choose>
				
				<table>
					<tr>
						<xsl:choose>
							<xsl:when test="count(instance) &gt; 0">
								<td align="left" class="plain">
								<xsl:variable name="msg-confirm"><xsl:call-template name="msg-confirm"/></xsl:variable>
								<button id="_deleteBtn_"
										type="submit"
										class="ui-state-default ui-corner-all"
										onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
										onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
										onclick="return confirm('{$msg-confirm}')">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td><img src="images/delete.png" align="left"/></td>
											<td><xsl:call-template name="msg-delete" /></td></tr>
									</table>
								</button>
								<xsl:if test="/entity/properties/delete = 0">
								<script>
									$(function(){
										document.getElementById('_deleteBtn_').disabled = true;
										$('#_deleteBtn_').addClass('ui-state-disabled');
									});
								</script>
								</xsl:if>
								</td>
								<xsl:if test="/entity/properties/sortable = 1">
									<td align="center" class="plain">
									<button id="_sortBtn_"
											type="button"
											class="ui-state-default ui-corner-all"
											onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
											onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
											onclick="javascript:getAndTransform('{/entity/prefix}list{//@name}.{//@ext}?action=sort{$ids}','','')">
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><img src="images/order.png" align="left"/></td>
												<td><xsl:call-template name="msg-sort" /></td>
											</tr>
										</table>
									</button>
									<xsl:if test="count(instance) &lt; 2">
									<script>
										$(function(){
											document.getElementById('_sortBtn_').disabled = true;
											$('#_sortBtn_').addClass('ui-state-disabled');
										});
									</script>
									</xsl:if>
									</td>
								</xsl:if>
								<td align="right" class="plain">
									<button id="_addBtn_"
											type="button"
											class="ui-state-default ui-corner-all"
											onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
											onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
											onclick="getAndTransform('{/entity/prefix}mod{//@name}.{//@ext}?action=add{$ids}','','')">
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><img src="images/add.png" align="left"/></td>
												<td><xsl:call-template name="msg-add" /></td>
											</tr>
										</table>
									</button>
									<xsl:if test="/entity/properties/add = 0">
									<script>
										$(function(){
											document.getElementById('_addBtn_').disabled = true;
											$('#_addBtn_').addClass('ui-state-disabled');
										});
									</script>
									</xsl:if>
								</td>
							</xsl:when>
							<xsl:otherwise>
								<td align="center" class="plain">
									<button id="_addBtn_"
											type="button"
											class="ui-state-default ui-corner-all"
											onclick="getAndTransform('{/entity/prefix}mod{//@name}.{//@ext}?action=add{$ids}','','')"
											onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
											onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')">
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><img src="images/add.png" align="left"/></td>
												<td><xsl:call-template name="msg-add"/></td>
											</tr>
										</table>
									</button>
									<xsl:if test="/entity/properties/add = 0">
									<script>
										$(function(){
											document.getElementById('_addBtn_').disabled = true;
											$('#_addBtn_').addClass('ui-state-disabled');
										});
									</script>
									</xsl:if>
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
		<td align="left">
			<xsl:call-template name="msg-showing"/>: <xsl:value-of select="offset + 1"/>
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
				<a class="ui-state-default ui-corner-all"
					style="padding-bottom:3px; padding-top:2px;"
					onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
					onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
					href="{/entity/prefix}list{//@name}.{//@ext}?ini={offset - page-size}{$ids}">
					<img src="images/less.gif" align="absmiddle"/>
				</a>&nbsp;
			</xsl:if>
			<xsl:value-of select="ceiling(offset div page-size)+1"/>/<xsl:value-of select="ceiling(total-rows div page-size)"/>
			<xsl:if test="offset &lt; total-rows - page-size">
				&nbsp;<a class="ui-state-default ui-corner-all"
					style="padding-bottom:3px; padding-top:2px;"
					onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
					onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
						href="{/entity/prefix}list{//@name}.{//@ext}?ini={offset + page-size}{$ids}">
							<img src="images/more.gif" align="absmiddle"/>
						</a>
			</xsl:if>
		</td>
	</xsl:template>
</xsl:stylesheet>
