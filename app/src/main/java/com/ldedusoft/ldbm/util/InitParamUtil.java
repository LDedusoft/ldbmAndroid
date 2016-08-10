package com.ldedusoft.ldbm.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.MenuItem;
import com.ldedusoft.ldbm.model.SysProperty;
import com.ldedusoft.ldbm.model.UserProperty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjianwei on 2016/6/27.
 */
public class InitParamUtil {

    private SharedPreferences pref; //保存文件
    private Context mContent;
    public InitParamUtil(Context context){
     mContent = context;
    }

    public static InitParamUtil getInstance(Context context){
        return new InitParamUtil(context);
    }


    /*初始化全部菜单*/
    public void initAllMenuList() {
        ArrayList<MenuItem> allMenuList = new ArrayList<MenuItem>();
       String config = readConfig("allMenuItem.txt");
        try {
            JSONArray jsonArray = new JSONArray(config);
            int length = jsonArray.length();
            for (int i=0;i<length;i++){
                MenuItem menuItem = new MenuItem();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Class<com.ldedusoft.ldbm.R.drawable> cls = R.drawable.class;
                int iconId = 0;
                try{
                    iconId = cls.getDeclaredField(jsonObject.getString("iconId")).getInt(null);
                }catch (Exception e){
                }
                menuItem.setMenuTitle(jsonObject.getString("menuTitle"));
                menuItem.setMenuDescribe(jsonObject.getString("menuDescribe"));
                menuItem.setIconId(iconId);
                menuItem.setAllowCreate(jsonObject.getBoolean("allowCreate"));
                menuItem.setValue(jsonObject.getString("value"));
                menuItem.setTitleIntentPath(jsonObject.getString("titleIntentPath"));
                menuItem.setCreateIntentPath(jsonObject.getString("createIntentPath"));
                menuItem.setIsGroup(jsonObject.getBoolean("isGroup"));
                menuItem.setGroupTitle(jsonObject.getString("groupTitle"));
                allMenuList.add(menuItem);
            }
            SysProperty.getInstance().setAllMenuList(allMenuList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*初始化其他页面菜单*/
    public void initMenu(){
        initHomeMenuList();
        initRepairMenuList();
        initCarMenuList();
        initNegotiateMenuList();
        initPartMenuList();
        initReportMenuList();
        reportKeyDic();
    }

    /*初始化首页菜单*/
    public void initHomeMenuList() {
        pref = PreferenceManager.getDefaultSharedPreferences(mContent);
        ArrayList<MenuItem> homeMenuItemList = new ArrayList<MenuItem>();
        ArrayList<MenuItem> allMenuItemList = SysProperty.getInstance().getAllMenuList();
        String userItemsStr = pref.getString(UserProperty.getInstance().getUserName(), "");//获取用户配置
        String[] sysItemsArray = mContent.getResources().getStringArray(R.array.home_menu_item);//从配置中获取默认的菜单项
//        if(!TextUtils.isEmpty(userItemsStr)){  //如果用户已修改菜单，则使用用户配置
        String[]  userItemsArray = userItemsStr.split(","); //用户配置文件中保存的菜单项
//        }
        if(userItemsArray!=null&&userItemsArray.length>0) { //如果用户配置不为空,遍历用户配置
            for (String itemName : userItemsArray) {
                for (MenuItem itemObj : allMenuItemList) {
                    if (itemName.equals(itemObj.getMenuTitle())) {
                        itemObj.setIsHomeMenu(true); //加入首页时需要设置标志（废除，改在MenuListAdapter中判断当前activity）
                        homeMenuItemList.add(itemObj);
                        break;
                    }
                }
            }
        }else{  //如果用户配置为空,使用默认配置
            for (String itemName : sysItemsArray) {
                for (MenuItem itemObj : allMenuItemList) {
                    if (itemName.equals(itemObj.getMenuTitle())) {
                        itemObj.setIsHomeMenu(true); //加入首页时需要设置标志（废除，改在MenuListAdapter中判断当前activity）
                        homeMenuItemList.add(itemObj);
                        break;
                    }
                }
            }
        }
        homeMenuItemList.add(null); //添加快捷功能按钮
        SysProperty.getInstance().setHomeMenuList(homeMenuItemList);
    }



    /*初始化维修页面菜单*/
    private void initRepairMenuList() {
        ArrayList<MenuItem> menuItemList =getMenuList(R.array.repair_menu_item);
        SysProperty.getInstance().setRepairMenuList(menuItemList);
    }

    /*初始化整车页面菜单*/
    private void initCarMenuList() {
        ArrayList<MenuItem> menuItemList =getMenuList(R.array.car_menu_item);
        SysProperty.getInstance().setCarMenuList(menuItemList);
    }

    /*初始化整车洽谈页面菜单*/
    private void initNegotiateMenuList() {
        ArrayList<MenuItem> menuItemList =getMenuList(R.array.negotiate_menu_item);
        SysProperty.getInstance().setNegotiateMenuList(menuItemList);
    }

    /*初始化配件页面菜单*/
    private void initPartMenuList() {
        ArrayList<MenuItem> menuItemList =getMenuList(R.array.part_menu_item);
        SysProperty.getInstance().setPartMenuList(menuItemList);
    }

    /*初始化报表页面菜单*/
    private void initReportMenuList() {
        ArrayList<MenuItem> menuItemList =getMenuList(R.array.report_menu_item);
        SysProperty.getInstance().setReportMenuList(menuItemList);
    }

    /*获取菜单配置*/
    private ArrayList<MenuItem> getMenuList(int menuId){
        ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();
        ArrayList<MenuItem> allMenuItemList = SysProperty.getInstance().getAllMenuList();
        String[] userItemsArray = mContent.getResources().getStringArray(menuId);//从配置中获取默认的菜单项
        for (String itemName : userItemsArray) {
            for (MenuItem itemObj : allMenuItemList ) {
                if (itemName.equals(itemObj.getMenuTitle())) {
                    menuItemList.add(itemObj);
                    break;
                }
            }
        }
        return menuItemList;
    }

    /*新配件信息form*/
    public  ArrayList<InputItem> initPT_NewFixings() {
        String config = readConfig("saveFixInfo.txt");
        return createInputItemList(config);
    }

    /*维修预约form*/
    public  ArrayList<InputItem> initRP_ReceptionNew_YY() {
        String config = readConfig("repairYY.txt");
        return createInputItemList(config);
    }

    /*维修接待form*/
    public  ArrayList<InputItem> initRP_ReceptionNew_WX() {
        String config = readConfig("repairWX.txt");
        return createInputItemList(config);
    }

    /*保存车辆信息form*/
    public  ArrayList<InputItem> initPub_SaveCar() {
        String config = readConfig("saveCar.txt");
        return createInputItemList(config);
    }

    /*新建保存客户信息form*/
    public  ArrayList<InputItem> initPub_SaveClient() {
        String config = readConfig("saveClient.txt");
        return createInputItemList(config);
    }

    /*新建个人洽谈form*/
    public  ArrayList<InputItem> initSC_SaveNegotiate_person() {
        String config = readConfig("savePersonNegotiate.txt");
        return createInputItemList(config);
    }

    /*新建公司洽谈form*/
    public  ArrayList<InputItem> initSC_SaveNegotiate_company() {
        String config = readConfig("saveCompanyNegotiate.txt");
        return createInputItemList(config);
    }

    /*整车销售form*/
    public  ArrayList<InputItem> initSC_SavePurchase() {
        String config = readConfig("saveCarPurchase.txt");
        return createInputItemList(config);
    }

    /*配件销售form*/
    public  ArrayList<InputItem> initSC_SaveSaleFixings() {
        String config = readConfig("saveFixSale.txt");
        return createInputItemList(config);
    }

    /*配件采购form*/
    public  ArrayList<InputItem> initPT_SavePurchaseFixings() {
        String config = readConfig("saveFixPurchase.txt");
        return createInputItemList(config);
    }

    /**
     * 生成输入表单item列表
     * @param config
     * @return
     */
    private ArrayList<InputItem>  createInputItemList(String config){
        ArrayList<InputItem> itemList = new ArrayList<InputItem>();
        try {
            JSONArray jsonArray = new JSONArray(config);
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                InputItem inputItem = new InputItem();
                inputItem.setItemTitle(jsonObject.getString("itemTitle"));
                inputItem.setValue(jsonObject.getString("value"));
                inputItem.setHint(jsonObject.getString("hint"));
                inputItem.setDefaultValue(jsonObject.getString("defaultValue"));
                inputItem.setInputType(jsonObject.getInt("inputType"));
                inputItem.setIntentPath(jsonObject.getString("intentPath"));
                inputItem.setIntentRequestCode(jsonObject.getInt("intentRequestCode"));
                inputItem.setItemId(jsonObject.getString("itemId"));
                inputItem.setIntentParam(jsonObject.getString("intentParam"));
                inputItem.setRequired(jsonObject.getBoolean("required"));
                if(jsonObject.has("relationItem")){
                    inputItem.setRelationItem(jsonObject.getString("relationItem"));
                }
                if(jsonObject.has("unit")){
                    inputItem.setUnit(jsonObject.getString("unit"));
                }
                itemList.add(inputItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemList;
    }

    /**
     * 从assets中读取txt
     */
    private String readConfig(String fileName) {
        String content = "";
        try {
            InputStream is = mContent.getAssets().open(fileName);
            content = readTextFromFile(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 按行读取txt
     *
     * @param is
     * @return
     * @throws Exception
     */
    private String readTextFromFile(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /*初始化报表页面中key对应的中文*/
    private void reportKeyDic(){
        LinkedHashMap<String,String> dic = new LinkedHashMap<String,String>();
        //收款统计
        //{BianHao：客户编号；MingCheng：客户名称；QianKuan：客户欠款；ShiShou：实际收款；YingShou：应收款}
        dic.put("收款统计MingCheng","客户名称");
        dic.put("收款统计BianHao","客户编号");
        dic.put("收款统计QianKuan","客户欠款");
        dic.put("收款统计ShiShou","实际收款");
        dic.put("收款统计YingShou","应收款");
        //客户账务统计
        //返回值格式：{BianHao：客户编号；MingCheng：客户名称；QianKuan：客户欠款；LinkMan：联系人；ZuiDaEDu：信誉额度金额；cedu：超信誉额度金额}
        dic.put("客户账务统计MingCheng","客户名称");
        dic.put("客户账务统计BianHao","客户编号");
        dic.put("客户账务统计QianKuan","客户欠款");
        dic.put("客户账务统计LinkMan","联系人");
        dic.put("客户账务统计ZuiDaEDu","信誉额度金额");
        dic.put("客户账务统计cedu","超信誉额度金额");
        /*
    * 进货汇总
    * 返回值格式：{XiangMu：项目；Num：数量合计；NPercent：数量百分比；Jine：金额合计；JPercent：金额百分比}
    * */
        dic.put("进货汇总XiangMu","项目");
        dic.put("进货汇总Num","数量合计");
        dic.put("进货汇总NPercent","数量百分比");
        dic.put("进货汇总Jine","金额合计");
        dic.put("进货汇总JPercent","金额百分比");

        /*
    * 采购订单
    * 返回值格式：{BianHao：配件编号；MingCheng：配件名称；DNum：订单数量；DJine：订单金额；
    * GNum：进货数量；GJine：进货金额；RNum：入库数量；RJine：入库金额；JBilv：进货比率；RBilv：入库比率}
    * */
        dic.put("采购订单BianHao","配件编号");
        dic.put("采购订单MingCheng","配件名称");
        dic.put("采购订单DNum","订单数量");
        dic.put("采购订单DJine","订单金额");
        dic.put("采购订单GNum","进货数量");
        dic.put("采购订单GJine","进货金额");
        dic.put("采购订单RNum","入库数量");
        dic.put("采购订单RJine","入库金额");
        dic.put("采购订单JBilv","进货比率");
        dic.put("采购订单RBilv","入库比率");

        /*
        *供应商累计供货排行
        * {BianHao：供商编号；MingCheng：供商名称；Num：累计供货数量；Jine：含税金额；BJine：不含税金额}
        * */
        dic.put("供应商累计供货排行MingCheng","供商名称");
        dic.put("供应商累计供货排行BianHao","供商编号");
        dic.put("供应商累计供货排行Num","累计供货数量");
        dic.put("供应商累计供货排行Jine","含税金额");
        dic.put("供应商累计供货排行BJine","不含税金额");

        /*
        *销售计划分析表
        * {BianHao：配件编号；MingCheng：配件名称；DNum：订单数量；DJine：订单金额；GNum：进货数量；
        * GJine：进货金额；RNum：出库数量；RJine：出库金额；JBilv：销售比率；RBilv：出库比率}
        * */
        dic.put("销售计划分析表MingCheng","配件名称");
        dic.put("销售计划分析表BianHao","配件编号");
        dic.put("销售计划分析表DNum","订单数量");
        dic.put("销售计划分析表DJine","订单金额");
        dic.put("销售计划分析表GNum","进货数量");
        dic.put("销售计划分析表GJine","进货金额");
        dic.put("销售计划分析表RNum","出库数量");
        dic.put("销售计划分析表RJine","出库金额");
        dic.put("销售计划分析表JBilv","销售比率");
        dic.put("销售计划分析表RBilv","出库比率");

        /*
        *客户销售额排行
        *{BianHao：供商编号；MingCheng：供商名称；Num：销售数量；Jine：销售金额；LinkMan：联系人；
        * ChengBenZh：成本金额；LiRun：毛利润}
        * */
        dic.put("客户销售额排行MingCheng","供商名称");
        dic.put("客户销售额排行BianHao","供商编号");
        dic.put("客户销售额排行Num","销售数量");
        dic.put("客户销售额排行Jine","销售金额");
        dic.put("客户销售额排行LinkMan","联系人");
        dic.put("客户销售额排行ChengBenZh","成本金额");
        dic.put("客户销售额排行LiRun","毛利润");

        /*
        *日期销售报表
        *{RiQi：日期；Jine：销售额；ChengBen：销售成本；LiRun：毛利润；Count：来客数；PingJun：平均客买}
        * */
        dic.put("日期销售报表RiQi","日期");
        dic.put("日期销售报表Jine","销售额");
        dic.put("日期销售报表ChengBen","销售成本");
        dic.put("日期销售报表LiRun","毛利润");
        dic.put("日期销售报表Count","来客数");
        dic.put("日期销售报表PingJun","平均客买");

        /*
        *仓库销售排行
        *{BianHao：仓库编号；CangKu：仓库名称；Num：销售数量；Jine：销售金额；ChengBen：成本金额；LiRun：毛利润}
        * */
        dic.put("仓库销售排行CangKu","仓库名称");
        dic.put("仓库销售排行BianHao","仓库编号");
        dic.put("仓库销售排行Num","销售数量");
        dic.put("仓库销售排行Jine","销售金额");
        dic.put("仓库销售排行ChengBen","成本金额");
        dic.put("仓库销售排行LiRun","毛利润");


       /*
   * 个人洽谈
    * {Name：客户名称；Sex：客户性别；cType：客户类型：Phone：电话；Time：方便时间；Plan：购车方案；WantCar：意向车型的id；WantTime：预购时间}
 * */
        dic.put("个人洽谈Name","客户名称");
        dic.put("个人洽谈Sex","客户性别");
        dic.put("个人洽谈cType","客户类型");
        dic.put("个人洽谈Phone","电话");
        dic.put("个人洽谈Time","方便时间");
        dic.put("个人洽谈Plan","购车方案");
        dic.put("个人洽谈WantCar","意向车型的id");
        dic.put("个人洽谈WantTime","预购时间");
        dic.put("个人洽谈LinkTime","方便时间");
        dic.put("个人洽谈DanHao","单号");
        dic.put("个人洽谈Date","日期");
        dic.put("个人洽谈jsr","经手人");
        dic.put("个人洽谈zdr","制单人");


         /*
  * 公司洽谈
   * ClientType为"公司"时{Name：公司名称；Sex：负责人；Phone：电话；cType：联系人；Time：联系人电话；
   * Plan：购车方案；WantCar：意向车型的id；WantTime：预购时间}
* */
        dic.put("公司洽谈Name","公司名称");
        dic.put("公司洽谈Sex","负责人");
        dic.put("公司洽谈Phone","电话");
        dic.put("公司洽谈cType","联系人");
        dic.put("公司洽谈Time","联系人电话");
        dic.put("公司洽谈Plan","购车方案");
        dic.put("公司洽谈WantCar","意向车型的id");
        dic.put("公司洽谈WantTime","预购时间");
        dic.put("个人洽谈LinkTime","方便时间");
        dic.put("个人洽谈DanHao","单号");
        dic.put("个人洽谈Date","日期");
        dic.put("个人洽谈jsr","经手人");
        dic.put("个人洽谈zdr","制单人");


        /*
* 销售单
 *{ID：ID；DanHao：单号；Date：时间；dPrice：订金总额；Num：订单数量；Price：订单金额；ZhiDanRen：制单人；JingShouRen：经手人}
* */
        dic.put("销售单DanHao","单号");
        dic.put("销售单Date","时间");
        dic.put("销售单dPrice","订金总额");
        dic.put("销售单Num","订单数量");
        dic.put("销售单Price","订单金额");
        dic.put("销售单ZhiDanRen","制单人");
        dic.put("销售单JingShouRen","经手人");


           /*
* 配件销售单
*{ID：ID；DanHao：单号；RiQi：日期；JinE：单据金额；Num：单据数量；GongFang：供方单位；JingShouRen：经手人；ZhiDanRen：制单人}
* */
        dic.put("配件销售单DanHao","单号");
        dic.put("配件销售单RiQi","日期");
        dic.put("配件销售单JinE","单据金额");
        dic.put("配件销售单Num","单据数量");
        dic.put("配件销售单GongFang","供方单位");
        dic.put("配件销售单JingShouRen","经手人");
        dic.put("配件销售单ZhiDanRen","制单人");


            /*
* 配件采购单
*{ID：ID；DanHao：单号；RiQi：日期；JinE：金额；Num：数量；JingShouRen：经手人；ZhiDanRen：制单人}
* */
        dic.put("配件采购单DanHao","单号");
        dic.put("配件采购单RiQi","日期");
        dic.put("配件采购单JinE","金额");
        dic.put("配件采购单Num","数量");
        dic.put("配件采购单JingShouRen","经手人");
        dic.put("配件采购单ZhiDanRen","制单人");

                    /*
        * 预约维修
        *{Id：id；DanHao：单号；djTime：登记时间；yyTime：预约时间；CarCode：车牌号；MingCheng：客户名称；
        * wxFangShi：维修方式；ywLeiBie：业务类别；dxTime:弹性时间}
        * */
        dic.put("预约维修DanHao","单号");
        dic.put("预约维修djTime","登记时间");
        dic.put("预约维修yyTime","预约时间");
        dic.put("预约维修CarCode","车牌号");
        dic.put("预约维修MingCheng","客户名称");
        dic.put("预约维修wxFangShi","维修方式");
        dic.put("预约维修ywLeiBie","业务类别");
        dic.put("预约维修dxTime","弹性时间");

       /*
       * 维修接待
       * {Id：id；DanHao：单号；jdTime：接待时间；CarCode：车牌号；MingCheng：客户名称；WeiXiuWay：维修方式；
       * ywLeiBie：业务类别;SongXiuPeople:送修人；SongXiuPhone：送修人电话；CarOils：油品；BenCiCunYou：油量；
       * BenCiMileage：里程；NextByTime：下次保养时间；ChengBaoGongSi：承包公司；DingSunYuan：定损员；YeWuBeiZhu：备注；
       * JingShouRen：业务员；ZhiDanRen：制单人；IsBywh：是否是保养；NextByMileage：下次保养里程；YuJiWanGong：预计完工时间}
       * */
        dic.put("维修接待DanHao","单号");
        dic.put("维修接待jdTime","接待时间");
        dic.put("维修接待CarCode","车牌号");
        dic.put("维修接待MingCheng","客户名称");
        dic.put("维修接待WeiXiuWay","维修方式");
        dic.put("维修接待ywLeiBie","业务类别");
        dic.put("维修接待SongXiuPeople","送修人");
        dic.put("维修接待SongXiuPhone","送修人电话");
        dic.put("维修接待CarOils","油品");
        dic.put("维修接待BenCiCunYou","油量");
        dic.put("维修接待BenCiMileage","里程");
        dic.put("维修接待NextByTime","下次保养时间");
        dic.put("维修接待ChengBaoGongSi","承包公司");
        dic.put("维修接待DingSunYuan","定损员");
        dic.put("维修接待YeWuBeiZhu","备注");
        dic.put("维修接待JingShouRen","业务员");
        dic.put("维修接待ZhiDanRen","制单人");
        dic.put("维修接待IsBywh","是否是保养");
        dic.put("维修接待NextByMileage","下次保养里程");
        dic.put("维修接待YuJiWanGong","预计完工时间");

        SysProperty.getInstance().setReportKeyDic(dic);
    }


    public static List<Map<String, String>> getGroupMenuList(){
         List<Map<String, String>> parentList = new ArrayList<Map<String, String>>();
        String[] groupArray = {"维修管理","整车管理","配件管理","报表管理"};
        for (int i = 0; i < groupArray.length; i++)
        {
            Map<String, String> groupMap = new HashMap<String, String>();
            groupMap.put("groupText",groupArray[i]);
            groupMap.put("isGroupCheckd", "No");
            parentList.add(groupMap);
        }
        return parentList;
    }

    public static  List<List<Map<String, String>>> getChildMenuList(String[] userConfigArray){
         List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        String[][] groupArray = {{"预约维修","维修接待","进度查询"},
                {"洽谈","销售"},
                {"采购计划","配件采购","配件销售单"},
                {"报表","库存","销售查询","销量"}};
        for (int i = 0; i < groupArray.length; i++)
        {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (int j = 0; j < groupArray[i].length; j++)
            {
                Map<String, String> map = new HashMap<String, String>();
                map.put("childItem", groupArray[i][j]);
                for(int k=0;k<userConfigArray.length;k++){ //判断是否已选中
                    if(groupArray[i][j].equals(userConfigArray[k])){
                        map.put("isChecked", "Yes");
                        break;
                    }
                }

                list.add(map);
            }
            childData.add(list);
        }
        return childData;
    }

}
