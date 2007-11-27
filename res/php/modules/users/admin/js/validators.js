
function validateProfile(f){
	return true
	&& isRequired(f.str_u02_name,"Name");
}
		
function validatePermission(f){
	return true
	&& isRequired(f.str_u03_name,"Name")
	&& isRequired(f.str_u04_section,"Section")
	&& isRequired(f.str_u03_action,"Action");
}
		
function validateSection(f){
	return true
	&& isRequired(f.str_u04_name,"Name");
}
		
function validateUser(f){
	return true
	&& isRequired(f.str_u01_login,"Login")
	&& isValidPassword(f.str_u01_password,f.conf_str_u01_password,"Password",true)
	&& isValidEmail(f.str_u01_email,"Email",true);
}
		