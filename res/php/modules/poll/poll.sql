DROP TABLE IF EXISTS `p01_poll`;
CREATE TABLE `p01_poll` (
  `p01_id` bigint(30) unsigned NOT NULL auto_increment,
  `p01_title` varchar(50) default '',
  `p01_question` varchar(50) default '',
  `p01_start_date` varchar(14) default '',
  `p01_end_date` varchar(14) default '',
  `p01_published` int(10) default '0',
  PRIMARY KEY  (`p01_id`)
) ENGINE=MyISAM;

INSERT INTO u04_sections (u04_name) VALUES ('Poll');
INSERT INTO u03_permissions (u03_name, u03_action, u02_id, u04_id) VALUES 
	('View on Poll','VIEW',1, last_insert_ID()),
	('Add on Poll','ADD',1, last_insert_ID()),
	('Modify on Poll','MOD',1, last_insert_ID()),
	('Delete on Poll','DEL',1, last_insert_ID());


#Entity creation
DROP TABLE IF EXISTS `p02_options`;
CREATE TABLE `p02_options` (
  `p02_id` bigint(30) unsigned NOT NULL auto_increment,
  `p02_label` varchar(50) default '',
  `p02_votes` int(10) default '0',
  `p01_id` bigint(30) unsigned NOT NULL,
  `_sort` bigint(10) NOT NULL,
  PRIMARY KEY  (`p02_id`),
  KEY `parent` (`p01_id`)
) ENGINE=MyISAM;

INSERT INTO u04_sections (u04_name) VALUES ('Options');
INSERT INTO u03_permissions (u03_name, u03_action, u02_id, u04_id) VALUES 
	('Sort on Options','SORT',1, last_insert_ID()),
	('View on Options','VIEW',1, last_insert_ID()),
	('Add on Options','ADD',1, last_insert_ID()),
	('Modify on Options','MOD',1, last_insert_ID()),
	('Delete on Options','DEL',1, last_insert_ID());

#Entity creation
DROP TABLE IF EXISTS `p03_votes`;
CREATE TABLE `p03_votes` (
  `p03_id` bigint(30) unsigned NOT NULL auto_increment,
  `u01_id` int(10) default '0',
  `p02_id` bigint(30) unsigned NOT NULL,
  PRIMARY KEY  (`p03_id`),
  KEY `parent` (`p02_id`)
) ENGINE=MyISAM;

INSERT INTO u04_sections (u04_name) VALUES ('Votes');
INSERT INTO u03_permissions (u03_name, u03_action, u02_id, u04_id) VALUES 
	('View on Votes','VIEW',1, last_insert_ID()),
	('Add on Votes','ADD',1, last_insert_ID()),
	('Modify on Votes','MOD',1, last_insert_ID()),
	('Delete on Votes','DEL',1, last_insert_ID());