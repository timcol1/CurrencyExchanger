package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionDB;

import java.sql.Connection;

public class JDBCDao {
    private DeploymentEnvironment deploymentEnvironment;

    public Connection getConnection() {
        return ConnectionDB.getConnection(deploymentEnvironment);
    }

    public JDBCDao(DeploymentEnvironment deploymentEnvironment) {
        this.deploymentEnvironment = deploymentEnvironment;
    }
}