package match.w500.data.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import match.data.parse.HtmlParse;
import match.html.parse.util.HtmlUtil;
import match.html.parse.util.HtmlUtilBean;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/***
 * 500w某场比赛分析数据解析
 * http://odds.500.com/fenxi/shuju-552514.shtml
 * @author xgh
 *
 */
public class Fenxi_shuju_parse extends HtmlParse{
	
	

	public Fenxi_shuju_parse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fenxi_shuju_parse(String dataUrl) {
		super(dataUrl);
		
	}

	public static void main(String[] args) {
		Fenxi_shuju_parse dataParse = new Fenxi_shuju_parse("http://odds.500.com/fenxi/shuju-552514.shtml");
		//查看基本信息
		Map baseInfoMap = dataParse.getBaseInfo();
		System.out.println(baseInfoMap);
		//查看主队联赛成绩
		List list = dataParse.getHometeamLeagueTotalChengji();
		System.out.println(list);
		//查看主队联赛成绩
		List list2 = dataParse.getAwayteamLeagueTotalChengji();
		System.out.println(list2);
		
		//查看近期战绩
		Map zhanjiMap= dataParse.getJinqiZhanji();
		System.out.println(zhanjiMap);
		
		//查看近期走势
        System.out.println(dataParse.getJingqiZoushi());
	}
	
	/***
	 * 解析出比赛基本信息
	 * @return
	 */
	public Map<Object,Object> getBaseInfo(){
		HtmlUtilBean htmlUtil = new HtmlUtilBean(this.getDocument());
		
		Element div = htmlUtil.getFirstElement("div.M_sub_title");
		//主队名称
		String ht_name = div.select("div.team_name").get(0).textNodes().get(0).text();
		//主队联赛排名名称
		String htl_rankname=div.select("div.team_name").get(0).select("span").text();
		//客户名
		String at_name = div.select("div.team_name").get(1).textNodes().get(0).text();
		//客队联赛排名名称
	    String atl_rankname=div.select("div.team_name").get(1).select("span").text();
	    
	    
	    Map<Object,Object> table1 = new HashMap<Object,Object>();
	    table1.put("ht_name", ht_name);
	    table1.put("htl_rankname",htl_rankname);
	    table1.put("at_name",at_name);
	    table1.put("atl_rankname",atl_rankname);
	    
	    return table1;
	}
	
