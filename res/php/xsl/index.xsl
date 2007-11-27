<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/"><![CDATA[<? include("../includes.php"); ?>
<!-- one page to rule them all, changing the ajax calls-->
<html>
	<head>
		<meta http-equiv="Expires" content="Tue, 01 Jan 2000 12:12:12 GMT"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		
		<link href="css/styles.css" rel="stylesheet" type="text/css"/>
		<script src="../js/dojo/dojo.js"></script>
		<script src="../js/tinymce/tiny_mce.js"></script>
		<script src="../js/jquery.js"></script>
		<script src="../js/utils.js"></script>
		<script src="../upload/js/upload.js"></script>
		<script src="js/validators.js"></script>
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
						if(data=='0'){
							dojo.byId('login').style.display='none';
							dojo.byId('options').style.display='block';
						}else{
							alert(data);
						}
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
					<? include_once('../users/admin/index.php'); ?>	
					]]>
					<xsl:apply-templates select="/project/modules/module"/>
					<xsl:apply-templates select="/project/entities/entity"/>
					<![CDATA[<li><a href="logoff.php"><img src="images/logout.png" align="absmiddle"/>&#160;<b>Log out</b></a></li>
				</ul>
				<form action="authenticate.php" method="post" id="login" name="login">
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
								<button type="submit">
									<table border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td><img src="images/key.png" align="left"/></td>
											<td>Enter</td></tr>
									</table>
								</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- include here the content list -->
			<div id="center"/>
		</div>
	</body>
	<script>
	<? if($_SESSION['user_id']!=NULL){?>
		dojo.byId('login').style.display='none';
		dojo.byId('options').style.display='block';
	<? }?>
	</script>
</html>]]>
	</xsl:template>

	<xsl:template match="entity">
	<![CDATA[<li><a href="javascript:getAndTransform('list]]><xsl:value-of select="@name"/><![CDATA[.php','list.xsl','center')"><img src="images/bullet.png" align="absmiddle"/>]]><xsl:value-of select="@title"/><![CDATA[</a></li>]]>
	</xsl:template>
	
	<xsl:template match="module">
	<![CDATA[<? secureInclude('../]]><xsl:value-of select="@name"/><![CDATA[/admin/index.php')?>]]>
	</xsl:template>
</xsl:stylesheet>