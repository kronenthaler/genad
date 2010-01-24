<script type="text/javascript" src="../users/admin/js/validators.js"></script>
<div id="useraccordion">
	<div style="padding-top:5px;padding-bottom:5px;padding-left:5px;"
		 class="ui-state-active ui-accordion-header"
		 onclick="$('#users').toggle()
				  $('#users-img').attr('src',$('#users').css('display')=='none'?
					  'images/expand.gif':
					  'images/collapse.gif');
				  $('#users').css('display')=='none'?
					  $(this).removeClass('ui-state-active').addClass('ui-state-default'):
					  $(this).addClass('ui-state-active').removeClass('ui-state-default')"
		onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
		onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"
		>
		 <img src="images/collapse.gif" id="users-img" align="absmiddle"/>&nbsp;<?=MSG_USERS_TITLE?>
	</div>
	<!--h3><?=MSG_USERS_TITLE?></h3-->
	<ul id="users">
		<li class="ui-state-default"
			onclick="markSelected(this);
					 parent.mainFrame.document.location.href='../users/admin/listUser.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><?=MSG_USERS_USERS?></li>
		<li class="ui-state-default"
			onclick="markSelected(this);
					 parent.mainFrame.document.location.href='../users/admin/listProfile.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><?=MSG_USERS_PROFILES?></li>
		<li class="ui-state-default"
			onclick="markSelected(this);
					 parent.mainFrame.document.location.href='../users/admin/listPermission.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><?=MSG_USERS_PERMISSIONS?></li>
		<li class="ui-state-default"
			onclick="markSelected(this);
					 parent.mainFrame.document.location.href='../users/admin/listSection.php'"
			onmouseover="$(this).addClass('ui-state-hover').removeClass('ui-state-default')"
			onmouseout="$(this).removeClass('ui-state-hover').addClass('ui-state-default')"><?=MSG_USERS_SECTIONS?></li>
	</ul>
</div>
<!--script>
	$('#useraccordion').accordion({collapsible:true,fillSpace:true});
</script-->