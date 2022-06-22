/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2022, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/
package schemacrawler.integration.test;

import static org.junit.jupiter.api.Assertions.fail;
import static schemacrawler.integration.test.utility.OracleTestUtility.newOracle21Container;
import static schemacrawler.test.utility.TestUtility.javaVersion;

import java.sql.Connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import schemacrawler.test.utility.HeavyDatabaseTest;

@HeavyDatabaseTest
@Testcontainers
public class OracleTest extends BaseOracleWithConnectionTest {

  @Container private final JdbcDatabaseContainer<?> dbContainer = newOracle21Container();

  @BeforeEach
  public void createDatabase() {

    if (!dbContainer.isRunning()) {
      fail("Testcontainer for database is not available");
    }

    final String urlx = "restrictGetTables=true;useFetchSizeWithLongColumn=true";
    createDataSource(dbContainer.getJdbcUrl(), "SYS AS SYSDBA", dbContainer.getPassword(), urlx);

    createDatabase("/oracle.scripts.txt");
  }

  @Test
  public void testOracleWithConnection() throws Exception {
    final Connection connection = getConnection();
    final String expectedResource = String.format("testOracleWithConnection.%s.txt", javaVersion());
    testOracleWithConnection(connection, expectedResource, 33);

    testSelectQuery(connection, "testOracleWithConnectionQuery.txt");
  }
}
