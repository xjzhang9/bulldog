package org.minbox.framework.bulldog.storage.database.executor.service;

import org.minbox.framework.bulldog.common.utils.Assert;
import org.minbox.framework.bulldog.storage.database.executor.QueryDataExecutor;
import org.minbox.framework.bulldog.storage.database.executor.mapping.parameter.IntegerParameterTypeMapping;
import org.minbox.framework.bulldog.storage.database.executor.mapping.parameter.ParameterTypeMapping;
import org.minbox.framework.bulldog.storage.database.executor.mapping.parameter.StringParameterTypeMapping;
import org.minbox.framework.bulldog.storage.database.executor.variable.ParameterVariable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.minbox.framework.bulldog.storage.database.executor.variable.VariableKeys.*;

/**
 * Select serviceId executor
 *
 * @author 恒宇少年
 */
public class SelectServiceIdDataExecutor extends QueryDataExecutor<String> {
    /**
     * Select ServiceDetails Id SQL
     */
    private static final String SQL_SELECT_SERVICE_DETAILS_ID = "select service_id\n" +
            "from bulldog_service_instance\n" +
            "where service_name = ?\n" +
            "  and service_ip = ?\n" +
            "  and service_port = ?\n" +
            "limit 1;";
    private static final String COLUMN_NAME_SERVICE_ID = "service_id";

    @Override
    public String getSql() {
        return SQL_SELECT_SERVICE_DETAILS_ID;
    }

    @Override
    protected void preExecute(ParameterVariable variable) {
        Assert.notEmpty(variable.getVariable(SERVICE_NAME), "The ServiceName cannot be empty.");
        Assert.notEmpty(variable.getVariable(SERVICE_IP), "The ServiceIp cannot be empty.");
        Assert.notEmpty(variable.getVariable(SERVICE_PORT), "The ServicePort cannot be empty.");
    }

    /**
     * Set sql placeholder parameters
     *
     * @param variable Parametric variable collection object
     * @return The {@link ParameterTypeMapping} collection
     */
    @Override
    public List<ParameterTypeMapping> getParameterMappings(ParameterVariable variable) {
        return Arrays.asList(
                new StringParameterTypeMapping(1, variable.getVariable(SERVICE_NAME)),
                new StringParameterTypeMapping(2, variable.getVariable(SERVICE_IP)),
                new IntegerParameterTypeMapping(3, variable.getVariable(SERVICE_PORT))
        );
    }

    /**
     * Encapsulate the mapping result set and add the result to the variable set
     *
     * @param resultSet The select {@link ResultSet} instance
     * @param variable  The variable collect
     * @throws SQLException sql exception
     */
    @Override
    public void mappingResult(ResultSet resultSet, ParameterVariable variable) throws SQLException {
        while (resultSet.next()) {
            String serviceId = resultSet.getString(COLUMN_NAME_SERVICE_ID);
            variable.putVariable(SERVICE_ID, serviceId);
        }
    }

    /**
     * After execute sql
     *
     * @param variable Parametric variable collection object
     * @return Return {@link #execute} method result
     */
    @Override
    protected String afterExecute(ParameterVariable variable) {
        return variable.getVariable(SERVICE_ID);
    }
}