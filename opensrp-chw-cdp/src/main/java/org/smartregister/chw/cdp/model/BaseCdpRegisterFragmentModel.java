package org.smartregister.chw.cdp.model;

import org.jetbrains.annotations.NotNull;
import org.smartregister.chw.cdp.CdpLibrary;
import org.smartregister.chw.cdp.contract.BaseCdpRegisterFragmentContract;
import org.smartregister.chw.cdp.util.ConfigHelper;
import org.smartregister.chw.cdp.util.DBConstants;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.View;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.cursoradapter.SmartRegisterQueryBuilder;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;

public class BaseCdpRegisterFragmentModel implements BaseCdpRegisterFragmentContract.Model {

    @Override
    public RegisterConfiguration defaultRegisterConfiguration() {
        return ConfigHelper.defaultRegisterConfiguration(CdpLibrary.getInstance().context().applicationContext());
    }

    @Override
    public ViewConfiguration getViewConfiguration(String viewConfigurationIdentifier) {
        return ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().getViewConfiguration(viewConfigurationIdentifier);
    }

    @Override
    public Set<View> getRegisterActiveColumns(String viewConfigurationIdentifier) {
        return ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().getRegisterActiveColumns(viewConfigurationIdentifier);
    }

    @Override
    public String countSelect(String tableName, String mainCondition) {
        SmartRegisterQueryBuilder countQueryBuilder = new SmartRegisterQueryBuilder();
        countQueryBuilder.selectInitiateMainTableCounts(tableName);
        return countQueryBuilder.mainCondition(mainCondition);
    }

    @NonNull
    @Override
    public String mainSelect(@NonNull String tableName, @NonNull String mainCondition) {
        SmartRegisterQueryBuilder queryBuilder = new SmartRegisterQueryBuilder();
        queryBuilder.selectInitiateMainTable(tableName, mainColumns(tableName), DBConstants.KEY.BASE_ENTITY_ID);
        return queryBuilder.mainCondition(mainCondition);
    }

    @NotNull
    public String[] mainColumns(String tableName) {
        Set<String> columnList = new HashSet<>();
        columnList.add(tableName + "." + DBConstants.KEY.BASE_ENTITY_ID);
        columnList.add(tableName + "." + DBConstants.KEY.RELATIONAL_ID);
        columnList.add(tableName+ "." + DBConstants.KEY.OUTLET_NAME);
        columnList.add(tableName+ "." + DBConstants.KEY.OUTLET_TYPE);
        columnList.add(tableName+ "." + DBConstants.KEY.OUTLET_VILLAGE_STREET_NAME);
        columnList.add(tableName+ "." + DBConstants.KEY.OUTLET_WARD_NAME);
        columnList.add(tableName+ "." + DBConstants.KEY.FOCAL_PERSON_NAME);
        columnList.add(tableName+ "." + DBConstants.KEY.FOCAL_PERSON_PHONE);

        return columnList.toArray(new String[columnList.size()]);
    }

}
