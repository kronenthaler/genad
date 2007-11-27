
DROP TABLE IF EXISTS `u01_users`;
CREATE TABLE  `u01_users` (
  `u01_id` bigint(30) unsigned NOT NULL auto_increment,
  `u01_login` varchar(50) default '',
  `u01_password` varchar(50) default '',
  `u01_email` varchar(50) default '',
  `u02_id` bigint(20) default NULL,
  PRIMARY KEY  (`u01_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `u01_users` VALUES  (1,'kronenthaler','MQ==','kronenthaler@gmail.com',0);

DROP TABLE IF EXISTS `u01u02_has`;
CREATE TABLE  `u01u02_has` (
  `u01_id` bigint(20) NOT NULL,
  `u01u02_id` bigint(20) NOT NULL auto_increment,
  `u02_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`u01u02_id`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

INSERT INTO `u01u02_has` VALUES  (1,32,1);

DROP TABLE IF EXISTS `u02_profiles`;
CREATE TABLE  `u02_profiles` (
  `u02_id` bigint(30) unsigned NOT NULL auto_increment,
  `u02_name` varchar(50) default '',
  PRIMARY KEY  (`u02_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `u02_profiles` VALUES  (1,'Administrator'),
 (3,'User');

DROP TABLE IF EXISTS `u02u03_has`;
CREATE TABLE  `u02u03_has` (
  `u02u03_id` bigint(1) NOT NULL auto_increment,
  `u02_id` bigint(20) default NULL,
  `u03_id` bigint(20) default NULL,
  PRIMARY KEY  (`u02u03_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `u03_permissions`;
CREATE TABLE  `u03_permissions` (
  `u03_id` bigint(30) unsigned NOT NULL auto_increment,
  `u03_name` varchar(50) default '',
  `u04_id` char(50) default NULL,
  `u03_action` varchar(50) default '',
  `u02_id` bigint(30) unsigned NOT NULL,
  PRIMARY KEY  (`u03_id`),
  KEY `parent` (`u02_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `u03_permissions` VALUES  (1,'nombre','1','asas',1);

DROP TABLE IF EXISTS `u04_sections`;
CREATE TABLE  `u04_sections` (
  `u04_id` bigint(30) unsigned NOT NULL auto_increment,
  `u04_name` varchar(50) default '',
  PRIMARY KEY  (`u04_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `u04_sections` VALUES  (1,'Admin');