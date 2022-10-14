package org.livraria.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
@TransactionManagement(value = TransactionManagementType.BEAN)
public class J2eeDBMigration {
	private final Logger log = LoggerFactory.getLogger(J2eeDBMigration.class);

	@Resource(mappedName = "java:jboss/mysql/livraria")
	DataSource dataSource;
	
	@PostConstruct
	public void onStartup() {
		if (dataSource == null) {
			log.error("no datasource found to execute the db migrations!");
			throw new EJBException("no datasource found to execute the db migrations!");
		}

		Flyway flyway = Flyway.configure().dataSource(dataSource).load();
		MigrationInfo migrationInfo = flyway.info().current();

		if (migrationInfo == null) {
			log.info("No existing database at the actual datasource");
		} else {
			log.info("Found a database with the version: {}",
					migrationInfo.getVersion() + " : " + migrationInfo.getDescription());
		}

		flyway.migrate();
        log.info("Successfully migrated to database version: {}", flyway.info().current().getVersion());
	}
}
