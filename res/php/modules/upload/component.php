<? 
$name = $_REQUEST['name'];
header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past
?>
<html>
<head>
<link href="../admin/css/styles.css" rel="stylesheet" type="text/css"/>
<script>
function touch(){
	document.getElementById('str_<?=$name?>').value='changed';
	parent.document.getElementById('str_<?=$name?>').value='changed';
	
	document.getElementById('btn_del').disabled=false;
}

function clean(){
	document.getElementById('thefile').innerHTML='<input type="file" name="file_<?=$name?>" id="file_<?=$name?>" onclick="touch()"/>';	
	document.getElementById('str_<?=$name?>').value='';
	parent.document.getElementById('str_<?=$name?>').value='';
	
	document.getElementById('btn_del').disabled=true;
}

<? 
if($_REQUEST['finish']==1){
	if($_REQUEST['path']!=''){?>
		parent.document.getElementById('str_<?=$name?>').value='<?=$_REQUEST['path']?>';
		parent.upload.uploadReady("<?=$name?>", parent.upload.READY);//ready
<?	}else{//error?>
		//parent.document.getElementById('str_<?=$name?>').value='<?=$_REQUEST['path']?>';
		clean();
		parent.upload.uploadReady("<?=$name?>", parent.upload.WRONG);//wrong
<?	}
}
?>
</script>

</head>
<body>
<div id="container">
	<div id="center">
	<table class="plain" cellpadding="0" cellspacing="0" border="0">
		<tr valign="middle"><td  align="left">
			<? if($_REQUEST['path']==''){?>
			<form action="process.php" method="POST" enctype="multipart/form-data" name="frm_up" id="frm_up" onsubmit="return true;">
				<label id="thefile" class="cabinet"><input class="file" type="file" name="file_<?=$name?>" id="file_<?=$name?>" onclick="touch()"/></label>
				<button type="button" id="btn_del" onclick="clean()" disabled="<?=$_REQUEST['value']==''?'true':'false'?>"><img src="../admin/images/delete.png" align="left"/><span>Remove</span></button>
				<!--img src="../upload/images/spacer.gif" id="loading" align="absmiddle" /-->
				<? if($_REQUEST['error']!=''){ ?>
				<a href="#" onclick="alert('<?=$_REQUEST['error']?>')" id="error"><img src="../admin/images/cancel.png" title="<?=str_replace("\\'","'",$_REQUEST['error'])?>" align="absmiddle" border="0"/> Error uploading the file</a>
				<? }
				  foreach($_REQUEST as $key => $value)
				   	if($key!='finish' && $key!='error' && $key!='path')
						echo '<input type="hidden" name="'.$key.'_'.$name.'" value="'.$value.'"/>';
					
				?>
				<input type="hidden" name="fieldName" value="<?=$name?>"/>
				<input type="hidden" name="str_<?=$name?>" id="str_<?=$name?>" value="<?=$_REQUEST['value']?>"/>
			</form>
			<? }else{ ?>
			<a href="../<?=$_REQUEST['path']?>" target="_blank"><img src="../admin/images/accept.png" border="0" align="absmiddle"/> File uploaded successfully</a>
			<? }?>
		</td></tr>
	</table>
	</div>
</div>
</body>
</html>