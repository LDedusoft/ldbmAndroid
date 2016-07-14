package com.ldedusoft.ldbm.util.interfacekits;

import com.ldedusoft.ldbm.model.Appointment;
import com.ldedusoft.ldbm.model.CarCode;
import com.ldedusoft.ldbm.model.CarColor;
import com.ldedusoft.ldbm.model.CarList;
import com.ldedusoft.ldbm.model.CarType;
import com.ldedusoft.ldbm.model.CarWarehouse;
import com.ldedusoft.ldbm.model.Client;
import com.ldedusoft.ldbm.model.ClientType;
import com.ldedusoft.ldbm.model.FixingInfo;
import com.ldedusoft.ldbm.model.FixingsWarehouse;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.Invoice;
import com.ldedusoft.ldbm.model.Progress;
import com.ldedusoft.ldbm.model.Reception;
import com.ldedusoft.ldbm.model.RepaireType;
import com.ldedusoft.ldbm.model.SalesMan;
import com.ldedusoft.ldbm.model.TrafficClass;

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
}
