package com.ldedusoft.ldbm.interfacekits;

import com.ldedusoft.ldbm.model.Appointment;
import com.ldedusoft.ldbm.model.CarCode;
import com.ldedusoft.ldbm.model.InputItem;
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
    /*新建表单*/
    public static String RP_ReceptionNewResult = "RP_ReceptionNewResult";
    /*维修类型列表*/
    public static String RP_RepaireTypeResult = "RP_RepaireTypeResult";
    /*业务类型列表*/
    public static String RP_TrafficClassResult = "RP_TrafficClassResult";
    /*预约维修列表*/
    public static String AP_AppointmentListResult = "AP_AppointmentListResult";

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
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String danhao = jsonObject.getString("DanHao");
            String formMaker = jsonObject.getString("FormMaker");
            for (InputItem item : listData){
                if("Number".equals(item.getItemId())){
                    item.setValue(danhao);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
}
