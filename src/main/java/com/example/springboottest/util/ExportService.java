package com.example.springboottest.util;//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.quancai.utils.ReturnObject;
//import com.shelectric.despso.finance.tool.config.AppConfig;
//import com.shelectric.despso.finance.tool.dao.AnalysisProjectOptimizeResultDao;
//import com.shelectric.despso.finance.tool.dao.entity.AnalysisProjectOptimizeResultDto;
//import com.shelectric.despso.finance.tool.dao.query.AnalysisProjectOptimizeResultQuery;
//import com.shelectric.despso.finance.tool.dao.query.ExportAnalysisEntity;
//import com.shelectric.despso.finance.tool.service.IExPortService;
//import freemarker.template.Template;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//import sun.misc.BASE64Encoder;
//import top.leoxiao.common.db.pojo.PageReq;
//import freemarker.template.Configuration;
//
//import java.io.*;
//import java.util.*;
//
//
//@Data
//public abstract class ExportService implements IExPortService{
//    @Autowired
//    public AnalysisProjectOptimizeResultDao analysisProjectOptimizeResultDao;
//    @Autowired
//    private AppConfig appConfig;
//
//
//  public ReturnObject<String> exportWord(AnalysisProjectOptimizeResultQuery params, PageReq page){
//      return null;
//  }


//    @Autowired
//    FreeMarkerConfigurer freeMarkerConfigurer;
//
//    /**
//     *
//     * @PARAM map 用于模板填充的数据
//     * @return
//     * @throws Exception
//     */
//    public String exportWord(Map<String,Object> map) throws Exception {
//        String path = (String)map.remove("path");
//        String canonicalPath = new File("").getCanonicalPath();//获取当前项目目录路径
//        File outFile = new File(canonicalPath+path.substring(0,path.lastIndexOf("/")));
//        if (!outFile.exists()) {
//            outFile.mkdirs();
//        }
//        outFile=new File(canonicalPath+path);

//        try(Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), appConfig.getEncoding()),10240)) {
//        Configuration configuration = new Configuration();
//        configuration.setDefaultEncoding(appConfig.getEncoding());
//        String s = canonicalPath + appConfig.getFilePath();
//        appConfig.setFileName("/template"+appConfig.getFileName());
//        Template t =  freeMarkerConfigurer.getConfiguration().getTemplate(appConfig.getFileName(),appConfig.getEncoding());
//        t.process(map, out);
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//        return path;
//    }

   /* public static String fileNamePath="D:/test(01).doc";
    public static String filePath="D:\\GIT\\";//模板绝对路径
    public static String fileName="finance-tool\\uncertainlyAnalysisTemplate.ftl";
    public static String encoding="utf-8";
    public static final Integer MAX_COLUMN=18;//要显示多少行
    public static final Integer MAX_ROW=11;//需要显示的列
*/

