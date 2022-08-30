package org.smartregister.chw.cdp.dao;

import org.smartregister.chw.cdp.domain.OutletObject;
import org.smartregister.dao.AbstractDao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CdpDao extends AbstractDao {

    public static int getNextOutletVisitNumber(String baseEntityId) {
        String sql = "SELECT visit_number from ec_cdp_register where is_closed is 0" +
                "   AND base_entity_id = '" + baseEntityId + "' ";

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "visit_number");
        List<Integer> res = readData(sql, dataMap);
        if (res != null && res.size() > 0 && res.get(0) != null)
            return res.get(0) + 1;

        return 1;
    }

    public static Integer getCDPOutletCount() {
        String sql = "SELECT count(*) count from ec_cdp_outlet where is_closed is 0";

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> res = readData(sql, dataMap);
        if (res == null || res.size() == 0)
            return 0;
        return res.get(0);
    }

    public static int getLastRecordedStockAtOutlet(String baseEntityId) {
        int maleCondomsCount = 0;
        int femaleCondomsCount = 0;
        String sql = "SELECT male_condoms_count, female_condoms_count FROM ec_cdp_register " +
                     "   WHERE base_entity_id = '" + baseEntityId + "' ";
        DataMap<Integer> female_condoms_count = cursor -> getCursorIntValue(cursor, "female_condoms_count");
        DataMap<Integer> male_condoms_count = cursor -> getCursorIntValue(cursor, "male_condoms_count");

        List<Integer> maleCondomsCountRes = readData(sql, male_condoms_count);
        List<Integer> femaleCondomsCountRes = readData(sql, female_condoms_count);

        if (maleCondomsCountRes != null && maleCondomsCountRes.size() > 0 && maleCondomsCountRes.get(0) != null)
            maleCondomsCount = maleCondomsCountRes.get(0);
        if (femaleCondomsCountRes != null && femaleCondomsCountRes.size() > 0 && femaleCondomsCountRes.get(0) != null)
            femaleCondomsCount = femaleCondomsCountRes.get(0);

        return maleCondomsCount + femaleCondomsCount;

    }

    public static OutletObject getOutlet(String baseEntityID) {
        String sql = "SELECT p.base_entity_id, p.outlet_name, p.outlet_type, p.outlet_ward_name, p.unique_id, p.focal_person_name, p.focal_person_phone " +
                "           FROM ec_cdp_outlet p " +
                "           INNER JOIN ec_cdp_register m on p.base_entity_id = m.base_entity_id " +
                "           WHERE m.is_closed is 0" +
                "           AND m.base_entity_id = '" + baseEntityID + "'";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        DataMap<OutletObject> dataMap = cursor -> {
            OutletObject outletObject = new OutletObject();

            outletObject.setBaseEntityId(getCursorValue(cursor, "base_entity_id"));
            outletObject.setOutletName(getCursorValue(cursor, "outlet_name", ""));
            outletObject.setOutletId(getCursorValue(cursor, "unique_id", ""));
            outletObject.setOutletWardName(getCursorValue(cursor, "outlet_ward_name", ""));
            outletObject.setOutletType(getCursorValue(cursor, "outlet_type", ""));
            outletObject.setFocalPersonName(getCursorValue(cursor, "focal_person_name", ""));
            outletObject.setFocalPersonNumber(getCursorValue(cursor, "focal_person_phone", ""));

            return outletObject;
        };

        List<OutletObject> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return null;

        return res.get(0);
    }
}
