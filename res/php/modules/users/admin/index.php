<script type="text/javascript" src="../users/admin/js/validators.js"></script>
<div id="useraccordion">
	<table class="ui-state-active"
		   cellspacing="0"
		   onclick="$('#users').toggle()
				  $('#users').css('display')=='none'?
					$('#users-img').removeClass('ui-icon-triangle-1-s').addClass('ui-icon-triangle-1-e'):
					$('#users-img').removeClass('ui-icon-triangle-1-s').addClass('ui-icon-triangle-1-s');
				  $('#users').css('display')=='none'?
					  $(this).removeClass('ui-state-active').addClass('ui-state-default'):
					  $(this).addClass('ui-state-active').removeClass('ui-state-default')"
		>
		<tr onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"
			height="25px">
			<td width="5px"><span id="users-img" class="ui-icon ui-icon-triangle-1-s"/></td>
			<td align="left"><?=MSG_USERS_TITLE?></td>
		</tr>
	</table>
	<ul id="users">
		<li class="ui-state-default"
			onclick="markSelected(this);
					 parent.mainFrame.document.location.href='../users/admin/listUser.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><?=MSG_USERS_USER_TITLE?></li>
		<li class="ui-state-default"
			onclick="markSelected(this);
					 parent.mainFrame.document.location.href='../users/admin/listProfile.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><?=MSG_USERS_PROFILE_TITLE?></li>
		<li class="ui-state-default"
			onclick="markSelected(this);
					 parent.mainFrame.document.location.href='../users/admin/listPermission.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><?=MSG_USERS_PERMISSION_TITLE?></li>
		<li class="ui-state-default"
			onclick="markSelected(this);
					 parent.mainFrame.document.location.href='../users/admin/listSection.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><?=MSG_USERS_SECTION_TITLE?></li>
	</ul>
</div>