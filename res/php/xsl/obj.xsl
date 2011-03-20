<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/"><xsl:apply-templates/></xsl:template>

	<xsl:template match="entity"><![CDATA[<?]]>
class <xsl:value-of select="//@name"/> extends AbstractObject{
	function <xsl:value-of select="//@name"/>(){
		$this->tablename='<xsl:value-of select="table/@name"/>';
		$this->primarykey='<xsl:value-of select="table/@primary-key"/>';
		$this->title = MSG_<xsl:value-of select="translate(//@name,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_TITLE;

		//initialize the fields array
		<!-- usar MSG_<xsl:value-of select="translate(//@name,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_<xsl:value-of select="translate(db-field,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_TITLE-->
		<xsl:if test="parent/@id != ''">$this->fields['<xsl:value-of select="parent/@id"/>'] = array(TITLE=>'PARENT_ID', TYPE=>'textfield', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);</xsl:if>
		<xsl:if test="form/field/db-field != table/@primary-key">$this->fields['<xsl:value-of select="table/@primary-key"/>'] = array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);</xsl:if>
		<xsl:for-each select="form/field">
		$this->fields['<xsl:value-of select="db-field"/>'] = array(TITLE=>MSG_<xsl:value-of select="translate(//@name,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_<xsl:value-of select="translate(db-field,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_TITLE, TYPE=>'<xsl:value-of select="@type"/>', VISIBLE=><xsl:value-of select="show-in-form"/>, REQUIRED=><xsl:value-of select="required"/>, LISTABLE=><xsl:value-of select="show-in-list"/>, EDITABLE=><xsl:value-of select="0"/>, SEARCHABLE=><xsl:value-of select="searchable"/>, ONLISTPOS=><xsl:value-of select="position()"/>, ONFORMPOS=><xsl:value-of select="position()"/>);</xsl:for-each>
		$this->fields['_sort'] = array(TITLE=>'Order', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>-1, ONFORMPOS=>-1);	
		
		$this->properties[ADD]=1;
		$this->properties[DELETE]=1;
		$this->properties[SORTABLE]=<xsl:value-of select="sortable/@value"/>;
		$this->properties[PAGER]=<xsl:value-of select="splitpage/@value"/>;
		$this->properties[SEARCH]=<xsl:value-of select="search/@value"/>;
		
		$this->childs=array(<xsl:for-each select="entity"><xsl:if test="position() != 1">,</xsl:if>'<xsl:value-of select="@name"/>'</xsl:for-each>); //childs classes
		$this->ancestor = '<xsl:value-of select="parent/@class"/>'; //ancestor class
		$this->relations = array(<xsl:for-each select="relations/relation"><xsl:if test="position() != 1">,</xsl:if>'<xsl:value-of select="@name"/>'</xsl:for-each>);
	}
}
?></xsl:template>

<xsl:template match="relation"><![CDATA[<?]]>
class <xsl:value-of select="//@name"/> extends AbstractRelation{
	function <xsl:value-of select="//@name"/>(){
		$this->tablename='<xsl:value-of select="table/@name"/>';
		$this->primarykey='<xsl:value-of select="table/@name"/>_id';
		$this->title = '<xsl:value-of select="//@title"/>';

		//initialize the fields array
		$this->fields['<xsl:value-of select="table/@name"/>_id'] = array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		<xsl:for-each select="entity">
		$this->fields['<xsl:value-of select="@primarykey"/>'] = array(TITLE=>'ID', TYPE=>'id', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);</xsl:for-each>
		<xsl:for-each select="form/field">
		$this->fields['<xsl:value-of select="db-field"/>'] = array(TITLE=>'<xsl:value-of select="label"/>', TYPE=>'<xsl:value-of select="@type"/>', VISIBLE=><xsl:value-of select="show-in-form"/>, LISTABLE=><xsl:value-of select="show-in-list"/>, EDITABLE=><xsl:value-of select="0"/>, SEARCHABLE=><xsl:value-of select="searchable"/>, ONLISTPOS=><xsl:value-of select="position()"/>, ONFORMPOS=><xsl:value-of select="position()"/>);</xsl:for-each>
		$this->fields['_sort'] = array(TITLE=>'Order', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>-1, ONFORMPOS=>-1);

		$this->properties[ADD]=1;
		$this->properties[DELETE]=1;
		$this->properties[SORTABLE]=<xsl:value-of select="sortable/@value"/>;
		$this->properties[PAGER]=<xsl:value-of select="splitpage/@value"/>;
		$this->properties[SEARCH]=<xsl:value-of select="search/@value"/>;

		$this->childs = array(); //childs classes (relations shouldn't have childs)
		$this->ancestor = ''; //ancestor class (relations shouldn't have ancestors)
		$this->classes = array(<xsl:for-each select="entity"><xsl:if test="position() != 1">,</xsl:if>'<xsl:value-of select="@name"/>'</xsl:for-each>);
	}
}
?></xsl:template>
</xsl:stylesheet>