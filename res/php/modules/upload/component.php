<? include_once('../includes.php');?>
<? $name = $_REQUEST['name']; ?>
<html>
<head>
<link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
<link href="../admin/css/ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src= "../js/jquery.js"></script>
<script type="text/javascript" src= "../js/jquery-ui.js"></script>
<style>
	/* force the top padding */
	body{
		padding-top: 2px;
	}
	
	/*  force the table width */
	#container table{
		width: 100%;
	}
</style>
<script type="text/javascript">
	//document.getElementById("stylus").href=parent.document.styleSheets[0].href; //must be the main style file
	var anychange = false;
	function touch(){
		document.getElementById('str_<?=$name?>').value='changed';
		parent.document.getElementById('str_<?=$name?>').value='changed';

		document.getElementById('btn_del').disabled=false;
		anychange = true;
	}

	function clean(complete){
		document.getElementById('thefile').innerHTML='<input type="file" name="file_<?=$name?>" id="file_<?=$name?>" onclick="touch()"/>';
		document.getElementById('btn_del').disabled=true;

		document.getElementById('str_<?=$name?>').value='';
		parent.document.getElementById('str_<?=$name?>').value='';

		if(complete){ //i want remove it
			var img = parent.document.getElementById('img_link_<?=$name?>');
			if(img!=null) img.src = 'images/0.gif';
			anychange = true;
		}else //upload failed
			parent.document.getElementById('str_<?=$name?>').value='<?= $_REQUEST["prev"];?>';

		var link = parent.document.getElementById('link_<?=$name?>');
		if(link && complete) link.style.display='none';
	}
</script>
</head>
<body>
<div id="container">
	<div id="content">
	<form action="process.php" method="POST" enctype="multipart/form-data" name="frm_up" id="frm_up" onsubmit="return true;">
		<table cellpadding="0" cellspacing="0" border="0" width="100%" >
			<tr valign="middle">
				
					<? if($_REQUEST['path']==''){?>
						<td class="plain" align="left" id="thefile" width="1%">
						<input class="ui-widget ui-widget-content" type="file" name="file_<?=$name?>" id="file_<?=$name?>" onchange="touch()"/>
						</td>
						<td class="plain" align="left">
						<button type="button"
								id="btn_del"
								onclick="clean(true)"
								class="ui-state-default ui-corner-all"
								onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
								onmouseout="$(this).addClass('ui-state-default').removeClass('ui-state-hover')">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td><img src="../admin/images/delete.png" align="left"/></td>
									<td align="right"><a><?=MSG_UPLOAD_REMOVE?></a></td>
								</tr>
							</table>
						</button>
						<? if($_REQUEST['error']!=''){ ?>
						</td><td width="100%" class="ui-state-highlight ui-corner-all" align="left">
						<a style="padding:5px;"
						   href="javascript:void(0);"
						   onclick="alert('<?=$_REQUEST['error']?>')" id="error">
							<img src="../admin/images/cancel.png" title="<?=str_replace("\\'","'",$_REQUEST['error'])?>" align="absmiddle" border="0" style="border:0px;"/> <?=MSG_UPLOAD_ERROR_GUI?>
						</a>
						<? }
						  foreach($_REQUEST as $key => $value)
						   	if($key!='finish' && $key!='error' && $key!='path')
								echo '<input type="hidden" name="'.$key.'_'.$name.'" value="'.$value.'"/>';
							
						?>
						<input type="hidden" name="fieldName" value="<?=$name?>"/>
						<input type="hidden" name="str_<?=$name?>" id="str_<?=$name?>" value="<?=$_REQUEST['value']?>"/>
					</td>
					<? }else{ ?>
					<td width="100%" class="ui-state-highlight ui-corner-all" align="left">
					<a style="padding:5px;"
					   href="../<?=$_REQUEST['path']?>"
					   target="_blank"><img src="../admin/images/accept.png" border="0" align="absmiddle"/>&nbsp;<?=MSG_UPLOAD_SUCCESSFULLY?></a>
					</td>
					<? }?>
				
			</tr>
		</table>
	</form>
	</div>
	</div>
<!--/div-->
</body>
<script type="text/javascript">
<?
if($_REQUEST['finish']==1){
	if($_REQUEST['error']==''){?>
		parent.document.getElementById('str_<?=$name?>').value='<?=$_REQUEST['path']?>';
		parent.upload.uploadReady("<?=$name?>", parent.upload.READY);//ready
<?	}else{//error ?>
		clean(false);
		parent.upload.uploadReady("<?=$name?>", parent.upload.WRONG);//wrong
<?	}
}
?>
</script>
</html>
