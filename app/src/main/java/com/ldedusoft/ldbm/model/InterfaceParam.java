package com.ldedusoft.ldbm.model;

/**
 * Created by wangjianwei on 2016/6/27.
 */
public class InterfaceParam {

    private static InterfaceParam param;

    /**
     * 服务器地址
     */
    public static final String SERVER_PATH = "http://ldbm.ld-edusoft.com/webservers/WebService.asmx";

    public static final String SYS_USER = "admin";

    public static final String SYS_PASSWORD = "zwj6756";

    /**预约维修列表*/
    private String AP_AppointmentList;
    /**保存预约维修*/
    private String AP_AppointmentSave;
    /**库存成本异常*/
    private String BJ_InventoryCost;
    /**负库存报警*/
    private String BJ_NegativeInventory;
    /**库存配件缺货报警*/
    private String BJ_OutofStock;
    /**采购订单分析*/
    private String JH_BuyProject;
    /**进货汇总表*/
    private String JH_Restock;
    /**供应商累计供货排行*/
    private String JH_Supply;
    /**登陆*/
    private String  Login;
    /**修改密码*/
    private String ModifyPassword;
    /**配件品牌列表*/
    private String PT_BrandList;
    /**配件车型列表*/
    private String PT_CarTypeList;
    /**配件信息列表*/
    private String PT_FixingsList;
    /**配件类型*/
    private String PT_FixingsTypeList;
    /**配件仓库列表*/
    private String PT_FixingsWarehouseList;
    /**将采购计划单导入采购单*/
    private String PT_ImportPurchaseInfo;
    /**保存新配件信息*/
    private String PT_NewFixings;
    /**配件采购单（新建）*/
    private String PT_NewPurchaseFixings;
    /**新建配件销售单*/
    private String PT_NewSaleFixings;
    /**采购计划单列表*/
    private String PT_PlanList;
    /**配件采购单（保存）*/
    private String PT_SavePurchaseFixings;
    /**权限信息*/
    private String PowerInfo;
    /**客户类型列表*/
    private String PubClientTypeList;
    /**车辆信息列表*/
    private String Pub_CarCode;
    /**车辆颜色列表*/
    private String Pub_CarColorList;
    /**在库车辆*/
    private String Pub_CarList;
    /**车型列表*/
    private String Pub_CarTypeList;
    /**整车仓库列表*/
    private String Pub_CarWarehouseList;
    /**客户信息列表*/
    private String Pub_ClientList;
    /**发票信息*/
    private String Pub_InvoiceList;
    /**修改客户信息*/
    private String Pub_ModifyClientInfo;
    /**修改配件信息*/
    private String Pub_ModifyFixingtInfo;
    /** 经手人信息列表*/
    private String Pub_Salesman;
    /**保存车辆信息(客户车辆)*/
    private String Pub_SaveCar;
    /**保存客户信息*/
    private String Pub_SaveClient;
    /**维修接待作废*/
    private String RP_DelReception;
    /**维修进度*/
    private String RP_ProgressList;
    /**新建单据*/
    private String RP_ReceptionNew;
    /**保存维修接待*/
    private String RP_ReceptionSave;
    /**维修方式*/
    private String RP_RepaireType;
    /**维修接待列表*/
    private String RP_ShowReception;
    /**业务类别*/
    private String RP_TrafficClass;
    /**销售洽谈(新建)*/
    private String SC_NewNegotiate;
    /**整车销售订单(新建)*/
    private String SC_NewPurchase;
    /**保存车辆信息(整车)*/
    private String  SC_SaveCarInfo;
    /**销售洽谈(保存)*/
    private String  SC_SaveNegotiate;
    /**整车销售订单(保存)*/
    private String SC_SavePurchase;
    /**保存配件销售单*/
    private String SC_SaveSaleFixings;
    /**商品累计进货统计*/
    private String SP_ComAllIn;
    /**累计商品销售统计*/
    private String SP_ComAllSale;
    /**商品进货月分析*/
    private String SP_MonthIn;
    /**月销售分析*/
    private String SP_MonthSala;
    /**签到*/
    private String SignIn;
    /**客户销售额排行*/
    private String XS_ClientSalesVolume;
    /**日期销售报表*/
    private String XS_DateSale;
    /**营业额统计*/
    private String XS_OperatingAmount;
    /**销售计划分析表*/
    private String XS_SaleProject;
    /**仓库销售排行*/
    private String XS_StockSale;
    /**车辆销售统计*/
    private String ZC_CarSale;
    /**销售人员业绩统计*/
    private String ZC_SalesPerformance;
    /**客户账务统计*/
    private String ZH_CustomerAccounts;
    /**老板信息中心*/
    private String ZH_InfoForBoss;
    /**库存统计*/
    private String ZH_Inventory;
    /**经营统计*/
    private String ZH_Operation;
    /**收款统计*/
    private String ZH_Payment;
    /**维修统计*/
    private String ZH_Repair;
    /**人员回款统计*/
    private String ZY_BackPayment;
    /**员工销售排行*/
    private String ZY_StaffSale;

