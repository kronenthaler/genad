<?
/**
 * LIST OF MESSAGES PRODUCED OR USED BY PHP CLASSES:
 * 1. The title of the fields in each class must be modified in that class. The same
 * apply for the validator scripts. Coming soon that will be changed to make it more
 * flexible and effortless. Meanwhile, you must do it by hand.
 * 2. Keep the placeholders use.
 */

//MONTH'S NAMES
$months = array('01'=>'January',
				'02'=>'February',
				'03'=>'March',
				'04'=>'April',
				'05'=>'May',
				'06'=>'June',
				'07'=>'July',
				'08'=>'August',
				'09'=>'September',
				'10'=>'October',
				'11'=>'November',
				'12'=>'Dicember');
//DAY'S NAMES
$days = array('',
			  'Monday',
			  'Tuesday',
			  'Wednesday',
			  'Thursday',
			  'Friday',
			  'Saturday',
			  'Sunday');

//MAIN MENU
define('MSG_LOGIN','Login');
define('MSG_PASSWORD','Password');
define('MSG_LOGOUT','Logout');
define('MSG_ENTER','Enter');

//GENERALS MESSAGES
define('MSG_NO_ACTION','No action especified');
define('MSG_UNDEFINED_ACTION','Undefined action.');
define('MSG_UNDEFINED_SECTION','Undefined section.');
define('MSG_SESSION_EXPIRED','Session expired, you must log in again.');
define('MSG_INVALID_USER','Unknown/invalid user.');
define('MSG_PERMISSIONS_REQUIRED','This section requires especial permissions and you haven\'t.');
define('MSG_INVALID_LOGIN',"Invalid login or password");
define('MSG_ERROR_GENERIC','Error');

//JS validations (utils.js)
define('MSG_IS_REQUIRED','The "%1" field is required. Cannot be left in blank.');
define('MSG_INVALID_PASSWORD','The field "%1" does not match with the confirmation.');
define('MSG_INVALID_EMAIL','The field "%1" only can be a valid email address.');
define('MSG_INVALID_INTEGER','The field "%1" only can be integers.');
define('MSG_INVALID_DECIMAL','The field "%1" only can be decimals.');

define("MSG_ADD",'Add');
define("MSG_DELETE",'Delete');
define("MSG_SORT",'Sort');
define("MSG_FORBIDDEN",'Forbidden!');
define("MSG_SEARCH",'Search');
define("MSG_NOTHING",'Nothing to show');
define("MSG_SHOWING",'Showing');
define("MSG_EDIT",'Edit');
define("MSG_CANCEL",'Cancel');
define("MSG_APPLY",'Apply');
define("MSG_UP",'Up');
define("MSG_DOWN",'Down');
define("MSG_PREV",'Prev');
define("MSG_NEXT",'Next');
define("MSG_CONFIRM_DELETE",'Do you really want to delete the selected items?');
define("MSG_ADDING",'Adding');
define("MSG_EDITING",'Editing');
define("MSG_SORTING",'Sorting');
define("MSG_LISTING",'Listing');
?>
