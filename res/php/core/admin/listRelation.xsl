<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 	<xsl:output method="html" encoding="utf-8"/>
	
	<xsl:include href="getResource.php?file=admin/list.xsl"/>
	<xsl:include href="getResource.php?file=admin/common.xsl"/>
	
 	<!-- define the ids of the ancestors required for each link that modifies the entity -->
 	<xsl:variable name="ids">
		<xsl:for-each select="/entity/ancestors/ancestor">&amp;<xsl:value-of select="@id"/>=<xsl:value-of select="@value"/></xsl:for-each>	
	</xsl:variable>
	<xsl:variable name="backids">
		<xsl:for-each select="/entity/back/ancestor">&amp;<xsl:value-of select="@id"/>=<xsl:value-of select="@value"/></xsl:for-each>
	</xsl:variable>
	
	<xsl:template match="list">
		<ul id="relation" style="padding-right: 30px;">
			<xsl:for-each select="/entity/relations/relation">
				<xsl:if test="@active = 'selected'">
					<li>
						<a class="ui-state-active ui-corner-tl ui-corner-tr"
							onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-active')"
							onmouseout="$(this).addClass('ui-state-active').removeClass('ui-state-hover')"
							href="{/entity/prefix}list{@class}.{//@ext}?rel_id={//@id}&amp;class={@class}&amp;currentClass={@currentClass}&amp;{$ids}">
								<xsl:value-of select="."/>:&nbsp; MSG_LISTING
						</a>
					</li>
				</xsl:if>	
				<xsl:if test="@active != 'selected'">
					<li>
						<a class="ui-state-default ui-corner-tl ui-corner-tr"
							onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
							onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
							href="{/entity/prefix}list{@class}.{//@ext}?rel_id={//@id}&amp;class={@class}&amp;currentClass={@currentClass}&amp;{$ids}">
								<xsl:value-of select="."/>
						</a>
					</li>
				</xsl:if>
			</xsl:for-each>
			<li>
				<a class="ui-state-default ui-corner-tl ui-corner-tr"
					onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
					onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')"
					href="{/entity/prefix}mod{//@currentClass}.{//@ext}?action=mod&amp;id={//@id}&amp;{$ids}">
					MSG_EDIT &nbsp;<xsl:value-of select="/entity/title"/>
				</a>
			</li>
		</ul>
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
								<tr class="ui-state-default ui-corner-all">
									<th width="30px">&nbsp;</th>
									<xsl:for-each select="listable/field">
										<th align="left" class="{@type}" style="padding-left:5px;padding-right:5px;">
											<a href="{/entity/prefix}list{//@name}.{//@ext}?orderby={@map}&amp;asc={/entity/order/asc}&amp;search={/entity/search}&amp;ini={/entity/pager/offset}{$ids}">
											<xsl:value-of select="@name"/>
											<xsl:if test="/entity/order/field = @map">
												<xsl:if test="/entity/order/asc = '0'">
													<span class="ui-icon ui-icon-arrowthick-1-s" style="float:left;"/>
												</xsl:if>
												<xsl:if test="/entity/order/asc = '2'">
													<span class="ui-icon ui-icon-arrowthick-1-n" style="float:left;"/>
												</xsl:if>
											</xsl:if>
											</a>
										</th>
									</xsl:for-each>
									<!--xsl:if test="/entity/properties/sortable = 1">
										<th class="part1" width="50" align="center">Sort</th>
									</xsl:if-->
									<!-- problema aqui se esta trayendo TODOS los instance/child, deberia traerse solo los de name diferentes -->
									<xsl:for-each select="listable/child">
										<th align="center" width="70px"><xsl:value-of select="@name"/>&nbsp;</th>
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
												<td align="center" class="ui-widget-content" style="border-top:0px;"><input type="checkbox" name="id[]" id="id[]" value="{@key}"/></td>
												<xsl:for-each select="field">
													<xsl:variable name="mapping" select="@map"/>
													<xsl:variable name="value" select="."/>
													<xsl:variable name="childs" select="count(../child)"/>
													<xsl:for-each select="../../listable/field">	
														<xsl:if test="@map = $mapping">
															<td class="ui-widget-content" style="border-top:0px;border-left:0px;">
																<xsl:choose>
																	<xsl:when test="@type = 'radio' or @type = 'checkbox'">
																		<center>
																			<a href="{/entity/prefix}exec{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@id}&amp;ini={/entity/pager/offset}&amp;action=mod&amp;id={$key}&amp;str_{@map}={1 - $value}{$ids}">
																				<!--img class="checkbox" src="images/{$value}.png"/-->
																				<xsl:if test="$value = '1'"><span class="ui-widget-content ui-icon ui-icon-check" /></xsl:if>
																				<xsl:if test="$value = '0'"><span class="ui-widget-content ui-icon ui-icon-close" /></xsl:if>
																			</a>
																		</center>
																	</xsl:when>
																	<xsl:when test="@type = 'image'">
																		<a href="{/entity/prefix}mod{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@id}&amp;action=mod&amp;id={$key}{$ids}"><img src="../{$value}" border="0"/></a>
																	</xsl:when>
																	<xsl:otherwise>
																		<a href="{/entity/prefix}mod{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@id}&amp;action=mod&amp;id={$key}{$ids}">
																			&nbsp;<xsl:value-of select="$value" disable-output-escaping="yes"/>
																		</a>
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</xsl:if>	
													</xsl:for-each>
												</xsl:for-each>
												<xsl:for-each select="child">
													<td align="center" class="ui-widget-content" style="border-top:0px;border-left:0px;"><a href="{/entity/prefix}list{@name}.{//@ext}?{$primarykey}={$key}{$ids}">(<xsl:value-of select="@count"/>)</a></td>
												</xsl:for-each>
											</tr>
										</xsl:when>
										<xsl:otherwise>
											<tr class="odd">
												<td align="center" class="ui-state-default" style="border-top:0px;"><input type="checkbox" name="id[]" id="id[]" value="{@key}"/></td>
												<xsl:for-each select="field">
													<xsl:variable name="mapping" select="@map"/>
													<xsl:variable name="value" select="."/>
													<xsl:variable name="childs" select="count(../child)"/>
													<xsl:for-each select="../../listable/field">	
														<xsl:if test="@map = $mapping">
															<td class="ui-state-default" style="border-top:0px;border-left:0px;">
																<xsl:choose>
																	<xsl:when test="@type = 'radio' or @type = 'checkbox'">
																		<center>
																			<a href="{/entity/prefix}exec{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@id}&amp;ini={/entity/pager/offset}&amp;action=mod&amp;id={$key}&amp;str_{@map}={1 - $value}{$ids}">
																				<!--img class="checkbox" src="images/{$value}.png"/-->
																				<xsl:if test="$value = '1'"><span class="ui-widget-content ui-icon ui-icon-check" /></xsl:if>
																				<xsl:if test="$value = '0'"><span class="ui-widget-content ui-icon ui-icon-close" /></xsl:if>
																			</a>
																		</center>
																	</xsl:when>
																	<xsl:when test="@type = 'image'">
																		<a href="{/entity/prefix}mod{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@id}&amp;action=mod&amp;id={$key}{$ids}"><img src="../{$value}" border="0"/></a>
																	</xsl:when>
																	<xsl:otherwise>
																		<a href="{/entity/prefix}mod{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@id}&amp;action=mod&amp;id={$key}{$ids}">
																			&nbsp;<xsl:value-of select="$value" disable-output-escaping="yes"/>
																		</a>
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</xsl:if>	
													</xsl:for-each>
												</xsl:for-each>
												<xsl:for-each select="child">
													<td align="center" class="ui-state-default" style="border-top:0px;border-left:0px;"><a href="{/entity/prefix}list{@name}.{//@ext}?{$primarykey}={$key}{$ids}">(<xsl:value-of select="@count"/>)</a></td>
												</xsl:for-each>
											</tr>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:for-each>	
							</tbody>
							</table>
						</xsl:when>
						<xsl:otherwise>
							<table cellspacing="0" cellpadding="0" border="0">
								<tr class="ui-state-default ui-corner-all"><th>&nbsp;</th></tr>
								<tr><td align="center" class="ui-widget-content">MSG_NOTHING</td></tr>
							</table>
						</xsl:otherwise>
					</xsl:choose>
				<table>
					<tr>
						<xsl:choose>
							<xsl:when test="count(instance) &gt; 0">
								<td align="left" class="plain">
								<xsl:variable name="msg-confirm">MSG_CONFIRM_DELETE</xsl:variable>
								<button id="_deleteBtn_"
										type="submit"
										class="ui-state-default ui-corner-all"
										onmouseover="$(this).addClass('ui-state-error').removeClass('ui-state-default')"
										onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-error')"
										onclick="return confirm('{$msg-confirm}')">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td><!--img src="images/delete.png" align="left"/--><span class="ui-icon ui-icon-minusthick"/></td>
											<td>MSG_DELETE</td></tr>
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
											onclick="javascript:goTo('{/entity/prefix}list{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@id}&amp;action=sort{$ids}')">
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><!--img src="images/order.png" align="left"/--><span class="ui-icon ui-icon-arrowthick-2-n-s"/></td>
												<td>MSG_SORT</td>
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
											onclick="goTo('{/entity/prefix}mod{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@id}&amp;action=add{$ids}')">
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><!--img src="images/add.png" align="left"/--><span class="ui-icon ui-icon-plusthick"/></td>
												<td>MSG_ADD</td>
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
											onclick="goTo('{/entity/prefix}mod{//@name}.{//@ext}?currentClass={//@currentClass}&amp;rel_id={//@id}&amp;action=add{$ids}')"
											onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
											onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')">
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td><!--img src="images/add.png" align="left"/--><span class="ui-icon ui-icon-plusthick"/></td>
												<td>MSG_ADD</td>
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
				<input type="hidden" name="rel_id" value="{//@id}"/>
				<input type="hidden" name="currentClass" value="{//@currentClass}"/>
				<!--input type="hidden" name="class" value="{//@name}"/-->
				<xsl:for-each select="/entity/ancestors/ancestor">
					<input type="hidden" name="{@id}" id="{@id}" value="{@value}"/>
				</xsl:for-each>
			</form>
		</center>
	</div>
	</xsl:template>
</xsl:stylesheet>
