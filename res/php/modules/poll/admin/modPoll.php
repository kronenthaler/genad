<?
	include_once('../../includes.php');
	
	
	$action = 'MOD';
			
	$section = 'Poll';
	include_once('../../common/checksession.php');
	
	
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past
  
	$obj=new Poll();
	
	if($_REQUEST['action']=='mod' && $_REQUEST['id']!='' && !$obj->load($_REQUEST['id']))
		die('No id found');	
	
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="../../admin/mod.xsl"?>
		  <entity name="'.get_class($obj).'" 
		  		  ext="php" 
		  		  action="'.$_REQUEST['action'].'" 
		  		  id="'.$_REQUEST['id'].'">
			<prefix>../poll/admin/</prefix>';
		echo $obj->getXMLTitle();
		echo $obj->getXMLAncestors(); 
		//begin field options
		
		$options["p01_start_date"]=array('prefix'=>'str_','date' => format($obj->p01_start_date,DATE,'M/D/Y'), 'time' => format(substr($obj->p01_start_date,8),TIME,'H:m:s'));
		$options["p01_end_date"]=array('prefix'=>'str_','date' => format($obj->p01_end_date,DATE,'M/D/Y'), 'time' => format(substr($obj->p01_end_date,8),TIME,'H:m:s'));
		$options["p01_published"]=array(
			//one array per option with the label, the value associated with, if is selected for this object or not and the javascript 'onclick' code.
			array('name'=> 'No','value' =>'0','selected'=>$obj->p01_published==0?'true':'','onclick'=>''),
			array('name'=> 'Yes','value' =>'1','selected'=>$obj->p01_published==1?'true':'','onclick'=>'')
		);
		
		//end field options
		echo $obj->getXMLForm($options);
	echo "</entity>";
?>