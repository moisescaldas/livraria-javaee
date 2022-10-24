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
	private static final Logger LOGGER = LoggerFactory.getLogger(J2eeDBMigration.class);
	
	private static final String NO_DATASOURCE_FOUND = "no datasource found to execute the db migrations!";
	private static final String NO_DATABASE_FOUND = "no existing database at the actual datasource";
	
	@Resource(mappedName = "java:jboss/mysql/livraria")
	DataSource dataSource;
	
	@PostConstruct
	public void onStartup() {
		if (dataSource == null) {
			LOGGER.error(NO_DATASOURCE_FOUND);
			throw new EJBException(NO_DATASOURCE_FOUND);
		}

		Flyway flyway = Flyway.configure().dataSource(dataSource).load();
		MigrationInfo migrationInfo = flyway.info().current();

		if (migrationInfo == null) {
			LOGGER.info(NO_DATABASE_FOUND);
		} else {
			LOGGER.info("Found a database with the version: {}",
					migrationInfo.getVersion() + " : " + migrationInfo.getDescription());
		}

		flyway.migrate();
        LOGGER.info("Successfully migrated to database version: {}", flyway.info().current().getVersion());
	}
}
