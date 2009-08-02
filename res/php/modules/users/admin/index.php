<script src="../users/admin/js/validators.js"></script>
<div class="ui-accordion-group">
	<div class="submenulabel"
		 onclick="$('#users').toggle();
				  $('#users-img').attr('src',$('#users').css('display')=='none'?
					  'images/expand.gif':
					  'images/collapse.gif')">
		 <img src="images/collapse.gif" id="users-img" align="absmiddle"/>&nbsp;<?=MSG_USERS_TITLE?>
	</div>
	<div class="menucontent" id="users">
		<ul>
			<li><a href="../users/admin/listUser.php" target="mainFrame"  onclick="markSelected(this)"><?=MSG_USERS_USERS?></a></li>
			<li><a href="../users/admin/listProfile.php" target="mainFrame"  onclick="markSelected(this)"><?=MSG_USERS_PROFILES?></a></li>
			<li><a href="../users/admin/listPermission.php" target="mainFrame"  onclick="markSelected(this)"><?=MSG_USERS_PERMISSIONS?></a></li>
			<li><a href="../users/admin/listSection.php" target="mainFrame"  onclick="markSelected(this)"><?=MSG_USERS_SECTIONS?></a></li>
		</ul>
	</div>
</div>
