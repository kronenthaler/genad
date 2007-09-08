<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/">
<![CDATA[<!-- one page to rule them all, changing the ajax calls-->
<html>
	<head>
		<meta http-equiv="Expires" content="Tue, 01 Jan 2000 12:12:12 GMT"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		
		<link href="css/styles.css" rel="stylesheet" type="text/css"/>
		<script src="../js/dojo/dojo.js"></script>
		<script src="../js/tinymce/tiny_mce.js"></script>
		<script src="../js/jquery.js"></script>
		<script src="../js/utils.js"></script>
		<script src="../js/validators.js"></script>
		<script>
			dojo.require("dojo.widget.*");
			dojo.require("dojo.lang.*");
			dojo.require("dojo.io.*");
			dojo.hostenv.writeIncludes();
			
			dojo.addOnLoad(function (){
				new dojo.io.FormBind({
					formNode: document.forms.login,
					handle: function(type, data, evt) {  },
					load: function(load, data,evt){ 
						dojo.byId('login').style.display='none';
						dojo.byId('options').style.display='block';
					},
					mimetype: "text/html"
				});
			});
			
			tinyMCE.init({mode: 'none', theme:'simple'});
		</script>
	</head>
	<body>
		<div id="container">
			<div id="header"></div>
			<div id="menu">
				<!-- if authentication is passed => show the dojo menu-->
				<ul id="options" style="display:none">
					<li dojoType="TitlePane" label="Title Pane #1" labelNodeClass="submenulabel" containerNodeClass="menucontent">
						<ul>
							<li><a href=""><img src="images/bullet.png" align="absmiddle"/>item</a></li>
							<li><a href="javascript:getAndTransform('mod.xml','mod.xsl','center')"><img src="images/bullet.png" align="absmiddle"/>Mod</a></li>
						</ul>
					</li>]]>
					<xsl:apply-templates select="/project/entities/entity"/>
					<![CDATA[<li><a href=""><img src="images/logout.png" align="absmiddle"/>&#160;<b>Log out</b></a></li>
				</ul>
				<form action="?" method="post" id="login" name="login">
					<table border="0"> 
						<tr>
							<td>Login:</td>
							<td><input type="text" name="login"/></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><input type="password" name="password"/></td>
						</tr>
						<tr>
							<td colspan="2">
								<button type="submit"><img src="images/key.png" align="left"/><span>Enter</span></button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- include here the content list -->
			<div id="center"/>
		</div>
	</body>
</html>]]>
	</xsl:template>

	<xsl:template match="entity">
	<![CDATA[<li><a href="javascript:getAndTransform('list]]><xsl:value-of select="@name"/><![CDATA[.php','list.xsl','center')"><img src="images/bullet.png" align="absmiddle"/>]]><xsl:value-of select="@title"/><![CDATA[</a></li>]]>
	</xsl:template>
</xsl:stylesheet>