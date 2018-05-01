import java.util.*;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.URL;
public class comicbookplus_dl {
	public static void main(String[] args) throws IOException{
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter URL ##########");
		String imageurl = kb.nextLine();
		Document doc = Jsoup.connect(imageurl).get();
		int lowerrange= 0;
		int upperrange=200;
		Elements urls = doc.select(".n>a");
		if(!urls.isEmpty()){
			System.out.println("Enter Range /Leave Empty to download all");
			String range= kb.nextLine();
			if(range.equals(null)){
				lowerrange= Integer.parseInt(range.substring(0,range.indexOf("-")))-1;
				upperrange= Integer.parseInt(range.substring(range.indexOf("-")+1,range.length()))-1;
			}
			System.out.println("Size:" + urls.size());
			for(int i=0; i<urls.size();i+=2){
				//System.out.println((i/2)>=lowerrange && (i/2)<lowerrange);
				//System.out.println((!urls.get(i).text().contains("(alt)"))&&(i/2)>=lowerrange && (i/2)<upperrange);
				if((!urls.get(i).text().contains("(alt)"))&&(i/2)>=lowerrange && (i/2)<=upperrange){
					makecomic("http://comicbookplus.com"+urls.get(i).attr("href"));
				}
				//System.out.println(i);
			}
		}
		else{
			makecomic(imageurl);
		}
		kb.close();
	}
	public static void makecomic(String imageurl)throws IOException{
		Document doc = Jsoup.connect(imageurl).get();
		int pagenum =Integer.parseInt(doc.select("span[itemprop=numberOfPages]").text());
		String folder = doc.title();
		System.out.println(folder);
		String baseurl = doc.select("#maincomic").attr("src");
		for(int i=0; i<(pagenum-1);i++){
			System.out.println("Downloading Page "+i);
			String imagename= baseurl.substring(0,baseurl.lastIndexOf("/")+1)+i+baseurl.substring(baseurl.length()-4,baseurl.length());
			System.out.println(imagename);
			getImages(imagename,folder);
		}
		
	}
	 private static void getImages(String src, String directory) throws IOException {
	                //Exctract the name of the image from the src attribute
	                int indexname = src.lastIndexOf("/");
	                if (indexname == src.length()) {
	                    src = src.substring(1, indexname);
	                }
	                indexname = src.lastIndexOf("/");
	                String name = src.substring(indexname, src.length());
	                System.out.println(name);
	         
	                //Open a URL Stream
	                URL url = new URL(src);
	                InputStream in = url.openStream();
	                File f = new File(System.getProperty("user.dir")+"\\"+directory);
	                f.mkdir();
	                OutputStream out = new BufferedOutputStream(new FileOutputStream(f+ name));
	                for (int b; (b = in.read()) != -1;) {
	                    out.write(b);
	                }
	                out.close();
	                in.close();
	       }
}