    private InterfaceParam(){
    }

    public static InterfaceParam getInstance() {
        if(param==null){
            param = new InterfaceParam();
        }
        return param;
    }

    /**预约维修列表*/
    public String getAP_AppointmentList() {
        return AP_AppointmentList;
    }
    /**保存预约维修*/
    public String getAP_AppointmentSave() {
        return AP_AppointmentSave;
    }
    /**库存成本异常*/
    public String getBJ_InventoryCost() {
        return BJ_InventoryCost;
    }
    /**负库存报警*/
    public String getBJ_NegativeInventory() {
        return BJ_NegativeInventory;
    }
    /**库存配件缺货报警*/
    public String getBJ_OutofStock() {
        return BJ_OutofStock;
    }
    /**采购订单分析*/
    public String getJH_BuyProject() {
        return JH_BuyProject;
    }
    /**进货汇总表*/
    public String getJH_Restock() {
        return JH_Restock;
    }
    /**供应商累计供货排行*/
    public String getJH_Supply() {
        return JH_Supply;
    }
    /**登陆*/
    public String getLogin(String userName,String passWord) {
        Login =  "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "  <soap:Header>" +
                "    <MySoapHeader xmlns=\"LDBM4S\">" +
                "      <UserName>@sysUser</UserName>" +
                "      <PassWord>@sysPassword</PassWord>" +
                "    </MySoapHeader>" +
                "  </soap:Header>" +
                "  <soap:Body>" +
                "    <Login xmlns=\"LDBM4S\">" +
                "      <UName>@userName</UName>" +
                "      <Pwd>@passWord</Pwd>" +
                "    </Login>" +
                "  </soap:Body>" +
                "</soap:Envelope>";
        Login = Login.replace("@sysUser",SYS_USER);
        Login = Login.replace("@sysPassword",SYS_PASSWORD);
        Login = Login.replace("@userName",userName);
        Login = Login.replace("@passWord",passWord);
        return Login;
    }
    /**修改密码*/
    public String getModifyPassword() {
        return ModifyPassword;
    }
    /**配件品牌列表*/
    public String getPT_BrandList() {
        return PT_BrandList;
    }
    /**配件车型列表*/
    public String getPT_CarTypeList() {
        return PT_CarTypeList;
    }
    /**配件信息列表*/
    public String getPT_FixingsList() {
        return PT_FixingsList;
    }
    /**配件类型*/
    public String getPT_FixingsTypeList() {
        return PT_FixingsTypeList;
    }
    /**配件仓库列表*/
    public String getPT_FixingsWarehouseList() {
        return PT_FixingsWarehouseList;
    }
    /**将采购计划单导入采购单*/
    public String getPT_ImportPurchaseInfo() {
        return PT_ImportPurchaseInfo;
    }
    /**保存新配件信息*/
    public String getPT_NewFixings() {
        return PT_NewFixings;
    }
    /**配件采购单（新建）*/
    public String getPT_NewPurchaseFixings() {
        return PT_NewPurchaseFixings;
    }
    /**新建配件销售单*/
    public String getPT_NewSaleFixings() {
        return PT_NewSaleFixings;
    }
    /**采购计划单列表*/
    public String getPT_PlanList() {
        return PT_PlanList;
    }
    /**配件采购单（保存）*/
    public String getPT_SavePurchaseFixings() {
        return PT_SavePurchaseFixings;
    }
    /**权限信息*/
    public String getPowerInfo() {
        return PowerInfo;
    }
    /**客户类型列表*/
    public String getPubClientTypeList() {
        return PubClientTypeList;
    }
    /**车辆信息列表*/
    public String getPub_CarCode() {
        return Pub_CarCode;
    }
    /**车辆颜色列表*/
    public String getPub_CarColorList() {
        return Pub_CarColorList;
    }
    /**在库车辆*/
    public String getPub_CarList() {
        return Pub_CarList;
    }
    /**车型列表*/
    public String getPub_CarTypeList() {
        return Pub_CarTypeList;
    }
    /**整车仓库列表*/
    public String getPub_CarWarehouseList() {
        return Pub_CarWarehouseList;
    }
    /**客户信息列表*/
    public String getPub_ClientList() {
        return Pub_ClientList;
    }
    /**发票信息*/
    public String getPub_InvoiceList() {
        return Pub_InvoiceList;
    }
    /**修改客户信息*/
    public String getPub_ModifyClientInfo() {
        return Pub_ModifyClientInfo;
    }
    /**修改配件信息*/
    public String getPub_ModifyFixingtInfo() {
        return Pub_ModifyFixingtInfo;
    }
    /**经手人信息列表*/
    public String getPub_Salesman() {
        return Pub_Salesman;
    }
    /**保存车辆信息(客户车辆)*/
    public String getPub_SaveCar() {
        return Pub_SaveCar;
    }
    /**保存客户信息*/
    public String getPub_SaveClient() {
        return Pub_SaveClient;
    }
    /**维修接待作废*/
    public String getRP_DelReception() {
        return RP_DelReception;
    }
    /**维修进度*/
    public String getRP_ProgressList() {
        return RP_ProgressList;
    }
    /**新建单据*/
    public String getRP_ReceptionNew() {
        return RP_ReceptionNew;
    }
    /**保存维修接待*/
    public String getRP_ReceptionSave() {
        return RP_ReceptionSave;
    }
    /**维修方式*/
    public String getRP_RepaireType() {
        return RP_RepaireType;
    }
    /**维修接待列表*/
    public String getRP_ShowReception() {
        return RP_ShowReception;
    }
    /**业务类别*/
    public String getRP_TrafficClass() {
        return RP_TrafficClass;
    }
    /**销售洽谈(新建)*/
    public String getSC_NewNegotiate() {
        return SC_NewNegotiate;
    }
    /**整车销售订单(新建)*/
    public String getSC_NewPurchase() {
        return SC_NewPurchase;
    }
    /**保存车辆信息(整车)*/
    public String getSC_SaveCarInfo() {
        return SC_SaveCarInfo;
    }
    /**销售洽谈(保存)*/
    public String getSC_SaveNegotiate() {
        return SC_SaveNegotiate;
    }
    /**整车销售订单(保存)*/
    public String getSC_SavePurchase() {
        return SC_SavePurchase;
    }
    /**保存配件销售单*/
    public String getSC_SaveSaleFixings() {
        return SC_SaveSaleFixings;
    }
    /**商品累计进货统计*/
    public String getSP_ComAllIn() {
        return SP_ComAllIn;
    }
    /**累计商品销售统计*/
    public String getSP_ComAllSale() {
        return SP_ComAllSale;
    }
    /**商品进货月分析*/
    public String getSP_MonthIn() {
        return SP_MonthIn;
    }
    /**月销售分析*/
    public String getSP_MonthSala() {
        return SP_MonthSala;
    }
    /**签到*/
    public String getSignIn() {
        return SignIn;
    }
    /**客户销售额排行*/
    public String getXS_ClientSalesVolume() {
        return XS_ClientSalesVolume;
    }
    /**日期销售报表*/
    public String getXS_DateSale() {
        return XS_DateSale;
    }
    /**营业额统计*/
    public String getXS_OperatingAmount() {
        return XS_OperatingAmount;
    }
    /**销售计划分析表*/
    public String getXS_SaleProject() {
        return XS_SaleProject;
    }
    /**仓库销售排行*/
    public String getXS_StockSale() {
        return XS_StockSale;
    }
    /**车辆销售统计*/
    public String getZC_CarSale() {
        return ZC_CarSale;
    }
    /**销售人员业绩统计*/
    public String getZC_SalesPerformance() {
        return ZC_SalesPerformance;
    }
    /**客户账务统计*/
    public String getZH_CustomerAccounts() {
        return ZH_CustomerAccounts;
    }
    /**老板信息中心*/
    public String getZH_InfoForBoss() {
        return ZH_InfoForBoss;
    }
    /**库存统计*/
    public String getZH_Inventory() {
        return ZH_Inventory;
    }
    /**经营统计*/
    public String getZH_Operation() {
        return ZH_Operation;
    }
    /**收款统计*/
    public String getZH_Payment() {
        return ZH_Payment;
    }
    /**维修统计*/
    public String getZH_Repair() {
        return ZH_Repair;
    }
    /**人员回款统计*/
    public String getZY_BackPayment() {
        return ZY_BackPayment;
    }
    /**员工销售排行*/
    public String getZY_StaffSale() {
        return ZY_StaffSale;
    }


}