	/***
	 * 解析主队在联赛的成绩：
	 *    比赛	    胜	平	负	进	失	净	积分	排名	胜率
	 * 总
	 * 主场
	 * 客场
	 * @return
	 */
	public List<Map> getHometeamLeagueTotalChengji(){
		HtmlUtilBean HtmlUtil = new HtmlUtilBean(this.getDocument());
		Element tbody1 = HtmlUtil.getFirstElement("div.team_a table.pub_table tbody");
	    Elements trs1=tbody1.select("tr");
	    //比赛	胜	平	负	进	失	净	积分	排名	胜率
	    /****所在联赛当前-总成绩  start****/
	    int rowIndex=1;
	    //比赛次数
	    String match_count= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 1);//第2行第2列的值
	    String win_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 2);//第2行第3列的值
	    String draw_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 3);//第2行第4列的值
	    String lost_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 4);//第2行第5列的值
	    //进球数
	    String jingqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 5);//第2行第6列的值
	    String shiqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 6);//第2行第7列的值
	    String jingshengqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 7);//第2行第8列的值
	    //联赛积分
	    String jifen= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 8);//第2行第9列的值
	    //联赛排名
	    String rank= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 9);//第2行第10列的值
	    //胜率
	    String shenglv= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 10);//第2行第11列的值
	   
	    Map<Object,Object> total_row = new HashMap<Object,Object>();
	    total_row.put("chengji_type", "T");//联赛成绩类型：T-总成绩,H-主场成绩,A-客场成绩
	    total_row.put("TeamType", "H");//球队类型：H-主队,A-客队
	    total_row.put("match_count", Integer.parseInt(match_count));
	    total_row.put("win_count", Integer.parseInt(win_count));
	    total_row.put("draw_count", Integer.parseInt(draw_count));
	    total_row.put("lost_count", Integer.parseInt(lost_count));
	    total_row.put("jingqiushu", Integer.parseInt(jingqiushu));
	    total_row.put("shiqiushu", Integer.parseInt(shiqiushu));
	    total_row.put("jingshengqiushu", Integer.parseInt(jingshengqiushu));
	    total_row.put("jifen", Integer.parseInt(jifen));
	    total_row.put("rank", Integer.parseInt(rank));
	    total_row.put("shenglv", Float.parseFloat(shenglv.replace("%", "")));
	    /****所在联赛当前-总成绩  end****/
	    
	    //比赛	胜	平	负	进	失	净	积分	排名	胜率
	    /****所在联赛当前-主场成绩  start****/
	    rowIndex=2;//第3行
	    //比赛次数
	    match_count= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 1);//第2行第2列的值
	    win_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 2);//第2行第3列的值
	    draw_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 3);//第2行第4列的值
	    lost_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 4);//第2行第5列的值
	    //进球数
	    jingqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 5);//第2行第6列的值
	    shiqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 6);//第2行第7列的值
	    jingshengqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 7);//第2行第8列的值
	    //联赛积分
	    jifen= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 8);//第2行第9列的值
	    //联赛排名
	    rank= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 9);//第2行第10列的值
	    //胜率
	    shenglv= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 10);//第2行第11列的值
	   
	    Map<Object,Object> zhuchang_row = new HashMap<Object,Object>();
	    zhuchang_row.put("chengji_type", "H");//联赛成绩类型：T-总成绩,H-主场成绩,A-客场成绩
	    zhuchang_row.put("TeamType", "H");//球队类型：H-主队,A-客队
	    zhuchang_row.put("match_count", Integer.parseInt(match_count));
	    zhuchang_row.put("win_count", Integer.parseInt(win_count));
	    zhuchang_row.put("draw_count", Integer.parseInt(draw_count));
	    zhuchang_row.put("lost_count", Integer.parseInt(lost_count));
	    zhuchang_row.put("jingqiushu", Integer.parseInt(jingqiushu));
	    zhuchang_row.put("shiqiushu", Integer.parseInt(shiqiushu));
	    zhuchang_row.put("jingshengqiushu", Integer.parseInt(jingshengqiushu));
	    zhuchang_row.put("jifen", Integer.parseInt(jifen));
	    zhuchang_row.put("rank", Integer.parseInt(rank));
	    zhuchang_row.put("shenglv", Float.parseFloat(shenglv.replace("%", "")));
	    /****所在联赛当前-主场成绩  end****/
	   
	   
	    
	  //比赛	胜	平	负	进	失	净	积分	排名	胜率
	    /****所在联赛当前-客场成绩  start****/
	    rowIndex=3;//第4行
	    //比赛次数
	    match_count= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 1);//第2行第2列的值
	    win_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 2);//第2行第3列的值
	    draw_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 3);//第2行第4列的值
	    lost_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 4);//第2行第5列的值
	    //进球数
	    jingqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 5);//第2行第6列的值
	    shiqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 6);//第2行第7列的值
	    jingshengqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 7);//第2行第8列的值
	    //联赛积分
	    jifen= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 8);//第2行第9列的值
	    //联赛排名
	    rank= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 9);//第2行第10列的值
	    //胜率
	    shenglv= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 10);//第2行第11列的值
	   
	    Map<Object,Object> kechang_row = new HashMap<Object,Object>();
	    kechang_row.put("chengji_type", "A");//联赛成绩类型：T-总成绩,H-主场成绩,A-客场成绩
	    kechang_row.put("TeamType", "H");//球队类型：H-主队,A-客队
	    kechang_row.put("match_count", Integer.parseInt(match_count));
	    kechang_row.put("win_count", Integer.parseInt(win_count));
	    kechang_row.put("draw_count", Integer.parseInt(draw_count));
	    kechang_row.put("lost_count", Integer.parseInt(lost_count));
	    kechang_row.put("jingqiushu", Integer.parseInt(jingqiushu));
	    kechang_row.put("shiqiushu", Integer.parseInt(shiqiushu));
	    kechang_row.put("jingshengqiushu", Integer.parseInt(jingshengqiushu));
	    kechang_row.put("jifen", Integer.parseInt(jifen));
	    kechang_row.put("rank", Integer.parseInt(rank));
	    kechang_row.put("shenglv", Float.parseFloat(shenglv.replace("%", "")));
	    /****所在联赛当前-客场成绩  end****/
	    
	   /* System.out.println(total_row);
	    System.out.println(zhuchang_row);
	    System.out.println(kechang_row);*/
	    
	    List<Map> list = new ArrayList<Map>();
	    list.add(total_row);//总成绩
	    list.add(zhuchang_row);//主场成绩
	    list.add(kechang_row);//客场成绩
	    
	    return list;
		
	}
	
	/***
	 * 解析客队在联赛的成绩：
	 *    比赛	    胜	平	负	进	失	净	积分	排名	胜率
	 * 总
	 * 主场
	 * 客场
	 * @return
	 */
	public List<Map> getAwayteamLeagueTotalChengji(){
		HtmlUtilBean HtmlUtil = new HtmlUtilBean(this.getDocument());
		Element tbody1 = HtmlUtil.getFirstElement("div.team_b table.pub_table tbody");
	    Elements trs1=tbody1.select("tr");
	    //比赛	胜	平	负	进	失	净	积分	排名	胜率
	    /****所在联赛当前-总成绩  start****/
	    int rowIndex=1;
	    //比赛次数
	    String match_count= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 1);//第2行第2列的值
	    String win_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 2);//第2行第3列的值
	    String draw_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 3);//第2行第4列的值
	    String lost_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 4);//第2行第5列的值
	    //进球数
	    String jingqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 5);//第2行第6列的值
	    String shiqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 6);//第2行第7列的值
	    String jingshengqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 7);//第2行第8列的值
	    //联赛积分
	    String jifen= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 8);//第2行第9列的值
	    //联赛排名
	    String rank= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 9);//第2行第10列的值
	    //胜率
	    String shenglv= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 10);//第2行第11列的值
	   
	    Map<Object,Object> total_row = new HashMap<Object,Object>();
	    total_row.put("chengji_type", "T");//联赛成绩类型：T-总成绩,H-主场成绩,A-客场成绩
	    total_row.put("TeamType", "H");//球队类型：H-主队,A-客队
	    total_row.put("match_count", Integer.parseInt(match_count));
	    total_row.put("win_count", Integer.parseInt(win_count));
	    total_row.put("draw_count", Integer.parseInt(draw_count));
	    total_row.put("lost_count", Integer.parseInt(lost_count));
	    total_row.put("jingqiushu", Integer.parseInt(jingqiushu));
	    total_row.put("shiqiushu", Integer.parseInt(shiqiushu));
	    total_row.put("jingshengqiushu", Integer.parseInt(jingshengqiushu));
	    total_row.put("jifen", Integer.parseInt(jifen));
	    total_row.put("rank", Integer.parseInt(rank));
	    total_row.put("shenglv", Float.parseFloat(shenglv.replace("%", "")));
	    /****所在联赛当前-总成绩  end****/
	    
	    //比赛	胜	平	负	进	失	净	积分	排名	胜率
	    /****所在联赛当前-主场成绩  start****/
	    rowIndex=2;//第3行
	    //比赛次数
	    match_count= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 1);//第2行第2列的值
	    win_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 2);//第2行第3列的值
	    draw_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 3);//第2行第4列的值
	    lost_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 4);//第2行第5列的值
	    //进球数
	    jingqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 5);//第2行第6列的值
	    shiqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 6);//第2行第7列的值
	    jingshengqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 7);//第2行第8列的值
	    //联赛积分
	    jifen= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 8);//第2行第9列的值
	    //联赛排名
	    rank= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 9);//第2行第10列的值
	    //胜率
	    shenglv= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 10);//第2行第11列的值
	   
	    Map<Object,Object> zhuchang_row = new HashMap<Object,Object>();
	    zhuchang_row.put("chengji_type", "H");//联赛成绩类型：T-总成绩,H-主场成绩,A-客场成绩
	    zhuchang_row.put("TeamType", "H");//球队类型：H-主队,A-客队
	    zhuchang_row.put("match_count", Integer.parseInt(match_count));
	    zhuchang_row.put("win_count", Integer.parseInt(win_count));
	    zhuchang_row.put("draw_count", Integer.parseInt(draw_count));
	    zhuchang_row.put("lost_count", Integer.parseInt(lost_count));
	    zhuchang_row.put("jingqiushu", Integer.parseInt(jingqiushu));
	    zhuchang_row.put("shiqiushu", Integer.parseInt(shiqiushu));
	    zhuchang_row.put("jingshengqiushu", Integer.parseInt(jingshengqiushu));
	    zhuchang_row.put("jifen", Integer.parseInt(jifen));
	    zhuchang_row.put("rank", Integer.parseInt(rank));
	    zhuchang_row.put("shenglv", Float.parseFloat(shenglv.replace("%", "")));
	    /****所在联赛当前-主场成绩  end****/
	   
	   
	    
	  //比赛	胜	平	负	进	失	净	积分	排名	胜率
	    /****所在联赛当前-客场成绩  start****/
	    rowIndex=3;//第4行
	    //比赛次数
	    match_count= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 1);//第2行第2列的值
	    win_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 2);//第2行第3列的值
	    draw_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 3);//第2行第4列的值
	    lost_count  = HtmlUtil.getText(trs1.get(rowIndex).select("td"), 4);//第2行第5列的值
	    //进球数
	    jingqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 5);//第2行第6列的值
	    shiqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 6);//第2行第7列的值
	    jingshengqiushu =  HtmlUtil.getText(trs1.get(rowIndex).select("td"), 7);//第2行第8列的值
	    //联赛积分
	    jifen= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 8);//第2行第9列的值
	    //联赛排名
	    rank= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 9);//第2行第10列的值
	    //胜率
	    shenglv= HtmlUtil.getText(trs1.get(rowIndex).select("td"), 10);//第2行第11列的值
	   
	    Map<Object,Object> kechang_row = new HashMap<Object,Object>();
	    kechang_row.put("chengji_type", "A");//联赛成绩类型：T-总成绩,H-主场成绩,A-客场成绩
	    kechang_row.put("TeamType", "H");//球队类型：H-主队,A-客队
	    kechang_row.put("match_count", Integer.parseInt(match_count));
	    kechang_row.put("win_count", Integer.parseInt(win_count));
	    kechang_row.put("draw_count", Integer.parseInt(draw_count));
	    kechang_row.put("lost_count", Integer.parseInt(lost_count));
	    kechang_row.put("jingqiushu", Integer.parseInt(jingqiushu));
	    kechang_row.put("shiqiushu", Integer.parseInt(shiqiushu));
	    kechang_row.put("jingshengqiushu", Integer.parseInt(jingshengqiushu));
	    kechang_row.put("jifen", Integer.parseInt(jifen));
	    kechang_row.put("rank", Integer.parseInt(rank));
	    kechang_row.put("shenglv", Float.parseFloat(shenglv.replace("%", "")));
	    /****所在联赛当前-客场成绩  end****/
	    
	   /* System.out.println(total_row);
	    System.out.println(zhuchang_row);
	    System.out.println(kechang_row);*/
	    
	    List<Map> list = new ArrayList<Map>();
	    list.add(total_row);//总成绩
	    list.add(zhuchang_row);//主场成绩
	    list.add(kechang_row);//客场成绩
	    
	    return list;
		
	}
	
	/***
	 * 解析两队近期战绩（包括近期10场战绩和近10场主|客场战绩）
	 * @return
	 */
	public Map<Object,Object> getJinqiZhanji(){
		HtmlUtilBean HtmlUtil = new HtmlUtilBean(this.getDocument());
		Elements ps = HtmlUtil.getElements("div.bottom_info >p");
		System.out.println(ps.size());
		String split = "   ";
		int pIndex=0;
		//主队近期战线文字描述
		String ht_name=ps.get(pIndex).select("strong").first().text();//主队名
		String ht_jqzhj = "主队：" +ps.get(pIndex).textNodes().get(0).text()
				+ split + ps.get(pIndex).select(">span").get(0).text()
				+ split + ps.get(pIndex).select(">span").get(1).text();
		
		//客队近期战线文字描述
		pIndex = 1;
		String at_name = ps.get(pIndex).select("strong").first().text();// 客队名
		String at_jqzhj = "客队：" + ps.get(pIndex).textNodes().get(0).text() + split
				+ ps.get(pIndex).select(">span").get(0).text() + split
				+ ps.get(pIndex).select(">span").get(1).text();
		
		//主队近期主战绩文字描述
		pIndex = 2;
		String ht_name_1 = ps.get(pIndex).select("strong").first().text();// 主队名
		String ht_zhchjqzhj = "主队主场：" + ps.get(pIndex).textNodes().get(0).text()
				+ split + ps.get(pIndex).select(">span").get(0).text() + split
				+ ps.get(pIndex).select(">span").get(1).text();
		
		//客队近期客战绩文字描述
		pIndex = 3;
		String at_name_1 = ps.get(pIndex).select("strong").first().text();// 客队名
		String at_kchjqzhj = "客队客场："
				+ ps.get(pIndex).textNodes().get(0).text() + split
				+ ps.get(pIndex).select(">span").get(0).text() + split
				+ ps.get(pIndex).select(">span").get(1).text();
				
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("ht_jqzhj", ht_jqzhj);
		map.put("at_jqzhj", at_jqzhj);
		map.put("ht_zhchjqzhj", ht_zhchjqzhj);
		map.put("at_kchjqzhj", at_kchjqzhj);
		
		return map;
	}
	
	/***
	 * 解析两队近期310走势及澳门盘路输赢走势， 近况走势 - LLDDLW
	 * @return
	 */
	public Map<Object,Object> getJingqiZoushi(){
		HtmlUtilBean HtmlUtil = new HtmlUtilBean(this.getDocument());
		Elements tables = HtmlUtil.getElements("div.M_content > table.pub_table");
		//System.out.println(tables.size());
		//主队胜平负走势
		String ht_310zoushi= tables.last().select("tr").get(0).select(">td").get(1).select(">font").text();
		//客队胜平负走势
		String at_310zoushi= tables.last().select("tr").get(1).select(">td").get(1).select(">font").text();
		
		//主队澳门盘路输赢走势
		String ht_am_plzoushi= tables.last().select("tr").get(0).select(">td").get(2).select(">font").text();
		
		//客队澳门盘路输赢走势
		String at_am_plzoushi= tables.last().select("tr").get(1).select(">td").get(2).select(">font").text();
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("ht_310zoushi",ht_310zoushi);
		map.put("at_310zoushi",at_310zoushi);
		map.put("ht_am_plzoushi",ht_am_plzoushi);
		map.put("at_am_plzoushi",at_am_plzoushi);
		
		return map;
	}
	
}
