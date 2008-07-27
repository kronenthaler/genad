
function validatePoll(f){
	return true
	&& isRequired(f.str_p01_title,"Title")
        && isRequired(f.str_p01_question,"Question")
        && isRequired(f.str_p01_start_date_date,"Starting date")
        && isRequired(f.str_p01_start_date_time,"Starting date")
        && isRequired(f.str_p01_end_date_date,"Ending date")
	&& isRequired(f.str_p01_end_date_time,"Ending date");
}
		
function validateOptions(f){
	return true
	&& isRequired(f.str_p02_label,"Label")
	&& isValidInteger(f.str_p02_votes,"Votes",false);
}
		
function validateVotes(f){
	return true
	&& isValidInteger(f.str_u01_id,"User",true);
}
		