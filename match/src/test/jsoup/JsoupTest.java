package test.jsoup;

import java.net.URL;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import match.common.util.DateUtil;
import match.html.parse.util.HtmlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class JsoupTest {
	
	

	/**
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception{
		String url = "http://trade.500.com/jczq//?date=2015-10-27&playtype=both";
		Document html = Jsoup.parse(new URL(url), 5000);
		//比赛日期
		String match_date=html.select("div.bet_date").first().attr("date");
		System.out.println(match_date);
		//描述：星期二 2015-10-27
		String desc1 = html.select("div.bet_date").first().textNodes().get(0).text();
		System.out.println(desc1);
		//描述：22场比赛可投注
		String desc2=html.select("div.bet_date a").first().text();
		System.out.println(desc2);
	}
	
	@Test
	public void init() throws Exception{
		 String url = "http://trade.500.com/jczq//?date=2015-10-27&playtype=both";
		 Document html = Jsoup.parse(new URL(url), 5000);
		 HtmlUtil.default_htmlDocument=html;
	}
	
	@Test
	public void init1() throws Exception{
		 String url = "http://odds.500.com/fenxi/shuju-552514.shtml";
		 Document html = Jsoup.parse(new URL(url), 5000);
		 HtmlUtil.default_htmlDocument=html;
	}
	
	
	@Test
	public void getText() throws Exception{
		init();
		System.out.println(HtmlUtil.getText("div.bet_date", 0));
		System.out.println(HtmlUtil.getText("div.bet_date a", 0));
		System.out.println(HtmlUtil.getAttr("div.bet_date", "date"));
		Date date = DateUtil.string2yyyyMMdd(HtmlUtil.getAttr("div.bet_date", "date"));
		System.out.println(date);
		
	}
	
	/***
	 * 比赛基本信息解析
	 * @throws Exception
	 */
	@Test
	public void iterator() throws Exception{
		init();
		Element table = HtmlUtil.getFirstElement("table.bet_table");
		Elements trs = HtmlUtil.getFirstElement("table.bet_table").select("tbody tr");
		
		Elements tds = trs.get(0).select("td");
		int tdIndex=0;
		//比赛序号
	    String match_Seq = tds.get(tdIndex++).text();//第1列
	    //联赛名称
	    String league_name = tds.get(tdIndex++).text();//第2列
	    //比赛开始时间：开赛时间：2015-10-28 02:00
	    String match_time = tds.get(tdIndex++).select("span").get(1).attr("title");//第3列
	    match_time = match_time.substring(match_time.indexOf('：')+1).replace(" ", "").replace(":", "").replace("-", "")+"00";
	    //主队名
	    String home_team_name=tds.get(tdIndex).select("a").first().attr("title");//第4列
	    //主队排名
	    String ht_rank = tds.get(tdIndex++).select("span").first().text().replace("[", "").replace("]", "");
	    
	    tdIndex++;//第5列
	    //客队名
	    String away_team_name=tds.get(tdIndex).select("a").first().attr("title");//第6列
	    //客队排名
	    String at_rank = tds.get(tdIndex++).select("span").first().text().replace("[", "").replace("]", "");
	    
	    //让球数
	    String rq_count = tds.get(tdIndex++).select("p span").first().text();//第7列
	    
	    //胜平负赔率nspf 
	    float winsp = Float.parseFloat(HtmlUtil.getElement(tds.get(tdIndex).select("div").get(0), "span", 0).attr("data-sp"));//第8列
	    float drawsp = Float.parseFloat(HtmlUtil.getElement(tds.get(tdIndex).select("div").get(0), "span", 1).attr("data-sp"));//第8列
	    float lostsp = Float.parseFloat(HtmlUtil.getElement(tds.get(tdIndex).select("div").get(0), "span", 2).attr("data-sp"));//第8列
	    
	    //让球胜平负赔率spf
	    float rwinsp = Float.parseFloat(HtmlUtil.getElement(tds.get(tdIndex).select("div").get(1), "span", 0).attr("data-sp"));//第8列
	    float rdrawsp = Float.parseFloat(HtmlUtil.getElement(tds.get(tdIndex).select("div").get(1), "span", 1).attr("data-sp"));//第8列
	    float rlostsp = Float.parseFloat(HtmlUtil.getElement(tds.get(tdIndex).select("div").get(1), "span", 2).attr("data-sp"));//第8列
	    tdIndex++;
	    
	    //析、亚、欧【链接地址】
	    String shuju_href = HtmlUtil.getElement(tds.get(tdIndex),"a",0).attr("href");//第9列
	    String yazhi_href = HtmlUtil.getElement(tds.get(tdIndex),"a",1).attr("href");//第9列
	    String ouzhi_href = HtmlUtil.getElement(tds.get(tdIndex),"a",2).attr("href");//第9列
	    tdIndex++;
	    
	    //平均欧赔（由于是ajax请求，所以直接解析不出来）
	    Float avg_ewinsp = null;
	    Float avg_edrawsp = null;
	    Float avg_elostsp = null;
	    
	  
	    Map<Object,Object> rowMap = new HashMap<Object,Object> ();
	    rowMap.put("match_Seq", match_Seq);
	    rowMap.put("league_name", league_name);
	    rowMap.put("match_time", DateUtil.string2yyyyMMddHHmmss(match_time));
	    rowMap.put("ht_name", home_team_name); 
	    rowMap.put("ht_rank", ht_rank); 
	    rowMap.put("at_name", away_team_name);
	    rowMap.put("at_rank", at_rank);
	    rowMap.put("rq_count", rq_count);
	    rowMap.put("winsp", winsp);
	    rowMap.put("drawsp", drawsp);
	    rowMap.put("lostsp", lostsp);
	    rowMap.put("rwinsp", rwinsp);
	    rowMap.put("rdrawsp", rdrawsp);
	    rowMap.put("rlostsp", rlostsp);
	    rowMap.put("shuju_href", shuju_href);
	    rowMap.put("yazhi_href", yazhi_href);
	    rowMap.put("ouzhi_href", ouzhi_href);
	    rowMap.put("avg_ewinsp", avg_ewinsp);
	    rowMap.put("avg_edrawsp", avg_edrawsp);
	    rowMap.put("avg_elostsp", avg_elostsp);
	    
	    
	    //System.out.println(avg_ewinsp);
	    System.out.println(rowMap);
	}
	
	/***
	 * 比赛基本面解析
	 * http://odds.500.com/fenxi/shuju-552514.shtml
	 * @throws Exception
	 */
	@Test
	public void shujuParse() throws Exception{
		init1();
		Element div = HtmlUtil.getFirstElement("div.M_sub_title");
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
	    
	    System.out.println(total_row);
	    System.out.println(zhuchang_row);
	    System.out.println(kechang_row);
		
	}
	
	
}
