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
	public void getText() throws Exception{
		init();
		System.out.println(HtmlUtil.getText("div.bet_date", 0));
		System.out.println(HtmlUtil.getText("div.bet_date a", 0));
		System.out.println(HtmlUtil.getAttr("div.bet_date", "date"));
		Date date = DateUtil.string2yyyyMMdd(HtmlUtil.getAttr("div.bet_date", "date"));
		System.out.println(date);
		
	}
	
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
}
