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
		<script type="text/javascript" src= "getResource.php?file=js/utils.js"></script>
		
		<script type="text/javascript" src="js/validators.js"></script>
		<script type="text/javascript">
			$(document).ready(function (){
				// bind to the form's submit event 
			    $('#login').submit(function() {
					$('#loading-img').toggle();
			        // inside event callbacks 'this' is the DOM element so we first 
			        // wrap it in a jQuery object and then invoke ajaxSubmit 
			        $(this).ajaxSubmit({success: function(data, status){
									        	if(data=='0'){							
													$('#login').hide();
													$('#options').show();
													$('#logout').show();
													document.location.href='menu.php'
												}else{
													alertMsg(data);
												}
												$('#loading-img').toggle();
								        	}  // post-submit callback 
										});
			        // !!! Important !!! 
			        // always return false to prevent standard browser submit and page navigation 
			        return false; 
			    }); 
				
				$('#options').accordion({autoHeight:false,collapsible:true});
				$('#options').accordion('activate',1);
			});
		</script>
	</head>
	<body style="background-color: #eee;">
		<div id="container">
			<div id="menu">
				<!-- if authentication is passed => show the dojo menu-->
				<div id="options" style="display:none;" class="ui-accordion">
					<? include_once('../users/admin/index.php'); ?>	
					]]>
					<xsl:apply-templates select="/project/modules/module"/>
					<![CDATA[
					<h3><a href="#">Site</a></h3>
					<div id="submenu">
					<ul>]]>
					<xsl:apply-templates select="/project/entities/entity"/>
					<![CDATA[
					</ul>
					</div>
					<div id="logout" style="display:none;  border:0px;" class="ui-accordion ui-widget ui-helper-reset ui-accordion-icons">
					<h3 class="ui-accordion-header ui-helper-reset ui-state-default ui-corner-all" 
						onclick="parent.document.location.href='logoff.php'"
						onmouseover="$(this).addClass('ui-state-hover')"
						onmouseout="$(this).removeClass('ui-state-hover')">
						<span class="ui-icon ui-icon-power"></span>
						<a href="#" target="_parent"><b><?=MSG_LOGOUT?></b></a>
					</h3>
				</div>
				</div>
				<form action="authenticate.php" method="post" id="login" name="login">
					<table border="0">
						<tr>
							<td><?=MSG_LOGIN?>:</td>
							<td align="left"><input type="text" name="login" class="ui-widget ui-widget-content"/></td>
						</tr>
						<tr>
							<td><?=MSG_PASSWORD?>:</td>
							<td align="left"><input type="password" name="password" class="ui-widget ui-widget-content"/></td>
						</tr>
						<tr>
							<td><img src="images/loading.gif" style="display:none;" id="loading-img"/></td>
							<td align="left">
								<button type="submit"
										class="ui-state-default ui-corner-all"
										onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
										onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')">
									<table border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td><span class="ui-icon ui-icon-key"/><!--img src="images/key.png" align="left"/--></td>
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
		$('#logout').show();
	<? }?>
	</script>
</html>]]>
	</xsl:template>

	<xsl:template match="entity">
	<xsl:if test="just-schema/@value = 0">
	<![CDATA[<li class="ui-state-default" 
	onclick="markSelected(this); parent.mainFrame.document.location.href='list]]><xsl:value-of select="@name"/><![CDATA[.php'" 
	onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')" 
	onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><span style="vertical-align:middle;">&nbsp;<?=MSG_]]><xsl:value-of select="translate(@name,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/><![CDATA[_TITLE?></span></li>]]></xsl:if>
	</xsl:template>
	
	<xsl:template match="module"><![CDATA[<? secureInclude(']]><xsl:value-of select="@name"/><![CDATA[/admin/index.php')?>]]>
	</xsl:template>
</xsl:stylesheet>
