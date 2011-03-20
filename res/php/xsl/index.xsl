<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/"><![CDATA[<? include("../includes.php"); ?>
<!-- one page to rule them all, changing the ajax calls-->
<html>
	<head>
		<meta http-equiv="Expires" content="Tue, 01 Jan 2000 12:12:12 GMT"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		
		<link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
		<link href="../admin/css/ui.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src= "../js/jquery.js"></script>
		<script type="text/javascript" src= "../js/jquery-ui.js"></script>
		<script type="text/javascript" src= "../js/jquery.form.js"></script>
		<script type="text/javascript" src= "../js/json.js"></script>
		<script type="text/javascript" src= "../js/utils.js"></script>
		<script type="text/javascript" src= "../js/ui/ui.core.js"></script>
		<script type="text/javascript" src= "../js/ui/ui.accordion.js"></script>
		<script type="text/javascript" src= "../js/ui/ui.dialog.js"></script>
		
		<script type="text/javascript" src="js/validators.js"></script>
		<script type="text/javascript">
			$(document).ready(function (){
				// bind to the form's submit event 
			    $('#login').submit(function() { 
			        // inside event callbacks 'this' is the DOM element so we first 
			        // wrap it in a jQuery object and then invoke ajaxSubmit 
			        $(this).ajaxSubmit({success: function(data, status){
									        	if(data=='0'){							
													$('#login').hide();
													$('#options').show();	
												}else{
													alert(data);
												}
								        	}  // post-submit callback 
										});
			        // !!! Important !!! 
			        // always return false to prevent standard browser submit and page navigation 
			        return false; 
			    }); 
			});
		</script>
	</head>
	<body style="background-color: #eee;">
		<div id="container">
			<div id="menu">
				<!-- if authentication is passed => show the dojo menu-->
				<ul id="options" style="display:none" class="ui-accordion">
					<? include_once('../users/admin/index.php'); ?>	
					]]>
					<xsl:apply-templates select="/project/modules/module"/>
					<xsl:apply-templates select="/project/entities/entity"/>
					<![CDATA[<li class="ui-state-default" onclick="markSelected(this); parent.document.location.href='logoff.php'" onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')" onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><img src="images/logout.png" align="absmiddle"/>&#160;<?=MSG_LOGOUT?></li>
				</ul>
				<form action="authenticate.php" method="post" id="login" name="login">
					<table border="0">
						<tr>
							<td><?=MSG_LOGIN?>:</td>
							<td><input type="text" name="login"/></td>
						</tr>
						<tr>
							<td><?=MSG_PASSWORD?>:</td>
							<td><input type="password" name="password"/></td>
						</tr>
						<tr>
							<td colspan="2">
								<button type="submit"
										class="ui-state-default ui-corner-all"
										onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
										onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')">
									<table border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td><img src="images/key.png" align="left"/></td>
											<td align="left"><a><?=MSG_ENTER?></a></td>
										</tr>
									</table>
								</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	<? if($_SESSION['user_id']!=NULL){?>
		$('#login').hide();
		$('#options').show();
	<? }?>
	</script>
</html>]]>
	</xsl:template>

	<xsl:template match="entity">
	<xsl:if test="just-schema/@value = 0">
	<![CDATA[<li class="ui-state-default" onclick="markSelected(this); parent.mainFrame.document.location.href='list]]><xsl:value-of select="@name"/><![CDATA[.php'" onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')" onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><?=MSG_]]><xsl:value-of select="translate(@name,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/><![CDATA[_TITLE?></li>]]></xsl:if>
	</xsl:template>
	
	<xsl:template match="module"><![CDATA[<? secureInclude(']]><xsl:value-of select="@name"/><![CDATA[/admin/index.php')?>]]>
	</xsl:template>
</xsl:stylesheet>
