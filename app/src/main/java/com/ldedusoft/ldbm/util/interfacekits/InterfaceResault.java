package com.ldedusoft.ldbm.util.interfacekits;

import android.text.TextUtils;

import com.ldedusoft.ldbm.Application.MyApplication;
import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.Appointment;
import com.ldedusoft.ldbm.model.Brand;
import com.ldedusoft.ldbm.model.CarCode;
import com.ldedusoft.ldbm.model.CarColor;
import com.ldedusoft.ldbm.model.CarList;
import com.ldedusoft.ldbm.model.CarType;
import com.ldedusoft.ldbm.model.CarWarehouse;
import com.ldedusoft.ldbm.model.Client;
import com.ldedusoft.ldbm.model.ClientType;
import com.ldedusoft.ldbm.model.FixCarType;
import com.ldedusoft.ldbm.model.FixingInfo;
import com.ldedusoft.ldbm.model.FixingsType;
import com.ldedusoft.ldbm.model.FixingsWarehouse;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.Invoice;
import com.ldedusoft.ldbm.model.Plan;
import com.ldedusoft.ldbm.model.Progress;
import com.ldedusoft.ldbm.model.Reception;
import com.ldedusoft.ldbm.model.RepaireType;
import com.ldedusoft.ldbm.model.SalesMan;
import com.ldedusoft.ldbm.model.TrafficClass;
import com.ldedusoft.ldbm.model.common.CommonNormal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/30.
 */
public class InterfaceResault {
    /*经手人列表*/
    public static String Pub_SalesmanResult = "Pub_SalesmanResult";
    /*车辆信息列表*/
    public static String Pub_CarCodeResult = "Pub_CarCodeResult";
    /*保存车辆信息返回值*/
    public static String  Pub_SaveCarResult="Pub_SaveCarResult";
    /*新建表单*/
    public static String RP_ReceptionNewResult = "RP_ReceptionNewResult";
    /*维修类型列表*/
    public static String RP_RepaireTypeResult = "RP_RepaireTypeResult";
    /*业务类型列表*/
    public static String RP_TrafficClassResult = "RP_TrafficClassResult";
    /*预约维修列表*/
    public static String AP_AppointmentListResult = "AP_AppointmentListResult";
    /*维修接待列表*/
    public static String RP_ShowReceptionResult = "RP_ShowReceptionResult";
    /*维修进度列表*/
    public static String RP_ProgressListResult = "RP_ProgressListResult";
    /*客户信息列表*/
    public static String Pub_ClientListResult = "Pub_ClientListResult";
    /*保存客户信息*/
    public static String Pub_SaveClientResult = "Pub_SaveClientResult";
    /*客户类型列表*/
    public static String PubClientTypeListResult="PubClientTypeListResult";
    /*新建洽谈单*/
    public static String SC_NewNegotiateResult="SC_NewNegotiateResult";
    /*保存洽谈单*/
    public static String SC_SaveNegotiateResult="SC_SaveNegotiateResult";
    /*车型列表*/
    public static String Pub_CarTypeListResult="Pub_CarTypeListResult";
    /*新建整车销售*/
    public static String SC_NewPurchaseResult = "SC_NewPurchaseResult";
    /*保存整车销售单*/
    public static String SC_SavePurchaseResult = "SC_SavePurchaseResult";
    /*整车仓库列表*/
    public static String Pub_CarWarehouseListResult = "Pub_CarWarehouseListResult";
    /*车辆颜色列表*/
    public static String Pub_CarColorListResult="Pub_CarColorListResult";
    /*在库车辆列表*/
    public static String Pub_CarListResult = "Pub_CarListResult";
    /*新建配件销售单*/
    public static String PT_NewSaleFixingsResult = "PT_NewSaleFixingsResult";
    /*保存配件销售单*/
    public static String SC_SaveSaleFixingsResult = "SC_SaveSaleFixingsResult";
    /*发票信息*/
    public static String Pub_InvoiceListResult = "Pub_InvoiceListResult";
    /*配件信息*/
    public static String PT_FixingsListResult = "PT_FixingsListResult";
    /*保存配件采购*/
    public static String PT_SavePurchaseFixingsResult = "PT_SavePurchaseFixingsResult";
    /*配件仓库列表*/
    public static String  PT_FixingsWarehouseListResult = "PT_FixingsWarehouseListResult";
    /*新建配件采购*/
    public static String  PT_NewPurchaseFixingsResult = "PT_NewPurchaseFixingsResult";
    /*保存配件信息*/
    public static String  PT_NewFixingsResult = "PT_NewFixingsResult";
    /*配件类型列表*/
    public static String  PT_FixingsTypeListResult = "PT_FixingsTypeListResult";
    /*配件车型列表*/
    public static String  PT_CarTypeListResult = "PT_CarTypeListResult";
    /*配件品牌列表*/
    public static String PT_BrandListResult = "PT_BrandListResult";
    /*采购计划列表*/
    public static String PT_PlanListResult = "PT_PlanListResult";

