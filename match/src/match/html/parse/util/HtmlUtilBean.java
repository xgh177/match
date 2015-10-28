package match.html.parse.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/***
 * html解析工具类
 * @author xgh
 *
 */
public class HtmlUtilBean {
	/***
	 * 默认html document
	 */
    private final Document default_htmlDocument;
    
    public HtmlUtilBean(Document document){
    	default_htmlDocument = document;
    }
    
	/***
	 * 根据传入的元素选择字符串获取节点列表中的第一个节点下的第n个文本节点
	 * @param selectString 如：div.aaa
	 * @param textIndex 文本节点索引
	 * @return
	 */
	public  String getText(String selectString,int textIndex){
		return default_htmlDocument.select(selectString).first().textNodes().get(textIndex).text();
	}
	
	public  String getText(Elements elements,int elementIndex){
		return elements.get(elementIndex).text();
	}
	
	
	
	/***
	 * 根据传入的元素选择字符串获取节点列表中的第一个节点下的第1个文本节点
	 * @param selectString 如：div.aaa
	 * @return
	 */
	public  String getFirstText(String selectString){
		return default_htmlDocument.select(selectString).first().textNodes().get(0).text();
	}
	
	/***
	 * 根据传入的元素选择字符串获取节点列表中的第一个节点下的属性attrName的值
	 * @param selectString css选择字符串 ， 如：div.aaa
	 * @param attrName 属性名称
	 * @return
	 */
	public  String getAttr(String selectString,String attrName){
		return default_htmlDocument.select(selectString).first().attr(attrName);
	}
	
	/***
	 * 根据传入的元素选择字符串获取节点列表
	 * @param selectString css选择字符串，如：table.bet_table 选取表示class值为bet_table的所有元素
	 * @return
	 */
	public  Elements getElements(String selectString){
		return default_htmlDocument.select(selectString);
	}
	
	/***
	 * 根据传入的元素选择字符串获取节点列表第n的元素
	 * @param selectString css选择字符串
	 * @param index 节点索引
	 * @return
	 */
	public  Element getElement(String selectString,int index){
		return default_htmlDocument.select(selectString).get(index);
	}
	
	 /* 根据传入的元素选择字符串获取节点列表第n的元素
	 * @param selectString css选择字符串
	 * @param index 节点索引
	 * @return
	 */
	public  Element getElement(Element element,String selectString,int index){
		return element.select(selectString).get(index);
	}
	
	/***
	 * 根据传入的元素选择字符串获取节点列表第1的元素
	 * @param selectString css选择字符串
	 * @return
	 */
	public  Element getFirstElement(String selectString){
		return default_htmlDocument.select(selectString).first();
	}
	
	
}
