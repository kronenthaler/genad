<?
class Modules{
	var $enabledModules;
	
	function Modules(){
		$this->enabledModules = array();
	}
	
	function enableModule($name){
		$this->enabledModules[$name]=true;
	}	
	
	function disableModule($name){
		$this->enabledModules[$name]=false;
	}
};
$_SESSION['modules']=new Modules();
?>