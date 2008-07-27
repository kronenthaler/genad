<?
	include('../includes.php');
	
	$poll=new Poll();
	
	if($_REQUEST['p02_id']!=''){
		$poll->castVote($_REQUEST['p01_id'],$_REQUEST['p02_id']);
	}
	
	$actives = $poll->getActivePolls();
	for($i=0,$n=count($actives);$i<$n;$i++){?>	
		<form action="?">
<?		echo $actives[$i]->p01_question.'<br/>';
		$opts = $actives[$i]->getOptions();
		for($j=0,$m=count($opts);$j<$m;$j++){?>
			<input type="radio" name="p02_id" value="<?=$opts[$j]->p02_id?>"><?=$opts[$j]->p02_label?></input><br/>
<?		}?>
			<input type="hidden" name="p01_id" value="<?=$actives[$i]->p01_id?>"/>
			<input type="submit" name="Vote"/>
		</form>
<?	}?>
