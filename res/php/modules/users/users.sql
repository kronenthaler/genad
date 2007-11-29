DROP TABLE IF EXISTS `u01_users`;
CREATE TABLE  `u01_users` (
  `u01_id` bigint(30) unsigned NOT NULL auto_increment,
  `u01_login` varchar(50) default '',
  `u01_password` varchar(50) default '',
  `u01_email` varchar(50) default '',
  `u02_id` bigint(20) default NULL,
  PRIMARY KEY  (`u01_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `u01_users` VALUES  (1,'admin','MQ==','root@localhost.localdomain',0);

DROP TABLE IF EXISTS `u01u02_has`;
CREATE TABLE  `u01u02_has` (
  `u01_id` bigint(20) NOT NULL,
  `u02_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`u01_id`,`u02_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `u01u02_has` VALUES  (1,1);

DROP TABLE IF EXISTS `u02_profiles`;
CREATE TABLE  `u02_profiles` (
  `u02_id` bigint(30) unsigned NOT NULL auto_increment,
  `u02_name` varchar(50) default '',
  `u03_id` bigint(20) default NULL,
  PRIMARY KEY  (`u02_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `u02_profiles` VALUES  (1,'Administrator',0),
(2,'User',0);

DROP TABLE IF EXISTS `u02u03_has`;
CREATE TABLE  `u02u03_has` (
  `u02_id` bigint(20) default NULL,
  `u03_id` bigint(20) default NULL,
  PRIMARY KEY  (`u02_id`, `u03_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;


INSERT INTO `u02u03_has` VALUES  (1,21),
 (1,15),
 (1,18),
 (1,12),
 (1,20),
 (1,14),
 (1,17),
 (1,11),
 (1,19),
 (1,13),
 (1,16),
 (1,10),
 (1,6),
 (1,7),
 (1,8),
 (1,9),
 (1,1);

DROP TABLE IF EXISTS `u03_permissions`;
CREATE TABLE  `u03_permissions` (
  `u03_id` bigint(30) unsigned NOT NULL auto_increment,
  `u03_name` varchar(50) default '',
  `u04_id` bigint(50) unsigned default NULL,
  `u03_action` varchar(50) default '',
  `u02_id` bigint(30) unsigned NOT NULL,
  PRIMARY KEY  (`u03_id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

INSERT INTO `u03_permissions` VALUES  (1,'Login',1,'LOGIN',1),
 (6,'View Users',2,'VIEW',0),
 (7,'Modify Users',2,'MOD',0),
 (8,'Delete Users',2,'DEL',0),
 (9,'Add Users',2,'ADD',0),
 
 (10,'Add Profiles',3,'ADD',0),
 (13,'Modify Profiles',3,'MOD',0),
 (16,'Delete Profiles',3,'DEL',0),
 (19,'View Profiles',3,'VIEW',0),
 
 (17,'Delete Permissions',4,'DEL',0),
 (20,'View Permissions',4,'VIEW',0),
 (14,'Modify Permissions',4,'MOD',0),
 (11,'Add Permissions',4,'ADD',0),
 
 (12,'Add Sections',5,'ADD',0),
 (15,'Modify Sections',5,'MOD',0),
 (18,'Delete Sections',5,'DEL',0),
 (21,'View Sections',5,'VIEW',0);

DROP TABLE IF EXISTS `u04_sections`;
CREATE TABLE  `u04_sections` (
  `u04_id` bigint(30) unsigned NOT NULL auto_increment,
  `u04_name` varchar(50) default '',
  PRIMARY KEY  (`u04_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO `u04_sections` VALUES  (1,'Admin'),
 (2,'Users'),
 (3,'Profiles'),
 (4,'Permissions'),
 (5,'Sections');