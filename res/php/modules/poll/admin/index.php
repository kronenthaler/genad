<h3><a href="#"><?=MSG_POLL_TITLE?></a></h3>
<div style="" id="submenu">
	<ul style="margin: 0px; list-style-type: none; padding:0px; overflow:hidden;">
		<li class="ui-state-default" 
			onclick="markSelected(this); parent.mainFrame.document.location.href='../poll/admin/listPoll.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')" 
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><span style="vertical-align:middle;">&nbsp;<?=MSG_POLL_TITLE?></span></li>
	</ul>
</div>
