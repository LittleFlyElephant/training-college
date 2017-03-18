package com.raychen.service;

import com.raychen.model.TbOrgFinancialModel;
import com.raychen.model.TbOrganizationModel;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by raychen on 2017/3/12.
 */
@Service
public class OrganizationService {

    public TbOrgFinancialModel buildCharge(TbOrganizationModel org, byte asd, double money, String op, int opType, byte state){
        TbOrgFinancialModel charge = new TbOrgFinancialModel();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        charge.setOrg(org);
        charge.setAsd(asd);
        charge.setMoney(money);
        charge.setOp(op);
        charge.setOpType(opType);
        charge.setTime(format.format(new Date()));
        charge.setState(state);
        return charge;
    }
}