    /**
     * 采购计划列表
     * @param listData
     * @param result
     * {DanHao：单号;RiQi：日期；Num：单据数量；JinE：单据金额;MingCheng:供方名称}
     * @return
     */
    public static ArrayList<Plan> getPT_PlanListResult(ArrayList<Plan> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Plan item = new Plan();
                item.DanHao = jsonObject.getString("DanHao");
                item.RiQi = jsonObject.getString("RiQi");
                item.Num = jsonObject.getString("Num");
                item.JinE = jsonObject.getString("JinE");
                item.MingCheng = jsonObject.getString("MingCheng");
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * 配件品牌列表
     * @param listData
     * @param result
     * {"ID":5,"PinPai":"宝马"}
     * @return
     */
    public static ArrayList<Brand> getPT_BrandListResult(ArrayList<Brand> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Brand item = new Brand();
                item.setID(jsonObject.getInt("ID"));
                item.setPinPai(jsonObject.getString("PinPai"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * 配件车型列表
     * @param listData
     * @param result
     * {"ID":5,"XingHao":"奥迪","BianMa":"CX00001"}
     * @return
     */
    public static ArrayList<FixCarType> getPT_CarTypeListResult(ArrayList<FixCarType> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FixCarType item = new FixCarType();
                item.setID(jsonObject.getInt("ID"));
                item.setXingHao(jsonObject.getString("XingHao"));
                item.setBianMa(jsonObject.getString("BianMa"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * 配件类型列表
     * @param listData
     * @param result
     *  {"ID":5,"LeiBie":"脚垫类","BianHao":"001001"}
     * @return
     */
    public static ArrayList<FixingsType> getPT_FixingsTypeListResult(ArrayList<FixingsType> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FixingsType item = new FixingsType();
                item.setID(jsonObject.getInt("ID"));
                item.setLeiBie(jsonObject.getString("LeiBie"));
                item.setBianHao(jsonObject.getString("BianHao"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * 新建配件采购单
     * @param listData
     * @param result
     * @return
     */
    public static ArrayList<InputItem> getPT_NewPurchaseFixingsResult(ArrayList<InputItem> listData,String result){
        listData = getNewFormResault(listData,result);
        return listData;
    }


    /**
     * 配件仓库列表
     * @param listData
     * @param result
     *{ID：id;CangKu：仓库名称；BianHao：编号}
     * @return
     */
    public static ArrayList<FixingsWarehouse> getPT_FixingsWarehouseListResult(ArrayList<FixingsWarehouse> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FixingsWarehouse item = new FixingsWarehouse();
                item.setId(jsonObject.getInt("ID"));
                item.setBianHao(jsonObject.getString("BianHao"));
                item.setCangKu(jsonObject.getString("CangKu"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * 配件信息列表
     * @param listData
     * @param result
     * {ID：id；BianHao：编号；MingCheng：名称；TuHao：图号；CangKu：仓库；KuCun：库存数量；
     * KuCunJinE：库存金额；KuCunJunJia：库存均价；DanWei：单位}
     * @return
     */
    public static ArrayList<FixingInfo> getPT_FixingsListResult(ArrayList<FixingInfo> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FixingInfo item = new FixingInfo();
                item.setID(jsonObject.getInt("ID"));
                item.setBianHao(jsonObject.getString("BianHao"));
                item.setMingCheng(jsonObject.getString("MingCheng"));
                item.setTuHao(jsonObject.getString("TuHao"));
                item.setCangKu(jsonObject.getString("CangKu"));
                item.setKuCun(jsonObject.getString("KuCun"));
                item.setKuCunJinE(jsonObject.getString("KuCunJinE"));
                item.setKuCunJunJia(jsonObject.getString("KuCunJunJia"));
                item.setDanWei(jsonObject.getString("DanWei"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * 发票信息列表
     * @param listData
     * @param result
     * {ID：id；FaPiao：名称；ShuiLv：税率}
     * @return
     */
    public static ArrayList<Invoice> getPub_InvoiceListResult(ArrayList<Invoice> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Invoice item = new Invoice();
                item.setID(jsonObject.getInt("ID"));
                item.setFaPiao(jsonObject.getString("FaPiao"));
                item.setShuiLv(jsonObject.getString("ShuiLv"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    /**
     * 新建配件销售单
     * @param listData
     * @param result
     * @return
     */
    public static ArrayList<InputItem> getPT_NewSaleFixingsResult(ArrayList<InputItem> listData,String result){
        listData = getNewFormResault(listData,result);
        return listData;
    }

    /**
     * 新建配件采购单
     * @param listData
     * @param result
     * @return
     */
    public static ArrayList<InputItem> getPT_NewPurchaseFixings(ArrayList<InputItem> listData,String result){
        listData = getNewFormResault(listData,result);
        return listData;
    }

    /**
     * 在库车辆列表
     * @param listData
     * @param result
     * CarCode：车牌号；Color：颜色；DanJia：价格；Vin：Vin码；EngineNo：发动机号；
     * Brand：品牌；Series：车系；Type：车型}
     * @return
     */
    public static ArrayList<CarList> getPub_CarListResult(ArrayList<CarList> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CarList item = new CarList();
                item.setCarCode(jsonObject.getString("CarCode"));
                item.setColor(jsonObject.getString("Color"));
                item.setDanJia(jsonObject.getString("DanJia"));
                item.setVin(jsonObject.getString("Vin"));
                item.setEngineNo(jsonObject.getString("EngineNo"));
                item.setBrand(jsonObject.getString("Brand"));
                item.setSeries(jsonObject.getString("Series"));
                item.setType(jsonObject.getString("Type"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    /**
     * 车辆颜色列表
     * @param listData
     * @param result
     * {Color:颜色}
     * @return
     */
    public static ArrayList<CarColor> getPub_CarColorListResult(ArrayList<CarColor> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CarColor item = new CarColor();
                item.setColor(jsonObject.getString("Color"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    /**
     * 整车仓库列表
     * @param listData
     * @param result
     * {ID：id；Name：仓库名称；Code：仓库编号}
     * @return
     */
    public static ArrayList<CarWarehouse> getPub_CarWarehouseListResult(ArrayList<CarWarehouse> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CarWarehouse item = new CarWarehouse();
                item.setID(jsonObject.getInt("ID"));
                item.setName(jsonObject.getString("Name"));
                item.setCode(jsonObject.getString("Code"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * 新建整车销售单
     * @param listData
     * @param result
     * @return
     */
    public static ArrayList<InputItem> getSC_NewPurchaseResult(ArrayList<InputItem> listData,String result){
        listData = getNewFormResault(listData,result);
        return listData;
    }

    /**
     * 车型列表
     * @param listData
     * @param result
     * {ID：id；Type：车型；Series：车系；Brand：品牌;PriceOut1:价格}
     * @return
     */
    public static ArrayList<CarType> getPub_CarTypeListResult(ArrayList<CarType> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CarType item = new CarType();
                item.setID(jsonObject.getInt("ID"));
                item.setType(jsonObject.getString("Type"));
                item.setSeries(jsonObject.getString("Series"));
                item.setBrand(jsonObject.getString("Brand"));
                item.setPriceOut1(jsonObject.getString("PriceOut1"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    /**
     * 新建洽谈单
     * @param listData
     * @param result
     * @return
     */
    public static ArrayList<InputItem> getSC_NewNegotiateResult(ArrayList<InputItem> listData,String result){
        listData = getNewFormResault(listData,result);
        return listData;
    }

    /**
     * 客户类型列表
     * @param listData
     * @param result
     * ID:id；BianHao:编号；LeiBie:类型名称}
     * @return
     */
    public static ArrayList<ClientType> getPubClientTypeListResult(ArrayList<ClientType> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ClientType item = new ClientType();
                item.setID(jsonObject.getInt("ID"));
                item.setBianHao(jsonObject.getString("BianHao"));
                item.setLeiBie(jsonObject.getString("LeiBie"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

        /**
         * 客户信息列表
         * @param listData
         * @param result
         * @return
         */
    public static ArrayList<Client> getPub_ClientListResult(ArrayList<Client> listData,String result) {
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Client item = new Client();
                item.setBianHao(jsonObject.getString("BianHao"));
                item.setLinkMan(jsonObject.getString("LinkMan"));
                item.setName(jsonObject.getString("Name"));
                item.setMobilephone(jsonObject.getString("Mobilephone"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
        /**
         * 维修进度查询
         *  DanHao：单号；JcDate：进出日期；JdDate：接待日期；WgDate：完工日期；
         * JinDu：进度；MingCheng：客户名称；CarCode：车牌号
         * @param result
         * @return
         */
    public static ArrayList<Progress> getRP_ProgressListResult(ArrayList<Progress> listData,String result){
        try {
            listData.clear();
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Progress item = new Progress();
                item.setDanHao(jsonObject.getString("DanHao"));
                item.setJcDate(jsonObject.getString("JcDate"));
                item.setJdDate(jsonObject.getString("JdDate"));
                item.setWgDate(jsonObject.getString("WgDate"));
                item.setJinDu(jsonObject.getString("JinDu"));
                item.setMingCheng(jsonObject.getString("MingCheng"));
                item.setCarCode(jsonObject.getString("CarCode"));
                listData.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    /**
     * 维修接待列表
     * {Id：id；DanHao：单号；jdTime：接待时间；CarCode：车牌号；
     * MingCheng：客户名称；WeiXiuWay：维修方式；ywLeiBie：业务类别}
     * @param listData
     * @param result
     * @return
     */
    public static ArrayList<Reception> getRP_ShowReceptionResult(ArrayList<Reception> listData,String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Reception reception = new Reception();
                reception.setId(jsonObject.getInt("Id"));
                reception.setDanHao(jsonObject.getString("DanHao"));
                reception.setJdTime(jsonObject.getString("jdTime"));
                reception.setCarCode(jsonObject.getString("CarCode"));
                reception.setMingCheng(jsonObject.getString("MingCheng"));
                reception.setWeiXiuWay(jsonObject.getString("WeiXiuWay"));
                reception.setYwLeiBie(jsonObject.getString("ywLeiBie"));
                listData.add(reception);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    /**
     * 预约维修列表
     *  {Id：id；DanHao：单号；djTime：登记时间；yyTime：预约时间；CarCode：车牌号；
     * MingCheng：客户名称；wxFangShi：维修方式；ywLeiBie：业务类别}
     */
    public static  ArrayList<Appointment> getAP_AppointmentListResult(ArrayList<Appointment> listData,String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Appointment appointment = new Appointment();
                appointment.setId(jsonObject.getInt("Id"));
                appointment.setDanHao(jsonObject.getString("DanHao"));
                appointment.setDjTime(jsonObject.getString("djTime"));
                appointment.setYyTime(jsonObject.getString("yyTime"));
                appointment.setCarCode(jsonObject.getString("CarCode"));
                appointment.setMingCheng(jsonObject.getString("MingCheng"));
                appointment.setWxFangShi(jsonObject.getString("wxFangShi"));
                appointment.setYwLeiBie(jsonObject.getString("ywLeiBie"));
                listData.add(appointment);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    /**
     * 维修类型列表返回值
     * @param listData
     * @param result
     * @return
     */
    public static ArrayList<TrafficClass> getRP_TrafficClassResult(ArrayList<TrafficClass> listData,String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TrafficClass trafficClass = new TrafficClass();
                trafficClass.setId(jsonObject.getInt("ID"));
                trafficClass.setTypeName(jsonObject.getString("ClassName"));
                listData.add(trafficClass);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    /**
     * 维修类型列表返回值
     * @param listData
     * @param result
     * @return
     */
    public static ArrayList<RepaireType> getRP_RepaireType(ArrayList<RepaireType> listData,String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RepaireType repaireType = new RepaireType();
                repaireType.setId(jsonObject.getInt("ID"));
                repaireType.setTypeName(jsonObject.getString("TypeName"));
                listData.add(repaireType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    /**
     * 预约维修
     * @param listData
     * @param result
     * @return
     */
    public static ArrayList<InputItem> getRP_ReceptionNewResult_YY(ArrayList<InputItem> listData,String result){
        listData = getNewFormResault(listData,result);
        return listData;
    }


    /**
     * 车辆信息返回值
     * @param result
     * @return
     */
    public static ArrayList<CarCode> getPub_CarCodeResult( ArrayList<CarCode> listData,String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CarCode carCode = new CarCode();
                carCode.setBrand(jsonObject.getString("brand"));
                carCode.setCarCode(jsonObject.getString("CarCode"));
                carCode.setColor(jsonObject.getString("color"));
                carCode.setLinkMan(jsonObject.getString("LinkMan"));
                carCode.setMobilephone(jsonObject.getString("Mobilephone"));
                carCode.setName(jsonObject.getString("Name"));
                carCode.setTelephone(jsonObject.getString("Telephone"));
                listData.add(carCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * 经手人返回结果
     * @param result
     * @return
     */
    public static ArrayList<SalesMan> getPub_SalesmanResult(ArrayList<SalesMan> listData,String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SalesMan salesMan = new SalesMan();
                salesMan.setId(jsonObject.getInt("Id"));
                salesMan.setName(jsonObject.getString("Name"));
                salesMan.setNumber(jsonObject.getString("Number"));
                salesMan.setDepartment(jsonObject.getString("Department"));
                salesMan.setCompany(jsonObject.getString("Company"));
                listData.add(salesMan);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * 获取新建表单时生成的单号和制单人
     * @param listData
     * @param result
     * @return
     */
    private static ArrayList<InputItem> getNewFormResault(ArrayList<InputItem> listData,String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String danhao = jsonObject.getString("DanHao");
            String formMaker = jsonObject.getString("FormMaker");
            for (InputItem item : listData){
                if("Number".equals(item.getItemId())){
                    item.setValue(danhao);
                }
                if("FormMaker".equals(item.getItemId())){
                    item.setValue(formMaker);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    /*经营
    * 返回值格式：1,2,3,4
    * */
    private static ArrayList addFixedCondition(ArrayList listData,String result,int arrayId){
        MyApplication myApplication = MyApplication.getInstance();
        String[] labelArray = myApplication.getArray(arrayId);
        if (!TextUtils.isEmpty(result)) {
            String[] resultArray = result.split(",");
            for(int i=0;i<resultArray.length;i++){
                try{
                    CommonNormal cn = new CommonNormal();
                    cn.name1 = labelArray[i];
                    cn.value1 = resultArray[i];
                    cn.details = false;
                    listData.add(cn);
                }catch (Exception e){}
            }
        }
        return listData;
    }

    /*收款
   * 返回值格式：{BianHao：客户编号；MingCheng：客户名称；QianKuan：客户欠款；ShiShou：实际收款；YingShou：应收款}
   * */
    private static ArrayList addZH_PaymentResultCondition(ArrayList listData,String result,String title){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.title = title;
                    cn.name1 = "客户名称";
                    cn.value1 = jsonObject.getString("MingCheng");
                    cn.name2 = "客户编号";
                    cn.value2 = jsonObject.getString("BianHao");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }

    /*
    * 客户账务统计
    * 返回值格式：{BianHao：客户编号；MingCheng：客户名称；QianKuan：客户欠款；LinkMan：联系人；ZuiDaEDu：信誉额度金额；cedu：超信誉额度金额}
    * */
    private static ArrayList addZH_CustomerAccountsResult(ArrayList listData,String result,String title){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.title = title;
                    cn.name1 = "客户名称";
                    cn.value1 = jsonObject.getString("MingCheng");
                    cn.name2 = "客户编号";
                    cn.value2 = jsonObject.getString("BianHao");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }


    /*
    * 进货汇总
    * 返回值格式：{XiangMu：项目；Num：数量合计；NPercent：数量百分比；Jine：金额合计；JPercent：金额百分比}
    * */
    private static ArrayList addJH_RestockResultCondition(ArrayList listData,String result,String title){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.title = title;
                    cn.name1 = "项目";
                    cn.value1 = jsonObject.getString("XiangMu");
                    cn.name2 = "数量合计";
                    cn.value2 = jsonObject.getString("Num");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }


    /*
    * 采购订单
    * 返回值格式：{BianHao：配件编号；MingCheng：配件名称；DNum：订单数量；DJine：订单金额；GNum：进货数量；GJine：进货金额；RNum：入库数量；RJine：入库金额；JBilv：进货比率；RBilv：入库比率}
    * */
    private static ArrayList addJH_BuyProjectResultCondition(ArrayList listData,String result,String title){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.title = title;
                    cn.name1 = "配件名称";
                    cn.value1 = jsonObject.getString("MingCheng");
                    cn.name2 = "订单数量";
                    cn.value2 = jsonObject.getString("DNum");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }


    /*
       * 供应商累计供货排行
       * {BianHao：供商编号；MingCheng：供商名称；Num：累计供货数量；Jine：含税金额；BJine：不含税金额}
       * */
    private static ArrayList addJH_SupplyResultCondition(ArrayList listData,String result,String title){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.title = title;
                    cn.name1 = "供商名称";
                    cn.value1 = jsonObject.getString("MingCheng");
                    cn.name2 = "累计供货数量";
                    cn.value2 = jsonObject.getString("Num");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }


    /*
      * 销售计划分析表
      * {BianHao：配件编号；MingCheng：配件名称；DNum：订单数量；DJine：订单金额；GNum：进货数量；GJine：进货金额；RNum：出库数量；RJine：出库金额；JBilv：销售比率；RBilv：出库比率}
      * */
    private static ArrayList addXS_SaleProjectResultCondition(ArrayList listData,String result,String title){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.title = title;
                    cn.name1 = "供商名称";
                    cn.value1 = jsonObject.getString("MingCheng");
                    cn.name2 = "订单数量";
                    cn.value2 = jsonObject.getString("DNum");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }

    /*
     * 客户销售额排行
     * {BianHao：供商编号；MingCheng：供商名称；Num：销售数量；Jine：销售金额；LinkMan：联系人；ChengBenZh：成本金额；LiRun：毛利润}
     * */
    private static ArrayList addXS_ClientSalesVolumeCondition(ArrayList listData,String result,String title){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.title = title;
                    cn.name1 = "供商名称";
                    cn.value1 = jsonObject.getString("MingCheng");
                    cn.name2 = "销售数量";
                    cn.value2 = jsonObject.getString("Num");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }



    /*
     * 日期销售报表
     * {RiQi：日期；Jine：销售额；ChengBen：销售成本；LiRun：毛利润；Count：来客数；PingJun：平均客买}
     * */
    private static ArrayList addXS_DateSaleResultCondition(ArrayList listData,String result,String title){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.title = title;
                    cn.name1 = "日期";
                    cn.value1 = jsonObject.getString("RiQi");
                    cn.name2 = "销售额";
                    cn.value2 = jsonObject.getString("Jine");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }


    /*
    * 仓库销售排行
    * {BianHao：仓库编号；CangKu：仓库名称；Num：销售数量；Jine：销售金额；ChengBen：成本金额；LiRun：毛利润}
    * */
    private static ArrayList addXS_StockSaleResultCondition(ArrayList listData,String result,String title){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.title = title;
                    cn.name1 = "仓库名称";
                    cn.value1 = jsonObject.getString("CangKu");
                    cn.name2 = "销售数量";
                    cn.value2 = jsonObject.getString("Num");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }

    /********************************************************/
    private static ArrayList test(ArrayList listData,String result){
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommonNormal cn = new CommonNormal();
                    cn.name1 = jsonObject.getString("ID");
                    cn.value1 = jsonObject.getString("PinPai");
                    cn.details = true;
                    cn.dataSource = jsonObject.toString();
                    listData.add(cn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listData;
    }

    /**
     * 通用获取返回值节点名
     * @param inName
     * @return
     */
    public static String getCommonRsName(String inName){
        MyApplication myApplication = MyApplication.getInstance();
        String resault="";
        if(inName.equals(myApplication.getStr(R.string.report_jingYing))) {
            resault = "ZH_OperationResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_kuCun))) {
            resault = "ZH_InventoryResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_weiXiu))) {
            resault = "ZH_RepairResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_shouKuan))) {
            resault = "ZH_PaymentResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_keHuZhangWuTJ))) {
            resault = "ZH_CustomerAccountsResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_jinHuoHuiZong))) {
            resault = "JH_RestockResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_caiGouDingDan))) {
            resault = "JH_BuyProjectResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_gongYingShangLeiJi))) {
            resault = "JH_SupplyResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_xiaoShouJiHua))) {
            resault = "XS_SaleProjectResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_keHuXiaoShouE))) {
            resault = "XS_ClientSalesVolumeResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_riQiXiaoShou))) {
            resault = "XS_DateSaleResult";
        }else if(inName.equals(myApplication.getStr(R.string.report_cangKuXiaoShou))) {
            resault = "XS_StockSaleResult";
        }
        return resault;
    }

    /**
     * 通用解析返回值方法
     * @param listData
     * @param result
     * @param inName
     * @return
     */
    public static ArrayList getCommonResault(ArrayList listData,String result,String inName){
        MyApplication myApplication = MyApplication.getInstance();
        if(inName.equals(myApplication.getStr(R.string.report_jingYing))) {
            listData = addFixedCondition(listData, result, R.array.report_jingYing);
        }else if(inName.equals(myApplication.getStr(R.string.report_kuCun))) {
            listData = addFixedCondition(listData, result, R.array.report_kuCun);
        }else if(inName.equals(myApplication.getStr(R.string.report_weiXiu))) {
            listData = addFixedCondition(listData, result, R.array.report_weiXiu);
        }else if(inName.equals(myApplication.getStr(R.string.report_shouKuan))) {
            listData = addZH_PaymentResultCondition(listData, result,inName);
        }else if(inName.equals(myApplication.getStr(R.string.report_keHuZhangWuTJ))) {
            listData = addZH_PaymentResultCondition(listData, result,inName);
        }else if(inName.equals(myApplication.getStr(R.string.report_jinHuoHuiZong))) {
            listData = addJH_RestockResultCondition(listData, result,inName);
        }else if(inName.equals(myApplication.getStr(R.string.report_caiGouDingDan))) {
            listData = addJH_BuyProjectResultCondition(listData, result,inName);
        }else if(inName.equals(myApplication.getStr(R.string.report_gongYingShangLeiJi))) {
            listData = addJH_SupplyResultCondition(listData, result,inName);
        }else if(inName.equals(myApplication.getStr(R.string.report_xiaoShouJiHua))) {
            listData = addXS_SaleProjectResultCondition(listData, result,inName);
        }else if(inName.equals(myApplication.getStr(R.string.report_keHuXiaoShouE))) {
            listData = addXS_ClientSalesVolumeCondition(listData, result,inName);
        }else if(inName.equals(myApplication.getStr(R.string.report_riQiXiaoShou))) {
            listData = addXS_DateSaleResultCondition(listData, result,inName);
        }else if(inName.equals(myApplication.getStr(R.string.report_cangKuXiaoShou))) {
            listData = addXS_StockSaleResultCondition(listData, result,inName);
        }
        return listData;
    }
}
