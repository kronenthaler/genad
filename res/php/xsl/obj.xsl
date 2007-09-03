<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	<xsl:template match="/"><![CDATA[<?]]>
class <xsl:value-of select="//@name"/> extends AbstractObject{
	function <xsl:value-of select="//@name"/>(){
		$this->tablename='<xsl:value-of select="entity/table/@name"/>';
		$this->primarykey='<xsl:value-of select="entity/table/@primary-key"/>';
		$this->title = '<xsl:value-of select="//@title"/>';

		//initialize the fields array
		<xsl:if test="entity/parent/@id != ''">$this->fields['<xsl:value-of select="entity/parent/@id"/>'] = array(TITLE=>'PARENT_ID', TYPE=>'textfield', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);</xsl:if>
		<xsl:if test="entity/form/field/db-field != entity/table/@primary-key">
		$this->fields['<xsl:value-of select="entity/table/@primary-key"/>'] = array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		</xsl:if>
		<xsl:for-each select="entity/form/field">
		$this->fields['<xsl:value-of select="db-field"/>'] = array(TITLE=>'<xsl:value-of select="label"/>', TYPE=>'<xsl:value-of select="@type"/>', VISIBLE=><xsl:value-of select="show-in-form"/>, LISTABLE=><xsl:value-of select="show-in-list"/>, EDITABLE=><xsl:value-of select="0"/>, SEARCHABLE=><xsl:value-of select="searchable"/>, ONLISTPOS=><xsl:value-of select="position()"/>, ONFORMPOS=><xsl:value-of select="position()"/>);
		</xsl:for-each>	

		$this->childs=array(<xsl:for-each select="entity/entity"><xsl:if test="position() != 1">,</xsl:if>'<xsl:value-of select="@name"/>'</xsl:for-each>); //childs classes
		$this->ancestor = '<xsl:value-of select="entity/parent/@class"/>'; //ancestor class
	}
}
?></xsl:template>
</xsl:stylesheet>