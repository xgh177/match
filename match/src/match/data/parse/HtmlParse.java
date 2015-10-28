package match.data.parse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/***
 * 
 * @author xgh
 *
 */
public class HtmlParse extends Parse{
	/***
	 * html document
	 */
	private Document document;
	
	

	public HtmlParse() {
		super();
		
	}

	public HtmlParse(String dataUrl) {
		super(dataUrl);
		init();
	}
	
	private void init(){
		try {
			document = Jsoup.parse(new URL(this.getDataUrl()), 6000);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Object parse() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * 获取经过数据url构建的Document
	 * @return
	 */
	public Document getDocument() {
		return document;
	}

	private void setDocument(Document document) {
		this.document = document;
	}
    
	

}
