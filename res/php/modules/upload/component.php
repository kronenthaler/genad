<? include_once('../includes.php');?>
<? $name = $_REQUEST['name']; ?>
<html>
<head>
<link href="../admin/css/ui.css" rel="stylesheet" type="text/css"/>
<link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src= "../js/jquery.js"></script>
<script type="text/javascript" src= "../js/jquery-ui.js"></script>
<script type="text/javascript" src= "../admin/getResource.php?file=js/utils.js"></script>
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
		$('#btn_del').removeClass('ui-state-disabled');
		anychange = true;
	}

	function clean(complete){
		document.getElementById('thefile').innerHTML='<input type="file" name="file_<?=$name?>" id="file_<?=$name?>" class="ui-widget ui-widget-content" onclick="touch()"/>';
		document.getElementById('btn_del').disabled=true;
		$('#btn_del').addClass('ui-state-disabled');

		document.getElementById('str_<?=$name?>').value='';
		parent.document.getElementById('str_<?=$name?>').value='';

		if(complete){ //i want remove it
			var img = parent.document.getElementById('img_link_<?=$name?>');
			if(img!=null) img.src = 'images/0.gif';
			anychange = '<?= $_REQUEST["prev"];?>'=='' ? false : true ;
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
									<td><!--img src="../admin/images/delete.png" align="left"/--><span class="ui-icon ui-icon-minusthick"/></td>
									<td align="right"><a><?=MSG_UPLOAD_REMOVE?></a></td>
								</tr>
							</table>
						</button>
						<? if($_REQUEST['error']!=''){ ?>
						</td><td width="100%" align="left">
						<div class="ui-state-highlight ui-corner-all" style="height:24px;" id="error">
							<p style="margin:4px;">
								<span class="ui-icon ui-icon-closethick" style="float:left;"></span>
								<a href="#"
								   onclick="alert('<?=addslashes($_REQUEST['error'])?>')" >
									<?=MSG_UPLOAD_ERROR_GUI?>
								</a>
							</p>
						</div>
						<? }
						  foreach($_REQUEST as $key => $value)
						   	if($key!='finish' && $key!='error' && $key!='path')
								echo '<input type="hidden" name="'.$key.'_'.$name.'" value="'.$value.'"/>';
							
						?>
						<input type="hidden" name="fieldName" value="<?=$name?>"/>
						<input type="hidden" name="str_<?=$name?>" id="str_<?=$name?>" value="<?=$_REQUEST['value']?>"/>
					</td>
					<? }else{ ?>
					<td width="100%" align="left">
						<div class="ui-state-highlight ui-corner-all" style="height:24px;">
							<p style="margin:4px;">
								<span class="ui-icon ui-icon-check" style="float:left;"></span>
								<a href="../<?=$_REQUEST['path']?>"
								   target="_blank">
									<?=MSG_UPLOAD_SUCCESSFULLY?>
								</a>
							</p>
						</div>
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
