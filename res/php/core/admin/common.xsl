<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="error">
		<div id="content">
			<center>
				<table height="50%" class="ui-state-highlight ui-corner-all" style="margin-top:50px;">
					<tr height="100%" valign="top">
						<td width="100"><img src="images/forbidden.png" style="cursor: default; margin: 10px"/></td>
						<td class="plain">
							<h1>MSG_FORBIDDEN</h1>
							<xsl:value-of select="msg"/><br/>
						</td>
					</tr>
				</table>
			</center>
			<xsl:if test="refresh = 'true'">
				<script>
					parent.leftFrame.reload();
				</script>
			</xsl:if>
		</div>
	</xsl:template>

	<xsl:template match="validator">
		<script language="{@language}">
			<xsl:value-of select="."/>
		</script>
	</xsl:template>
</xsl:stylesheet>