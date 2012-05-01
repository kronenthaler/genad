<h3><a href="#"><?=MSG_USERS_TITLE?></a></h3>
<div id="submenu">
	<ul>
		<li class="ui-state-default" 
			onclick="markSelected(this); parent.mainFrame.document.location.href='../users/admin/listUser.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')" 
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><span style="vertical-align:middle;">&nbsp;<?=MSG_USERS_USER_TITLE?></span></li>
		<li class="ui-state-default" 
			onclick="markSelected(this); parent.mainFrame.document.location.href='../users/admin/listProfile.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')" 
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><span style="vertical-align:middle;">&nbsp;<?=MSG_USERS_PROFILE_TITLE?></span></li>
		<li class="ui-state-default" 
			onclick="markSelected(this); parent.mainFrame.document.location.href='../users/admin/listPermission.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')" 
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><span style="vertical-align:middle;">&nbsp;<?=MSG_USERS_PERMISSION_TITLE?></span></li>
		<li class="ui-state-default" 
			onclick="markSelected(this); parent.mainFrame.document.location.href='../users/admin/listSection.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')" 
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><span style="vertical-align:middle;">&nbsp;<?=MSG_USERS_SECTION_TITLE?></span></li>
	</ul>
</div>