//    /**
//     * 该方法用于拆分表格
//     * @param map map key value键值对 key是表名 value是表的数据
//     * @param condition 用于判断传的是哪个表格
//     * @return
//     */
//  public Map<String,List<List<List<String>>>>  packagingData(Map<String,List<List<String>>> map,String condition){
//       //构造数据
//       Map<String,List<List<List<String>>>> map1=new LinkedHashMap<>();
//       List<String> list=new ArrayList<>();
//       List<List<String>> lists=new ArrayList<>();
//      Integer max_column = appConfig.getMAX_COLUMN();
//      List<List<List<String>>> listTable=new ArrayList<>();
//
//       List<List<List<List<String>>>> listRows=new ArrayList<>();
//       listTable.add(lists);//代表一个表格
//       String[] strings = map.keySet().toArray(new String[]{});
//       Iterator<String> iterator = map.keySet().iterator();
//        int c=0;
//        boolean flag=true;
//        int o=0;
//       while(iterator.hasNext()){
//           String next = iterator.next();
//           List<List<String>> lists1 = map.get(next);//lists1所有行
//           int p=0;
//           int count=0;
//           int i1 = lists1.size() / appConfig.getMAX_ROW();
//           listTable=new ArrayList<>();
//           listTable.add(lists);
//           map1.put(next,listTable);
//           Integer max_column1 = appConfig.getMAX_COLUMN();
//           int sum=max_column1;
//           int i2 = lists1.get(0).size()/(appConfig.getMAX_COLUMN()-3)+1;
//           for (int i=0;i<i2;i++){
//
//               if (condition.equals("计算结果")){
//                   if (i>0){
//                       if (i==1){
//                           sum=max_column1;
//                       }else{
//                           sum+=(appConfig.getMAX_COLUMN()-3);
//                       }
//                   }else{
//                       sum=0;
//                   }
//               }else{
//                   sum=i*sum;
//               }
//
//               for (int k=0;k<lists1.size();k++){//lists1 代表所有的行
//                  List<String>  list1 = lists1.get(k);//获取第K行
//
//
//                 if (list1.isEmpty()||lists.size()>=lists1.size()){
//                    o=listTable.size();
//                    if (lists.isEmpty()){
//                        break;
//                    }
//                    lists=new ArrayList<>();
//                    listTable.add(lists);//代表一个表格*/
//                    break;
//                 }
//                 if (lists.size()%appConfig.getMAX_ROW()==0&&lists.size()!=0){//代表lists已经包含了一个表格该有的行和列
//                     p++;
//                     flag=false;
//                     lists=new ArrayList<>();
//                     listTable.add(lists);//代表一个表格
//
//                    }
//                   lists.add(list);//lists代表所有行 一个新的lists就是代表拆分出来的行
//
//               for (int j=sum;j<list1.size();j++){//获取行里面的所有列
//
//                   if (condition.equals("计算结果")){//同一个表拆分出来的一个表  解决  一个表拆分成多个表没有表头 的问题
//                       if (flag==false&&k!=0&&j==0){
//                           if (p>0){
//                               lists.add(0,listTable.get(0).get(0));
//                               flag=true;
//                           }
//                       }else{
//                           if (i!=0&&j==sum){
//                               if (k==0){
//                                   list.add(lists1.get(k).get(0));
//                                   list.add(lists1.get(k).get(1));
//                                   list.add(lists1.get(k).get(2));
//
//                               }else{
//                                   if (lists.size()==1){
//                                    lists.add(0,listTable.get(o).get(0));
//                                   }
//                                   list.add(lists1.get(k).get(0));
//                                   list.add(lists1.get(k).get(1));
//                                   list.add(lists1.get(k).get(2));
//                               }
//                           }
//                       }
//                       }
//
//                   String s = list1.get(j);//把第k行的第j个列塞进去
//                   list.add(s);
//               if (list.size()%appConfig.getMAX_COLUMN()==0&&list.size()!=0||j>=list1.size()-1){//判断是否超出了设定的列  超出了则重新创建行
//                   list=new ArrayList<>();//代表一行中的所有列
//                   break;  //获取下一行的MAX_COLUMN列
//               }
//           }
//
//               }
//
//           }
//           if (condition.equals("计算结果")){
//               listTable.remove(listTable.size()-1);
//           }
//           c++;
//       }
//           return map1;
//    }
//
//    //图像转base64
//    public  String getImageString(String imgname)  {
//        InputStream in = null;
//        byte[] data = null;
//        try {
//            in = new FileInputStream(imgname);
//            data = new byte[in.available()];
//            in.read(data);
//
//
//            in.close();
//        } catch (Exception e) {
//
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        BASE64Encoder encoder = new BASE64Encoder();
//        return data != null ? encoder.encode(data) : "";
//    }
//
//    public ReturnObject<String> export(ExportAnalysisEntity exportAnalysisEntity){
//      return null;
//    }
//}
